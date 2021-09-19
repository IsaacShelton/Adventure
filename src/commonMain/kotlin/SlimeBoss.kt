import kotlin.math.min
import kotlin.random.Random

class SlimeBoss(var x: Int, var y: Int) {
    var vx = -1
    var vy = 0
    var health = 10
    var animation = Animations.slime_animation.newInstance()

    fun draw() {
        drawFromCameraEx(animation.now(), x.toDouble(), y.toDouble() - 64.0, 128.0 * vx.toDouble(), 128.0)
    }

    fun getAABB(adjX: Int = 0, adjY: Int = 0): AABB {
        return AABB(x + adjX, y + adjY, 128, 64)
    }

    fun update() {
        animation.update()

        // Handle landing on ground
        val ground: Region? = collidingGround(getAABB(0, 1))
        vy = if (ground != null) 0 else if (vy < 24) this.vy + 1 else this.vy

        // Advance forward
        x += vx * 3
        var collision: Region? = collidingGround(getAABB())

        // Turn slime boss around if ran into something
        if (collision != null) {
            x = if (vx > 0) collision.getX() - 64 else collision.getMaxX()
            vx *= -1
        }

        // Apply gravity to slime boss
        y += vy
        collision = collidingGround(getAABB())

        if (collision != null) {
            y = if (vy > 0) collision.getY() - 64 else collision.getMaxY()
        }

        // Turn slime boss around if about to fall off
        if (ground != null && collidingGround(getAABB(vx * 128, 64)) == null) {
            vx *= -1
        }

        // Handle player and slime boss collision
        val player = Globals.player!!
        val playerAABB: AABB = player.getAABB()

        if (playerAABB.intersecting(getAABB())) {
            if (player.vy > 0 && player.y + 32 < y + 16) {
                hurt(1)
                player.vy = -18
                Globals.playSound(Sounds.hurt)
            } else {
                Globals.killPlayer()
            }
        }
    }

    fun hurt(rawDamage: Int) {
        val damage = min(rawDamage, health)

        // Separate into smaller slimes
        for (i in 0 until damage) separate()

        // Deal damage
        health -= damage

        // Maybe switch direction
        if (Random.nextDouble() < 0.5) vx *= -1

        // Die if killed
        if (health <= 0) Globals.slimeBoss = null
    }

    fun separate() {
        // Spawn some smaller slimes moving at random speeds in random directions
        val parts: Int = (Random.nextDouble() * 4.0).toInt() + 1
        for (i in 0 until parts) {
            val speed = (Random.nextDouble() * 4.0).toInt() + 1
            Globals.crawlers.add(Crawler(CrawlerKind.SLIME, x + 32, y + 16, speed, Random.nextDouble() < 0.5))
        }
    }
}