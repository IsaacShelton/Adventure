import kotlin.math.ceil

fun draw() {
    Globals.autoRenderer!!.render(Globals.theme.sky, ceil(-Globals.skyShift), 0.0, Globals.viewWidth.toDouble(), Globals.viewHeight.toDouble())
    Globals.autoRenderer!!.render(Globals.theme.sky, -Globals.skyShift + Globals.viewWidth, 0.0, Globals.viewWidth.toDouble(), Globals.viewHeight.toDouble())

    if (Globals.scene == -1) {
        // Title screen
        Globals.titleState.draw()
        return
    }

    // Draw background portion of lake
    for (lake in Globals.lakes) drawLakeBehind(lake)

    // Draw exit portal if one exists
    if (Globals.exit != null) {
        val exit = Globals.exit!!
        drawFromCamera(if (Globals.exitDisabled) Animations.disabled_portal.now() else Animations.inwards_portal.now(),
            exit.getVisualX().toDouble(),
            exit.getVisualY().toDouble(),
            exit.getVisualWidth().toDouble(),
            exit.getVisualHeight().toDouble())
    }

    // Draw entrance portal if one exists
    if (Globals.entrance != null) {
        val entrance = Globals.entrance!!
        drawFromCamera(Animations.outwards_portal.now(),
            entrance.getVisualX().toDouble(),
            entrance.getVisualY().toDouble(),
            entrance.getVisualWidth().toDouble(),
            entrance.getVisualHeight().toDouble())
    }

    for (visual in Globals.visuals) drawFromCamera(visual.texture, visual.getX().toDouble(), visual.getY().toDouble(), visual.getWidth().toDouble(), visual.getHeight().toDouble())
    for (sign in Globals.signs) drawSign(sign)
    for (platform in Globals.platforms) drawPlatform(platform)
    for (bridge in Globals.bridges) drawBridge(bridge)
    for (lake in Globals.lakes) drawLakeUpFront(lake)
    for (bullet in Globals.bullets) drawFromCamera("bullet", bullet.x.toDouble(), bullet.y.toDouble(), 8.0, 8.0)

    if (Globals.cannon != null) {
        val cannon = Globals.cannon!!
        drawFromCameraEx("cannon_idle", cannon.x.toDouble(), cannon.y.toDouble(), 152.0 * cannon.direction.toDouble(), 72.0)
    }

    if (Globals.cannonBall != null) {
        var ball = Globals.cannonBall!!
        drawFromCamera("ball", ball.x.toDouble(), ball.y.toDouble(), 32.0, 32.0)
    }

    for (particle in Globals.particles) {
        drawFromCamera(particle.now(), particle.getVisualX().toDouble(), particle.getVisualY().toDouble(), particle.getVisualWidth().toDouble(), particle.getVisualHeight().toDouble())
    }

    if (Globals.portalEnabler != null) {
        val r = Globals.portalEnabler!!
        drawFromCamera(Animations.enabler.now(), r.getVisualX().toDouble(), r.getVisualY().toDouble(), r.getVisualWidth().toDouble(), r.getVisualHeight().toDouble())
    }

    for (itemSpawn in Globals.itemSpawns) drawItemSpawn(itemSpawn)

    Globals.slimeBoss?.draw()
    Globals.witchBoss?.draw()

    for (crawler in Globals.crawlers) drawCrawler(crawler)

    for (swordfish in Globals.swordfishes) {
        drawFromCameraEx(swordfish.now(), swordfish.x.toDouble(), swordfish.y.toDouble(), swordfish.vx.toDouble() * 256.0, 64.0)
    }

    // Draw player shadow for save-state powerup
    if (Globals.item == Item.STAR) drawPlayer(Globals.shadow!!, 0.5)

    // If alive render player otherwise render death animation
    // (and there is no black transition)
    if (Globals.blackOverlay < 0.5) {
        if (Globals.isDead)          drawDeath()
        else if (Globals.scene != 0) drawPlayer(Globals.player!!, if (Globals.teleporting) Globals.teleportOpacity else 1.0)
    }

    if (Globals.scene == 0 || Globals.scene == 1) {
        //drawFromCamera("controls", 576.0 - 224.0, 192.0, 512.0, 256.0)
        drawFromCamera("tutorial", 576.0 - 224.0, 192.0, 512.0, 256.0)
    }

    if (Globals.scene == 2002)
        drawFromCamera("win", 576.0 - 224.0, 192.0 + 32.0, 512.0, 128.0)

    // Draw powerup slot
    if (Globals.scene != 0)
        Globals.autoRenderer!!.render("slot", 1792.0, 32.0, 96.0, 96.0)

    // Draw signboard
    Globals.signboard?.draw()

    // Draw acquired powerup
    if (!Globals.isDead && Globals.item != Item.NONE)
        Globals.autoRenderer!!.render(getImageForItem(Globals.item), Globals.itemScreenX, Globals.itemScreenY, 64.0, 64.0)

    if (Globals.blackOverlay != 0.0) {
        Globals.autoRenderer!!.render("bullet", 0.0, 0.0, Globals.viewWidth.toDouble(), Globals.viewHeight.toDouble(), Globals.blackOverlay)
    }
}
