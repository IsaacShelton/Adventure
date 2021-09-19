import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class WitchBoss(var x: Double, var y: Double) {
    var initialX = x
    var initialY = y
    var alpha = 1.0
    var direction = 180
    var directionChange = -1
    var health = 4
    var animation = Animations.witch_fly.newInstance()
    var action = WitchAction.FLY
    var transition = WitchAction.NONE

    fun draw() {
        drawFromCameraEx(animation.now(), x, y - 64.0, 128.0 * getFacingDirection().toDouble(), 128.0, alpha)
    }

    fun getFacingDirection(): Int {
        return if(x.toInt() + 64 < Globals.player!!.x + 32) 1 else -1
    }

    fun getAABB(adjX: Int = 0, adjY: Int = 0): AABB {
        return AABB(x.toInt() + adjX, y.toInt() + adjY, 128, 80)
    }

    fun update() {
        val player = Globals.player!!

        if (transition != WitchAction.FADE_OUT && transition != WitchAction.FADE_IN) {
            // Handle player and witch boss collision
            val playerAABB = player.getAABB()

            if (playerAABB.intersecting(getAABB())) {
                if (player.vy > 0 && player.y + 32 < y.toInt() + 64) {
                    hurt(1)
                    player.vy = -24
                    Globals.playSound(Sounds.witchouch)
                } else {
                    Globals.killPlayer()
                }
            }
        }

        if (animation.currentAnimation == Animations.witch_cast_fly.currentAnimation && animation.atEnd()) {
            animation = Animations.witch_fly.newInstance()
        } else {
            animation.update()
        }

        if (transition == WitchAction.FADE_OUT) {
            alpha = alpha * 14.0 / 15.0
            if (abs(alpha) < 0.01) transition = WitchAction.FADE_IN
        }

        if (transition == WitchAction.FADE_IN) {
            alpha = (alpha * 14.0 + 1.0) / 15.0
            if (abs(alpha - 1.0) < 0.01) transition = WitchAction.NONE
        }

        if (transition == WitchAction.FLY) {
            x = if(abs(x - initialX) > 256.0) (x * 5.0 + initialX) / 6.0 else (x + initialX) / 2.0
            y = if(abs(y - initialY) > 256.0) (y * 5.0 + initialY) / 6.0 else (y + initialY) / 2.0

            if (abs(x - initialX) < 1.0 && abs(y - initialY) < 1.0) {
                x = initialX
                y = initialY
                action = WitchAction.FLY
                transition = WitchAction.NONE
                direction = 180
                directionChange = -1
            }

            return
        }

        if (action == WitchAction.FLY) {
            direction += directionChange * 2
            direction = direction % 360
            while (direction < 0) direction += 360

            if (direction == 90) directionChange *= -1

            val radians = direction.toDouble() * PI / 180.0
            x += 8.0 * cos(radians)
            y -= 8.0 * sin(radians)

            if (animation.currentAnimation == Animations.witch_cast_fly.currentAnimation) {
                if (animation.currentFrame == 5 && animation.ticker == 0) {
                    // Spawn in frog
                    val spawnX = x.toInt() + 64 + getFacingDirection() * 256
                    val spawnY = y.toInt()
                    val reversed = Random.nextDouble() < 0.5

                    if (Random.nextDouble() < 0.75) {
                        // Two
                        Globals.crawlers.add(Crawler(CrawlerKind.FROG, spawnX - 2, spawnY, 2, reversed))
                        Globals.crawlers.add(Crawler(CrawlerKind.FROG, spawnX + 2, spawnY, 2, !reversed))
                    } else {
                        // One
                        Globals.crawlers.add(Crawler(CrawlerKind.FROG, spawnX, spawnY, 2, reversed))
                    }

                    Globals.particles.add(Particle(Animations.hex, spawnX - 32, spawnY - 32, 64, 64))
                    Globals.playSound(Sounds.hex)
                }
            } else if (Random.nextDouble() < 0.01 * (4 - health / 5).toDouble() && Globals.crawlers.size < 100) {
                animation = Animations.witch_cast_fly.newInstance()
            }

            return
        }
    }

    fun hurt(damage: Int) {
        // Deal damage
        health -= damage

        // Die if killed
        if (health <= 0) Globals.witchBoss = null

        // Fade out
        transition = WitchAction.FADE_OUT
    }
}