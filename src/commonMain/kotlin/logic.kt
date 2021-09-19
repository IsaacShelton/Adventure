import kotlin.math.*
import kotlin.random.Random

fun isInWater(aabb: AABB): Boolean {
    for (lake in Globals.lakes) {
        if (aabb.intersecting(lake.collision)) return true
    }
    return false
}

fun playerIsInWater(): Boolean {
    return isInWater(Globals.player!!.getAABB())
}

fun collidingGround(aabb: AABB, trueY: Int = aabb.y, dropThrough: Boolean = false): Region? {
    for (platform in Globals.platforms) {
        if (platform.collision.intersecting(aabb)) return platform
    }
    if (!dropThrough) for (bridge in Globals.bridges) {
        if (trueY + aabb.h <= bridge.collision.y) {
            if (bridge.collision.intersecting(aabb)) return bridge
        }
    }
    return null
}

fun playerCollidingPlatform(trueY: Int, dropThrough: Boolean): Region? {
    return collidingGround(Globals.player!!.getAABB(), trueY, dropThrough)
}

fun playerOnPlatform(dropThrough: Boolean): Region? {
    val player = Globals.player!!
    return collidingGround(player.getAABB(0, 1), player.y, dropThrough)
}

fun updateCrawlers() {
    val player = Globals.player!!
    val playerAABB = player.getAABB()
    var didPlayerHurtCrawler = false
    var bigJumpBack = false

    var i: Int = 0

    while (i < Globals.crawlers.size) {
        val crawler = Globals.crawlers[i]

        // Update crawler's animation
        crawler.update()

        // Handle landing on ground
        val ground: Region? = collidingGround(crawler.getAABB(0, 1), crawler.y, false)
        crawler.vy = if (ground != null) 0 else if(crawler.vy < 24) crawler.vy + 1 else crawler.vy

        // Advance crawler
        var collision: Region? = null

        if (crawler.kind == CrawlerKind.FROG) {
            if (crawler.animation.currentFrame != 0 && crawler.animation.currentFrame != 4) {
                crawler.x += crawler.speed * crawler.vx * 2
                collision = collidingGround(crawler.getAABB())
            }

            if (Random.nextDouble() < 0.005 && abs(crawler.x - player.x) < 640) {
                Globals.playSound(Sounds.ribbit)
            }
        } else if (crawler.kind == CrawlerKind.CRAB) {
            val directionToPlayerUnscaled = player.x - crawler.x
            val directionToPlayer = if (directionToPlayerUnscaled == 0) 1 else directionToPlayerUnscaled / abs(directionToPlayerUnscaled)
            val scale = if (crawler.vx == 0) 1 else if (crawler.vx / abs(crawler.vx) == directionToPlayer) 2 else 1
            crawler.x += crawler.speed * crawler.vx * scale
            collision = collidingGround(crawler.getAABB())
        } else {
            crawler.x += crawler.speed * crawler.vx
            collision = collidingGround(crawler.getAABB())
        }

        // Turn crawler around if ran into something
        if (collision != null) {
            crawler.x = if (crawler.vx > 0) collision.getX() - 64 * crawler.getSizeMultiplier() else collision.getMaxX()
            crawler.vx *= -1
        }

        // Apply gravity to crawler
        crawler.y += crawler.vy
        collision = collidingGround(crawler.getAABB(), crawler.y - crawler.vy, false)

        if (collision != null) {
            crawler.y = if (crawler.vy > 0) collision.getY() - 32 * crawler.getSizeMultiplier() else collision.getMaxY()
        }

        // Kill crawlers in water
        if (isInWater(crawler.getAABB())) {
            Globals.crawlers.removeAt(i)
            continue
        }

        // Handle player-crawler collision
        if (playerAABB.intersecting(crawler.getAABB())) {
            if (player.vy > 0 && player.y + 32 <= crawler.y + 16 * crawler.getSizeMultiplier()) {
                didPlayerHurtCrawler = true

                if(crawler.hurt(1)){
                    Globals.crawlers.removeAt(i)
                    continue
                } else if(crawler.getSizeMultiplier() != 1){
                    // HACK: Revert y position when hitting enemy that stays alive
                    player.y -= player.vy + 1

                    bigJumpBack = crawler.kind == CrawlerKind.CRAB
                }
            } else {
                Globals.killPlayer()
            }
        }

        if (player.pirateClawCooldown > 0 && crawler.getAABB().intersecting(player.getAABB(player.direction * 64, 0))) {
            // Always one shots
            Globals.crawlers.removeAt(i)
            Globals.playSound(Sounds.hurt)
            continue
        }

        // Turn crawler around if about to fall off
        if (ground != null && collidingGround(crawler.getAABB(crawler.vx * 64 * crawler.getSizeMultiplier(), 32 * crawler.getSizeMultiplier())) == null) {
            crawler.vx *= -1
        }

        i++
    }

    if (didPlayerHurtCrawler) {
        Globals.playSound(Sounds.hurt)

        if (bigJumpBack) {
            player.vy = -12
            player.vx = 6 * (if (Random.nextBoolean()) 1 else -1)
        } else {
            player.vy = -10
        }
    }
}

