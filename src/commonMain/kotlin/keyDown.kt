import com.soywiz.korev.Key

fun keyDown(key: Key) {
    if (Globals.scene == -1) {
        Globals.titleState.onKey(key)
        return
    }

    // Only allow resuming when game is paused
    if (key == Globals.controls.pause) {
        pause()
        return
    }

    if (Globals.isPaused) return

    // Start game if on title screen
    if (Globals.scene == 0) gotoScene1()

    // If reading sign, close the signboard
    if (Globals.signboard != null) Globals.signboard!!.close()

    // Reading signs
    if (key == Globals.controls.interact) {
        if (Globals.signboard != null) {
            Globals.signboard!!.close()
        } else for (sign in Globals.signs) if (sign.inRange()) {
            sign.read()
            break
        }
    }

    val dropThrough = Globals.input!!.keys.pressing(Globals.controls.down)
    val player = Globals.player!!
    val controls = Globals.controls

    when (Globals.item) {
        Item.STAR -> {
            // Teleport to save-state
            if (key == controls.b) {
                teleport()
                Globals.playSound(Sounds.star)
            }

            // Set save-state
            if (key == controls.a) {
                Globals.shadow = player.freeze(getImageForPlayer(player))
                Globals.playSound(Sounds.remember)
            }
        }
        Item.GUN -> {
            if (key == controls.a && !player.swimming) {
                // Shoot gun
                Globals.particles.add(Particle(Animations.smoke, player.x + player.direction * 128, player.y, 64, 64))
                Globals.bullets.add(Bullet(player.x + player.direction * 96 + 16, player.y + 28, player.direction * 20))
                Globals.playSound(Sounds.bullet)
            }
        }
        Item.CANNON -> {
            if (key == controls.a && Globals.cannon != null) {
                val c = Globals.cannon!!

                // Ignite
                Globals.particles.add(Particle(Animations.flame, c.x + (if (c.direction == 1) -4 else 120) - 16, c.y - 16, 64, 64))

                // Shoot cannon
                if (Globals.cannonBall == null) {
                    Globals.cannonBall = CannonBall(c.x + (if (c.direction == 1) 128 else -40), c.y + 16, c.direction * 20)

                    Globals.particles.add(Particle(Animations.smoke, c.x + (if(c.direction == 1) 128 else -40), c.y, 64, 64))

                    Globals.playSound(Sounds.cannon)
                }
            }

            if (key == controls.b) {
                // Place cannon
                val newCannon = Cannon(if (player.direction == 1) player.x else player.x - 88, player.y - 128, player.direction)

                if (collidingGround(newCannon.getAABB()) == null) {
                    // Not obstructed
                    Globals.cannon = newCannon
                }
            }
        }
        Item.DOUBLEJUMP -> {
            if ((key == controls.up || key == controls.altUp) && !playerIsInWater() && !Globals.doubleJumped && playerOnPlatform(dropThrough) == null) {
                Globals.particles.add(Particle(Animations.slimesmoke, player.x, player.y, 64, 64))
                player.vy = -20
                Globals.doubleJumped = true
                Globals.playSound(Sounds.doublejump)
            }
        }
        Item.PIRATE -> {
            // This code is horrible, but it's good enough
            if (player.pirateShootCooldown == 0 && player.pirateClawCooldown == 0) {
                if (key == controls.a && !player.swimming) {
                    // Shoot gun
                    player.pirateShootCooldown = 10
                    Globals.particles.add(Particle(Animations.smoke, player.x + player.direction * 128, player.y, 64, 64))
                    Globals.bullets.add(Bullet(player.x + player.direction * 96 + 16, player.y + 28, player.direction * 20))
                    Globals.bullets.add(Bullet(player.x + player.direction * 88 + 16, player.y + 28 + 8, player.direction * 20, -1))
                    Globals.bullets.add(Bullet(player.x + player.direction * 88 + 16, player.y + 28 - 8, player.direction * 20, 1))
                    Globals.playSound(Sounds.bullet)
                } else if (key == controls.b && !player.swimming) {
                    // Use claw
                    player.pirateClawCooldown = 15
                    Globals.playSound(Sounds.bullet)
                }
            }
        }
        else -> {}
    }
}
