
fun drawFromCamera(texture: String, x: Double, y: Double, w: Double, h: Double, alpha: Double = 1.0) {
    Globals.autoRenderer!!.render(texture,x - Globals.cameraX, y - Globals.cameraY, w, h, alpha)
}

fun drawFromCameraEx(texture: String, x: Double, y: Double, w: Double, h: Double, alpha: Double = 1.0) {
    var realX = x
    var realY = y
    if (w < 0.0) realX -= w
    if (h < 0.0) realY -= h
    Globals.autoRenderer!!.render(texture,realX - Globals.cameraX, realY - Globals.cameraY, w, h, alpha)
}

fun drawLakeBehind(lake: Lake) {
    var x = lake.getX()
    while (x < lake.getMaxX()) {
        drawFromCamera(Globals.theme.wave.now(), x.toDouble(), lake.getY().toDouble(), 64.0, 64.0)
        x += 64
    }

    if (lake.getY() + 64 <= lake.getMaxY()) {
        drawFromCamera(Globals.theme.water, lake.getX().toDouble(), lake.getY().toDouble() + 64.0, lake.getWidth().toDouble(), lake.getHeight().toDouble())
    }
}

fun drawLakeUpFront(lake: Lake) {
    var y = lake.getY()

    while (y < lake.getMaxY()) {
        var x = lake.getX()

        while (x < lake.getMaxX()) {
            var opacity = 0.0
            val tile = AABB(x, y, 64, 64)

            if (opacity == 0.0) for (platform in Globals.platforms) {
                if (tile.intersecting(platform.visual)) {
                    opacity = 0.9
                    break
                }
            }

            if (opacity == 0.0) for (bridge in Globals.bridges) {
                if (tile.intersecting(bridge.visual)) {
                    opacity = 0.9
                    break
                }
            }

            if (opacity == 0.0 && Globals.exit != null && tile.intersecting(Globals.exit!!.visual))
                opacity = 0.5

            if (opacity == 0.0) {
                x += 64
                continue
            }

            drawFromCamera(if (y == lake.getY()) Globals.theme.wave.now() else Globals.theme.water, x.toDouble(), y.toDouble(), 64.0, 64.0, opacity)
            x += 64
        }
        y += 64
    }
}

fun drawPlatform(platform: Platform) {
    var y = platform.getY()

    while (y < platform.getMaxY()) {
        var x = platform.getX()

        while (x < platform.getMaxX()) {
            if (x == platform.getX() && y == platform.getY()) {
                drawFromCamera(Globals.theme.platformNW, x.toDouble(), y.toDouble(), 64.0, 64.0)
            } else if (x == platform.getX() && y + 64 >= platform.getMaxY()) {
                drawFromCamera(Globals.theme.platformSW, x.toDouble(), y.toDouble(), 64.0, 64.0)
            } else if (x + 64 >= platform.getMaxX() && y == platform.getY()) {
                drawFromCamera(Globals.theme.platformNE, x.toDouble(), y.toDouble(), 64.0, 64.0)
            } else if (x + 64 >= platform.getMaxX() && y + 64 >= platform.getMaxY()) {
                drawFromCamera(Globals.theme.platformSE, x.toDouble(), y.toDouble(), 64.0, 64.0)
            } else if (x == platform.getX()) {
                drawFromCamera(Globals.theme.platformW, x.toDouble(), y.toDouble(), 64.0, 64.0)
            } else if (y == platform.getY()) {
                drawFromCamera(Globals.theme.platformN, x.toDouble(), y.toDouble(), 64.0, 64.0)
            } else if (x + 64 >= platform.getMaxX()) {
                drawFromCamera(Globals.theme.platformE, x.toDouble(), y.toDouble(), 64.0, 64.0)
            } else if (y + 64 >= platform.getMaxY()) {
                drawFromCamera(Globals.theme.platformS, x.toDouble(), y.toDouble(), 64.0, 64.0)
            } else {
                drawFromCamera(Globals.theme.platformC, x.toDouble(), y.toDouble(), 64.0, 64.0)
            }

            x += 64
        }
        y += 64
    }

    for (decoration in platform.decorations) {
        drawFromCamera(decoration.texture, decoration.getX().toDouble(), decoration.getY().toDouble(),
            decoration.getWidth().toDouble(), decoration.getHeight().toDouble())
    }
}

fun drawBridge(bridge: Bridge) {
    if (bridge.getWidth() / 64 == 2) {
        drawFromCamera("bridge0", bridge.getX().toDouble(), bridge.getY().toDouble(), 64.0, 64.0)
        drawFromCamera("bridge1", bridge.getX().toDouble() + 64.0, bridge.getY().toDouble(), 64.0, 64.0)
        return
    }

    var x = bridge.getX()

    while (x < bridge.getMaxX()) {
        drawFromCamera("bridge0", x.toDouble(), bridge.getY().toDouble(), 64.0, 64.0)
        x += 64
    }
}

