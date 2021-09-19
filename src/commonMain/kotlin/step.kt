import com.soywiz.korau.sound.Sound
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

fun step() {
    // Shift sky
    if(!Globals.isPaused) {
        Globals.skyShift += 1.0 / 60.0 * 10.0
        Globals.skyShift %= Globals.viewWidth
    }

    if (Globals.scene == -1) {
        // Title screen
        Globals.titleState.step()
        return
    }

    if (abs(Globals.blackOverlay) >= 0.01) {
        Globals.blackOverlay -= 1.0 / 60.0
        Globals.blackOverlay = max(0.0, Globals.blackOverlay)
    }

    val player = Globals.player!!

    // Removed closed signboards
    if (Globals.signboard != null && Globals.signboard!!.closed()) Globals.signboard = null

    // Animate signboards
    if (Globals.signboard != null) Globals.signboard!!.update()

    // Handle player when dead
    if (Globals.isDead) {
        // Respawn if death animation is over
        if (Globals.deathY > 1280.0 || --Globals.respawnTimer == 0) {
            Globals.isDead = false
            Globals.respawnTimer = 0
            Globals.item = Item.NONE
            Globals.cannon = null
            gotoScene(Globals.scene)
        }

        // Animate death if initial pause-time is over
        if (Globals.respawnTimer < 120) {
            Globals.deathX += Globals.deathVx
            Globals.deathY += Globals.deathVy
            Globals.deathVy += 1.0
        }

        return
    }

    // Wait to resume if paused
    if (Globals.isPaused) return

    // Animate item pick up
    if (Globals.item != Item.NONE) {
        val targetScreenX = 1792.0 + 14.0
        val targetScreenY  = 32.0 + 14.0
        val itemScreenX = Globals.itemScreenX
        val itemScreenY = Globals.itemScreenY

        Globals.itemScreenX =
            if (itemScreenX < targetScreenX - 4.0) itemScreenX + (targetScreenX - itemScreenX) / 4.0
            else targetScreenX

        Globals.itemScreenY =
            if (itemScreenY > targetScreenY + 4.0) itemScreenY + (targetScreenY - itemScreenY) / 4.0
            else targetScreenY
    }

    if (player.swimming) {
        Animations.gun_swim.update()
        Animations.cannoneer_swim.update()
        Animations.pirate_swim.update()

        if (!playerIsInWater()) {
            // Transition from swimming -> walking
            player.swimming = false
            Animations.swim.restart()
            Animations.walk.restart()
            Animations.gun_walk.restart()
            Animations.gun_swim.restart()
            Animations.cannoneer_walk.restart()
            Animations.cannoneer_swim.restart()
            Animations.pirate_walk.restart()
            Animations.pirate_swim.restart()
            player.vy -= 20

            // Launch player in direction if moving horizontally
            if (player.movingHorizontally) player.vx += player.direction * 10

            // Play sound effect
            Globals.playSound(Sounds.splush)

            Globals.sinceLastBlub = 0
        } else {
            if (Globals.sinceLastBlub > 300) {
                Globals.playSound(Sounds.blub)
                Globals.sinceLastBlub = 0
            } else {
                Globals.sinceLastBlub++
            }
        }
    } else if (playerIsInWater()) {
        // Transition from walking - > swimming
        player.swimming = true
        Animations.swim.restart()
        Animations.walk.restart()
        Animations.gun_walk.restart()
        Animations.gun_swim.restart()
        Animations.cannoneer_walk.restart()
        Animations.cannoneer_swim.restart()
        Animations.pirate_walk.restart()
        Animations.pirate_swim.restart()
        Globals.doubleJumped = true

        // Play sound effect
        Globals.playSound(if (player.vy < 22) Sounds.splash else Sounds.heavysplash)
    }

    // Update conditional animations
    if (player.moving) {
        if (player.swimming && Globals.item != Item.GUN) {
            Animations.swim.update()
        } else if(!player.parachuting) {
            when(Globals.item){
                Item.GUN -> Animations.gun_walk.update()
                Item.CANNON -> Animations.cannoneer_walk.update()
                Item.PIRATE -> Animations.pirate_walk.update()
                else -> Animations.walk.update()
            }
        }
    }

    Globals.theme.wave.update()
    Animations.inwards_portal.update()
    Animations.disabled_portal.update()
    Animations.outwards_portal.update()
    Animations.enabler.update()

    // Record movement & whether horizontally moving
    player.moving = false
    player.movingHorizontally = false
    if (player.pirateShootCooldown > 0) player.pirateShootCooldown -= 1
    if (player.pirateClawCooldown > 0) player.pirateClawCooldown -= 1

    var i: Int = 0
    while (i < Globals.particles.size) {
        val particle = Globals.particles[i]

        if (particle.update()) Globals.particles.removeAt(i) else i++
    }

    i = 0
    while (i < Globals.bullets.size) {
        val bullet = Globals.bullets[i]

        bullet.x += bullet.vx
        bullet.y += bullet.vy

        // Destroy off-screen bullets
        if (bullet.x < 0 || bullet.x > Globals.sceneWidth.toInt()) {
            Globals.bullets.removeAt(i)
            continue
        }

        val aabb = bullet.getAABB()

        if (collidingGround(aabb) != null) {
            Globals.bullets.removeAt(i)
            continue
        }

        var hit = false

        // Test hit for crawlers
        var j: Int = 0
        while (j < Globals.crawlers.size) {
            if (aabb.intersecting(Globals.crawlers[j].getAABB())) {
                Globals.crawlers.removeAt(j)
                hit = true
                Globals.playSound(Sounds.hurt)
                break
            }
            j++
        }

        // Test hit for swordfish
        j = 0
        if(!hit) while (j < Globals.swordfishes.size) {
            if (aabb.intersecting(Globals.swordfishes[j].getAABB())) {
                Globals.swordfishes.removeAt(j)
                hit = true
                break
            }
            j++
        }

        // Test hit for slime boss
        if(!hit && Globals.slimeBoss != null && aabb.intersecting(Globals.slimeBoss!!.getAABB())) {
            Globals.slimeBoss!!.hurt(1)
            Globals.playSound(Sounds.hurt)
            hit = true
        }

        // Test hit for witch boss
        if(!hit && Globals.witchBoss != null && aabb.intersecting(Globals.witchBoss!!.getAABB())) {
            Globals.witchBoss!!.hurt(1)
            Globals.playSound(Sounds.witchouch)
            hit = true
        }

        if (hit) Globals.bullets.removeAt(i)
        i++
    }

    if (Globals.cannonBall != null) {
        val cannonBall = Globals.cannonBall!!

        cannonBall.x += cannonBall.vx
        val aabb = cannonBall.getAABB()

        // Destroy off-screen & stopped cannons balls
        if (cannonBall.x < 0 || cannonBall.x.toDouble() > Globals.sceneWidth || collidingGround(aabb) != null) {
            Globals.cannonBall = null
        } else {
            var hit = false

            // Test hit for crawlers
            var j: Int = 0
            while (j < Globals.crawlers.size) {
                if (aabb.intersecting(Globals.crawlers[j].getAABB())) {
                    Globals.crawlers.removeAt(j)
                    hit = true
                    Globals.playSound(Sounds.splat)
                    break
                }
                j++
            }

            // Test hit for swordfish
            j = 0
            if (!hit) while (j < Globals.swordfishes.size) {
                if (aabb.intersecting(Globals.swordfishes[j].getAABB())) {
                    Globals.swordfishes.removeAt(j)
                    hit = true
                    Globals.playSound(Sounds.splat)
                    break
                }
            }

            // Test hit for slime boss
            if(!hit && Globals.slimeBoss != null && aabb.intersecting(Globals.slimeBoss!!.getAABB())) {
                Globals.slimeBoss!!.hurt(2)
                hit = true
                Globals.playSound(Sounds.splat)
            }

            // Test hit for witch boss
            if(!hit && Globals.witchBoss != null && aabb.intersecting(Globals.witchBoss!!.getAABB())) {
                Globals.cannonBall = null
                Globals.witchBoss!!.hurt(2)
                Globals.playSound(Sounds.witchouch)
                hit = true
            }

            if (!hit && aabb.intersecting(player.getAABB())) {
                Globals.killPlayer()
                return
            }
        }
    }

    if (Globals.blackOverlay < 0.5 && Globals.input!!.keys.pressing(Globals.controls.right)) {
        // Walk right
        player.x += if (Globals.item == Item.WING) 8 else 6
        player.direction = 1
        player.moving = true
        player.movingHorizontally = true

        // Prevent walking into something
        val collision: Region? = playerCollidingPlatform(player.y, false)
        if (collision != null) player.x = collision.getX() - 64

        // Close signboard if reading
        if (Globals.signboard != null) Globals.signboard!!.close()
    }

    if (Globals.blackOverlay < 0.5 && Globals.input!!.keys.pressing(Globals.controls.left)) {
        // Walk left
        player.x += if (Globals.item == Item.WING) -8 else -6
        player.direction = -1
        player.moving = true
        player.movingHorizontally = true

        // Prevent walking into something
        val collision: Region? = playerCollidingPlatform(player.y, false)
        if (collision != null) player.x = collision.getMaxX()

        // Close signboard if reading
        if (Globals.signboard != null) Globals.signboard!!.close()
    }

    val dropThrough = Globals.input!!.keys.pressing(Globals.controls.down)
    val currentlyOnGround: Region? = playerOnPlatform(dropThrough)
    val holdingUp = Globals.input!!.keys.pressing(Globals.controls.up) || Globals.input!!.keys.pressing(Globals.controls.altUp)

    if (player.swimming) {
        // Swimming Mode

        if (holdingUp) {
            // Swim upwards
            val previousY = player.y
            player.y -= if (Globals.item == Item.WING) 6 else 4
            player.moving = true

            // Prevent swimming upwards into something
            val collision: Region? = playerCollidingPlatform(previousY, false)
            if (collision != null) player.y = collision.getMaxY()

            // Close signboard if reading
            if (Globals.signboard != null) Globals.signboard!!.close()
        }

        if (Globals.input!!.keys.pressing(Globals.controls.down)) {
            // Swim downwards
            val previousY = player.y
            player.y += if (Globals.item == Item.WING) 6 else 4
            player.moving = true

            // Prevent swimming downwards into something
            val collision: Region? = playerCollidingPlatform(previousY, dropThrough)
            if (collision != null) player.y = collision.getY() - 64

            // Close signboard if reading
            if (Globals.signboard != null) Globals.signboard!!.close()
        }

        // Sink player in water
        player.vy += if (player.vy < 1) 1 else if (player.vy > 1) -1 else 0
    } else if (currentlyOnGround == null) {
        // Airborn Mode
        player.vy += if (player.vy < 24) 1 else 0
    } else {
        // Grounded Mode
        Globals.doubleJumped = false
        var footstep: Sound? = null

        if (currentlyOnGround is Platform) {
            footstep = if (Globals.theme != Themes.THREE) {
                Sounds.footstep_grass
            } else {
                Sounds.footstep_sand
            }
        } else if (currentlyOnGround is Bridge) {
            footstep = Sounds.footstep_wood
        }

        val footstepInterval = if (Globals.item == Item.WING) 10 else 13

        if (footstep != null && Globals.sinceFootstep > footstepInterval && player.movingHorizontally) {
            Globals.playSound(footstep)
            Globals.sinceFootstep = -1
        }

        if (Globals.item == Item.WING && Globals.input!!.keys.pressing(Globals.controls.a)) {
            // Wing Big Jump Ability
            player.vy = -24
            player.vx += if (player.movingHorizontally) 1 else 0
            Globals.particles.add(Particle(Animations.smoke, player.x, player.y, 64, 64))

            // Play sound effect
            Globals.playSound(Sounds.wingjump)

            // Close signboard if reading
            if (Globals.signboard != null) Globals.signboard!!.close()
        } else if (holdingUp) {
            // Standard Jump
            player.vy = -20

            // Close signboard if reading
            if (Globals.signboard != null) Globals.signboard!!.close()
        } else {
            // Stand on Ground
            player.vy = 0
        }
    }

    Globals.sinceFootstep++

    // Parachute if falling in air and UP is held
    player.parachuting = currentlyOnGround == null && player.vy > 0 && holdingUp
    if (player.parachuting && player.vy > 4) player.vy = 4

    // Apply horizontal force on player
    player.x += player.vx
    var collision: Region? = playerCollidingPlatform(player.y, false)
    if (collision != null) player.x = if (player.vx > 0) collision.getX() - 64 else collision.getMaxX()

    // Apply vertical force on player
    if (Globals.blackOverlay < 0.5) {
        player.y += player.vy
        collision = playerCollidingPlatform(player.y - player.vy, dropThrough)
        if (collision != null) player.y = if (player.vy > 0) collision.getY() - 64 else collision.getMaxY()
    }

    // Apply friction
    player.vx += if (player.vx < 0) 1 else if (player.vx > 0) -1 else 0

    // Clamp player to level
    player.x =
        when {
            player.x < 0 -> 0
            player.x + 64 > Globals.sceneWidth.toInt() -> Globals.sceneWidth.toInt() - 64
            else -> player.x
        }

    player.y =
        when {
            player.y < 0 -> 0
            player.y + 64 > 1280 -> 1216
            else -> player.y
        }

    // Animate teleportation
    if (Globals.teleporting) {
        Globals.teleportOpacity -= 0.1

        if (Globals.teleportOpacity < 0.0) {
            Globals.player = Globals.shadow!!.thaw()
            Globals.teleporting = false
        }
    }

    // Handle item pick up
    for (itemSpawn in Globals.itemSpawns) {
        if (itemSpawn.collision.intersecting(player.getAABB())) {
            if (Globals.item == itemSpawn.item) break
            pickupItem(itemSpawn)
            break
        }
    }

    // Collect portal enabler if applicable
    if (Globals.portalEnabler != null && Globals.portalEnabler!!.collision.intersecting(player.getAABB())) {
        Globals.portalEnabler!!.collected = true
        Globals.playSound(Sounds.enabler)
    }

    // Move portal enabler towards portal if collected
    if (Globals.portalEnabler != null && Globals.portalEnabler!!.collected && Globals.exit != null) {
        val exitX = Globals.exit!!.visual.x + 32
        val exitY = Globals.exit!!.visual.y + 48
        var newX = (Globals.portalEnabler!!.collision.x + exitX) / 2
        val newY = (Globals.portalEnabler!!.collision.y + exitY) / 2

        val oldX = Globals.portalEnabler!!.collision.x
        if (abs(newX - oldX) > 240) {
            if (newX < oldX) newX = oldX - 240
            else if (newX < oldX) newX = oldX + 240
        }

        Globals.portalEnabler!!.collision.x = newX
        Globals.portalEnabler!!.collision.y = newY
        Globals.portalEnabler!!.visual.x = newX
        Globals.portalEnabler!!.visual.y = newY

        val xDiff = abs(newX - exitX).toDouble()
        val yDiff = abs(newY - exitY).toDouble()
        val dist = sqrt(xDiff * xDiff + yDiff * yDiff)

        if (dist < 2.0) {
            Globals.exitDisabled = false
            Globals.portalEnabler = null
        }
    }

    // Update camera
    updateCamera()

    // Update enemies
    updateCrawlers()
    updateSwordfish()

    // Update bosses
    Globals.slimeBoss?.update()
    Globals.witchBoss?.update()

    // Update cannon
    updateCannon()

    // Animate sign 'Press H' message
    for (sign in Globals.signs) {
        val targetOpacity = if (sign.inRange()) 1.0 else 0.0
        sign.pressHOpacity =
            if(abs(sign.pressHOpacity - targetOpacity) > 0.0001) sign.pressHOpacity + (targetOpacity - sign.pressHOpacity) / 6.0
            else targetOpacity
    }

    // Scene events
    if (Globals.scene == 11 && Globals.crawlers.size == 0 && Globals.slimeBoss == null && Globals.exitDisabled && Globals.portalEnabler == null)
        Globals.portalEnabler = PortalEnabler(928, 480, 64, 64)
    if (Globals.scene == 16 && Globals.crawlers.size == 0 && Globals.witchBoss == null && Globals.exitDisabled && Globals.portalEnabler == null)
        Globals.portalEnabler = PortalEnabler(928, 480, 64, 64)

    // Goto next scene if at exit portal
    if (Globals.exit != null && !Globals.exitDisabled && Globals.exit!!.collision.intersecting(player.getAABB())) {
        Globals.playSound(Sounds.teleport)
        gotoScene(++Globals.scene)
    }

    // This is a bad way to do it, but whatever
    if (Globals.sinceLastSplat < 1000) Globals.sinceLastSplat++
}