fun updateSwordfish() {
    val playerAABB = Globals.player!!.getAABB()

    for (swordfish in Globals.swordfishes) {
        swordfish.update()

        var overrideMovement = false

        // Do special things for shark
        // Should really not check this way, but I'm lazy so I don't care
        if (swordfish.animation.currentAnimation == Animations.shark.currentAnimation) {
            val xDiff = -1.0 * (swordfish.x + 128 - (Globals.player!!.x + 32)).toDouble()
            val yDiff = -1.0 * (swordfish.y + 32 - (Globals.player!!.y + 32)).toDouble()
            val distance = sqrt(xDiff * xDiff + yDiff * yDiff)
            val targetXDirection = if (xDiff < 0) -1 else 1

            // If in agro range and if shark is facing player,
            // Attempt to adjust y position
            if (distance < 320) {
                overrideMovement = true
                swordfish.ticker = 0

                val theta = atan2(yDiff, xDiff)
                val deltaY = (1.1 * swordfish.speed.toDouble() * sin(theta)).toInt()
                swordfish.y += deltaY

                // This code is horrible, but it's not worth cleaning up right now
                if (swordfish.touchingAnother() || !isInWater(swordfish.getAABB()) || !isInWater(swordfish.getAABB(0, 56 * sign(deltaY.toDouble()).toInt()))
                        || playerOnPlatform(false) != null || collidingGround(swordfish.getAABB()) != null) {
                    swordfish.y -= deltaY
                    overrideMovement = false
                } else {
                    swordfish.sinceSeenPlayer = 0

                    if (targetXDirection != swordfish.vx) {
                        swordfish.vx *= -1
                    }

                    swordfish.x += (1.5 * swordfish.speed.toDouble() * cos(theta)).toInt()
                    var collision: Region? = collidingGround(swordfish.getAABB())

                    if (collision != null) {
                        swordfish.x = if (swordfish.vx > 0) collision.getX() - 256 else collision.getMaxX()
                        swordfish.vx *= -1
                    }

                    if (swordfish.y + 64 > Globals.viewHeight) {
                        swordfish.y = Globals.viewHeight - 64
                    }
                }
            }
        }

        if(!overrideMovement){
            swordfish.sinceSeenPlayer++
            swordfish.x += swordfish.speed * swordfish.vx
            var collision: Region? = collidingGround(swordfish.getAABB())

            if (collision != null) {
                swordfish.x = if (swordfish.vx > 0) collision.getX() - 256 else collision.getMaxX()
                swordfish.vx *= -1
            }

            if (swordfish.sinceSeenPlayer > 60 && swordfish.y != swordfish.initialY) {
                val deltaY = if (swordfish.initialY < swordfish.y) -1 else 1
                swordfish.y += deltaY

                if (swordfish.touchingAnother() || collidingGround(swordfish.getAABB()) != null) {
                    // Undo y change if blocked
                    swordfish.y -= deltaY
                }
            }
        }

        if (playerAABB.intersecting(swordfish.getAABB())) {
            Globals.killPlayer()
        }
    }
}

fun updateCannon() {
    if (Globals.cannon == null) return

    val c = Globals.cannon!!

    // Handle landing on ground
    val ground: Region? = collidingGround(c.getAABB(0, 1), c.y, false)
    c.vy = if (ground != null) 0 else if (c.vy < 32) c.vy + 1 else c.vy

    // Apply gravity to cannon
    c.y += c.vy
    val collision: Region? = collidingGround(c.getAABB(), c.y - c.vy, false)

    if (collision != null) {
        c.y = if (c.vy > 0) collision.getY() - 72 else collision.getMaxY()
    }

    // If falling off screen, sit at bottom of lake
    if (c.y + 72 > 1280) {
        c.y = 1280 - 72
        c.vy = 0
    }
}