fun drawPlayer(p: Player, opacity: Double) {
    val image = if (p.frozenImage != null) p.frozenImage!! else getImageForPlayer(p)

    drawFromCameraEx(image.name, p.x.toDouble() + image.xOffset.toDouble(), p.y.toDouble() + image.yOffset.toDouble(),
        p.direction.toDouble() * image.width.toDouble(), image.height.toDouble(), opacity)

    if (!p.swimming && p.parachuting) {
        val parachute = when (Globals.item) {
            Item.CANNON -> "cannoneer_parachute"
            Item.PIRATE -> "pirate_parachute"
            else -> "parachute"
        }

        val parachuteOffset = when (Globals.item) {
            Item.PIRATE -> -10
            else -> 0
        }

        drawFromCamera(parachute, p.x.toDouble(), p.y.toDouble() - 64.0 + parachuteOffset.toDouble(), 64.0, 64.0, opacity)
    }
}

fun drawDeath() {
    val deathImage = Globals.deathImage!!
    drawFromCameraEx(deathImage.name, Globals.deathX + deathImage.xOffset.toDouble(), Globals.deathY + deathImage.yOffset.toDouble(),
        deathImage.width.toDouble(), deathImage.height.toDouble())
}

fun drawCrawler(crawler: Crawler) {
    val size = crawler.getSizeMultiplier().toDouble()
    drawFromCameraEx(crawler.now(), crawler.x.toDouble(), crawler.y.toDouble() - 32.0 * size, crawler.vx.toDouble() * 64.0 * size, 64.0 * size)
}

fun drawSign(s: Sign) {
    drawFromCamera("sign", s.visual.x.toDouble(), s.visual.y.toDouble(), 64.0, 64.0)

    if (s.pressHOpacity != 0.0) {
        drawText("Press H", s.visual.x.toDouble() - 3.0 - Globals.cameraX, s.visual.y.toDouble() - 16.0 - Globals.cameraY, 14.0, s.pressHOpacity)
    }
}

fun drawItemSpawn(itemSpawn: ItemSpawn) {
    if (Globals.item == itemSpawn.item) return
    val r: Region = itemSpawn
    drawFromCamera(getImageForItem(itemSpawn.item), r.getVisualX().toDouble(), r.getVisualY().toDouble(), r.getVisualWidth().toDouble(), r.getVisualHeight().toDouble())
}

fun getImageForPlayer(player: Player): NamedImageEx {
    if (Globals.item == Item.GUN) {
        if (player.swimming)
            return NamedImageEx(Animations.gun_swim.now(), if (player.direction == 1) 0 else -8, -8, 72, 72)

        if (!player.moving)
            return NamedImageEx("gun_idle", if (player.direction == 1) 0 else -48, -8, 112, 72)

        return NamedImageEx(Animations.gun_walk.now(), if (player.direction == 1) 0 else -48, -8, 112, 72)
    }

    if (Globals.item == Item.CANNON) {
        if (player.swimming)
            return NamedImageEx(Animations.cannoneer_swim.now(), if (player.direction == 1) 0 else -8, -8, 72, 72)

        if (!player.moving)
            return NamedImageEx("cannoneer_idle", 0, -24, 64, 88)

        return NamedImageEx(Animations.cannoneer_walk.now(), 0, -24, 64, 88)
    }

    if (Globals.item == Item.PIRATE) {
        if (player.swimming)
            return NamedImageEx(Animations.pirate_swim.now(), if (player.direction == 1) 0 else -8, -8, 72, 72)

        if (player.pirateShootCooldown > 0)
            return NamedImageEx("pirate_shoot", -32 + if (player.direction == 1) 0 else -32, -38, 160, 116)

        if (player.pirateClawCooldown > 0)
            return NamedImageEx("pirate_claw", -32 + (if (player.direction == 1) 0 else -32) + player.direction * 16, -38, 160, 116)

        if (!player.moving)
            return NamedImageEx("pirate_idle", -32 + if (player.direction == 1) 0 else -32, -38, 160, 116)

        return NamedImageEx(Animations.pirate_walk.now(), -32 + if (player.direction == 1) 0 else -32, -38, 160, 116)
    }

    if (!player.moving)
        return NamedImageEx("idle", 0, 0, 64, 64)

    if (player.swimming)
        return NamedImageEx(Animations.swim.now(), 0, 0, 64, 64)

    return NamedImageEx(Animations.walk.now(), 0, 0, 64, 64)
}

fun getImageForItem(item: Item): String {
    return when (item) {
        Item.STAR ->       return "star"
        Item.WING ->       return "wing"
        Item.GUN ->        return "gun"
        Item.CANNON ->     return "cannon_power"
        Item.DOUBLEJUMP -> return "doublejump"
        Item.PIRATE ->     return "pirate"
        else -> "stump"
    }
}
