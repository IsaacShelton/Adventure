
fun gotoScene(newScene: Int) {
    when (newScene) {
        1 -> gotoScene1()
        2 -> gotoScene2()
        3 -> gotoScene3()
        4 -> gotoScene4()
        5 -> gotoScene5()
        6 -> gotoScene6()
        7 -> gotoScene7()
        8 -> gotoScene8()
        9 -> gotoScene9()
        10 -> gotoScene10()
        11 -> gotoScene11()
        12 -> gotoScene12()
        13 -> gotoScene13()
        14 -> gotoScene14()
        15 -> gotoScene15()
        16 -> gotoScene16()
        17 -> gotoScene17()
        18 -> gotoScene18()
        19 -> gotoScene19()
        else -> gotoWinScreen()
    }
}

fun scenePresets() {
    Globals.cameraX = 0.0
    Globals.cameraY = 0.0
    Globals.isDead = false
    Globals.respawnTimer = 0
    Globals.platforms.clear()
    Globals.lakes.clear()
    Globals.bridges.clear()
    Globals.crawlers.clear()
    Globals.swordfishes.clear()
    Globals.item = Item.NONE
    Globals.itemSpawns.clear()
    Globals.signs.clear()
    Globals.visuals.clear()
    Globals.entrance = null
    Globals.exit = null
    Globals.exitDisabled = false
    Globals.portalEnabler = null
    Globals.particles.clear()
    Globals.bullets.clear()
    Globals.cannon = null
    Globals.cannonBall = null
    Globals.slimeBoss = null
    Globals.witchBoss = null
    Globals.theme = Themes.ONE
}

fun sceneDefaultShadows() {
    Globals.shadow = Globals.player!!.freeze(NamedImageEx("idle", 0, 0, 64, 64))
    Globals.defaultShadow = Globals.player!!.freeze(NamedImageEx("idle", 0, 0, 64, 64))
}

fun gotoTitleScreen() {
    scenePresets()
    Globals.scene = -1
}

fun gotoScene1() {
    scenePresets()
    Globals.scene = 1
    Globals.sceneWidth = 2560.0
    Globals.player = Player(640 - 64, 0, 10)
    Globals.blackOverlay = 1.0
    sceneDefaultShadows()
    createPlatform(512, 544, 256, 256, Decor.BUSH)
    createPlatform(1088, 320, 256, 256, Decor.NONE)
    createPlatform(64, 704, 256, 128, Decor.NONE)
    createPlatform(1920, 480, 256, 512, Decor.NONE)
    Globals.lakes.add(Lake(0, 768, 2560, 512))
    Globals.bridges.add(Bridge(256 + 64, 704, 192))
    Globals.bridges.add(Bridge(256 + 256 + 256 + 192, 320 + 192, 128))
    Globals.bridges.add(Bridge(1920 + 256, 480 + 32, 256))
    Globals.exit = Portal(1920 + 64, 480 - 128)
    Globals.signs.add(Sign(1088 + 128, 320 - 64, "Hold 'space' to parachute"))
}

fun gotoScene2() {
    scenePresets()
    Globals.scene = 2
    Globals.sceneWidth = 5120.0
    Globals.player = Player(640 - 64, 544 - 64)
    sceneDefaultShadows()
    createPlatform(512, 544, 256, 640, Decor.NONE)
    createPlatform(1298 + 192, 544, 256, 384, Decor.FLOWERA)
    createPlatform(1984, 384, 512, 192, Decor.NONE)
    createPlatform(3072, 256, 256, 256, Decor.FLOWERA)
    createPlatform(4608, 512, 384, 512, Decor.NONE)
    createPlatform(3712, 512, 512, 448, Decor.BUSH)
    Globals.lakes.add(Lake(0, 768, 5120, 512))
    Globals.bridges.add(Bridge(384, 704, 128))
    Globals.bridges.add(Bridge(3328, 384, 128))
    Globals.bridges.add(Bridge(4544, 704, 64))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 1298 + 192, 544 - 32))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 4608, 512 - 32))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 3712, 512 - 32))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 3712 + 256, 512 - 32))
    Globals.swordfishes.add(Swordfish(Animations.swordfish, 2048, 1024, 4, 180))
    Globals.entrance = Portal(Globals.player!!.x - 32, Globals.player!!.y - 64)
    Globals.exit = Portal(2048, 256)
    Globals.signs.add(Sign(4608 + 64, 512 - 64, "Kill enemies by squishing\nthem under your weight"))
}

fun gotoScene3() {
    scenePresets()
    Globals.scene = 3
    Globals.sceneWidth = 5120.0
    Globals.player = Player(320 - 64, 544 - 64)
    sceneDefaultShadows()
    createPlatform(128, 544, 640, 320, Decor.FENCE)
    createPlatform(1024, 480, 512, 192, Decor.MUSHROOMB)
    createPlatform(2048, 544, 256, 704, Decor.MUSHROOMA)
    createPlatform(3072, 544, 320, 192, Decor.MUSHROOMB)
    createPlatform(4160, 576, 640, 384, Decor.BUSH)
    Globals.lakes.add(Lake(0, 768, 5120, 512))
    Globals.bridges.add(Bridge(128 + 640, 544 + 192, 128))
    Globals.bridges.add(Bridge(2048 + 256, 544 + 192, 128))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 2048, 544 - 32, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 4160, 576 - 32, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 4160 + 128, 576 - 32, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 4160 + 384, 576 - 32, 2))
    Globals.swordfishes.add(Swordfish(Animations.swordfish, 512 + 4 * 240, 1024, 4, 240, true))
    Globals.swordfishes.add(Swordfish(Animations.swordfish, 2048 + 512, 896, 4, 270, false))
    Globals.swordfishes.add(Swordfish(Animations.swordfish, 2048 + 512 + 1024, 1024, 4, 240, true))
    Globals.swordfishes.add(Swordfish(Animations.swordfish, 4160, 1024, 4, 90, false))
    Globals.swordfishes.add(Swordfish(Animations.swordfish, 4160 - 64 + 90 * 4, 1088, 4, 90, true))
    Globals.swordfishes.add(Swordfish(Animations.swordfish, 4160 + 64, 1184, 4, 90, false))
    Globals.itemSpawns.add(ItemSpawn(Item.WING, 1024 + 64, 480 - 64))
    Globals.entrance = Portal(Globals.player!!.x - 32, Globals.player!!.y - 64)
    Globals.exit = Portal(4864, 1280 - 128)
    Globals.signs.add(Sign(1024 + 256, 480 - 64, "wing powerup:\n\nPassive speed increase\nPress 'j' to super jump\n\n\nDon't forget to parachute"))
}

fun gotoScene4() {
    scenePresets()
    Globals.scene = 4
    Globals.sceneWidth = 5120.0
    Globals.player = Player(640 - 64, 544 - 64)
    sceneDefaultShadows()
    createPlatform(512, 544, 256, 384, Decor.NONE)
    createPlatform(1088, 480, 256, 256, Decor.NONE)
    createPlatform(1856, 384, 256, 768, Decor.FLOWERA)
    createPlatform(2752, 320, 256, 832, Decor.BUSH)
    createPlatform(3584, 512, 256, 640, Decor.STUMP)
    createPlatform(4352, 384, 576, 704, Decor.NONE)
    Globals.lakes.add(Lake(0, 768, 5120, 512))
    Globals.bridges.add(Bridge(512 + 256, 704, 128))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 1856 + 192, 384 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2752, 320 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2752 + 128, 320 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2752 + 128, 320 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 3584, 512 - 32, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 4352, 384 - 32, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 4352 + 256, 384 - 32, 2))
    Globals.itemSpawns.add(ItemSpawn(Item.WING, 1088 + 96, 480 - 64))
    Globals.entrance = Portal(Globals.player!!.x - 32, Globals.player!!.y - 64)
    Globals.exit = Portal(4736, 256)
}

fun gotoScene5() {
    scenePresets()
    Globals.scene = 5
    Globals.sceneWidth = 2560.0
    Globals.player = Player(640 - 64, 544 - 64)
    sceneDefaultShadows()
    createPlatform(256 + 256, 544, 256, 256, Decor.NONE)
    createPlatform(64, 704, 256, 128, Decor.FLOWERA)
    createPlatform(1024, 384 + 32, 640, 832, Decor.BUSH)
    createPlatform(2112, 576, 256, 320, Decor.NONE)
    Globals.lakes.add(Lake(0, 768, 2560, 512))
    Globals.bridges.add(Bridge(256 + 64, 704, 192))
    Globals.swordfishes.add(Swordfish(Animations.swordfish, 1792, 1024, 4, 90, false))
    Globals.itemSpawns.add(ItemSpawn(Item.STAR, 192 + 32, 704 - 64))
    Globals.entrance = Portal(Globals.player!!.x - 32, Globals.player!!.y - 64)
    Globals.exit = Portal(1024 + 320, 384 + 32 - 128)
    Globals.exitDisabled = true
    Globals.portalEnabler = PortalEnabler(2112 + 128, 576 - 64, 64, 64)
    Globals.signs.add(Sign(1024 + 192 + 32, 384 + 32 - 64, "Blue star powerup:\n\nPress 'J' to remember\nPress 'K' to return"))
}

fun gotoScene6() {
    scenePresets()
    Globals.scene = 6
    Globals.sceneWidth = 5120.0
    Globals.player = Player(640 - 64, 544 - 64)
    sceneDefaultShadows()
    createPlatform(256, 544, 512, 384, Decor.BUSH)
    createPlatform(1088, 480, 256, 256, Decor.NONE)
    createPlatform(1792, 480, 320, 256, Decor.FLOWERA)
    createPlatform(2496, 480, 320, 256, Decor.BUSH)
    createPlatform(2976, 352, 768, 256, Decor.FENCE)
    createPlatform(4224, 384, 704, 256, Decor.FLOWERA)
    Globals.lakes.add(Lake(0, 768, 5120, 512))
    Globals.bridges.add(Bridge(512 + 256, 704, 128))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 1792, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 1792 + 128, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 1792 + 256, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2496, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2496 + 128, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2496 + 256, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2976, 352 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2976 + 128, 352 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2976 + 256, 352 - 32, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2976 + 384, 352 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2976 + 512, 352 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2976 + 640, 352 - 32, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2976 + 768, 352 - 32, 1))
    Globals.itemSpawns.add(ItemSpawn(Item.GUN, 1088 + 96, 480 - 64))
    Globals.entrance = Portal(Globals.player!!.x - 32, Globals.player!!.y - 64)
    Globals.exit = Portal(4736, 256)
}

fun gotoScene7() {
    scenePresets()
    Globals.scene = 7
    Globals.sceneWidth = 5120.0
    Globals.player = Player(640 - 64, 480 - 64)
    sceneDefaultShadows()
    createPlatform(448, 480, 320, 384, Decor.MUSHROOMB)
    createPlatform(1088, 480, 256, 256, Decor.NONE)
    createPlatform(1792, 480, 320, 512, Decor.MUSHROOMA)
    createPlatform(2496, 480, 320, 256, Decor.BUSH)
    createPlatform(2976, 352, 320, 512, Decor.MUSHROOMA)
    createPlatform(3456, 512, 640, 512, Decor.FENCE)
    createPlatform(4224, 384, 704, 256, Decor.MUSHROOMA)
    Globals.lakes.add(Lake(0, 768, 5120, 512))
    Globals.bridges.add(Bridge(512 + 256, 640, 128))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 1792, 480 - 32, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 1792 + 128, 480 - 32, 2, true))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 1792 + 128, 480 - 32, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 1792 + 256, 480 - 32, 2, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2496, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2496 + 128, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2496 + 256, 480 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2496 + 128, 480 - 32, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 2496 + 256, 480 - 32, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 3456, 512 - 32, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 3456 + 128, 512 - 32, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 3456 + 480, 512 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 3456 + 512, 512 - 32, 2, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SPIDER, 3456 + 128, 512 - 32, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.BEETLE, 3456 + 384, 512 - 32, 2, true))
    Globals.itemSpawns.add(ItemSpawn(Item.CANNON, 1088 + 96, 480 - 64))
    Globals.entrance = Portal(Globals.player!!.x - 32, Globals.player!!.y - 64)
    Globals.exit = Portal(4736, 256)
}

fun gotoScene8() {
    scenePresets()
    Globals.theme = Themes.TWO
    Globals.scene = 8
    Globals.sceneWidth = 5120.0
    Globals.player = Player(448, 256)
    sceneDefaultShadows()
    createPlatform(128, 320, 704, 256, Decor.NONE)
    createPlatform(1152, 512, 640, 512, Decor.NONE)
    createPlatform(1952, 352, 320, 512, Decor.NONE)
    createPlatform(2432, 480, 320, 256, Decor.NONE)
    createPlatform(3136, 480, 320, 512, Decor.NONE)
    createPlatform(3904, 480, 256, 256, Decor.NONE)
    createPlatform(4480, 480, 320, 384, Decor.NONE)
    Globals.lakes.add(Lake(0, 768, 5120, 512))
    Globals.bridges.add(Bridge(1024, 640, 128))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 3392, 448, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 3264, 448, 2, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 3264, 448, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 3136, 448, 2, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2688, 448, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2560, 448, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2432, 448, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2560, 448, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2432, 448, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1728, 480, 2))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1600, 480, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1248, 480, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1216, 480, 2, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1600, 480, 1))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1344, 480, 2, true))
    Globals.exit = Portal(4576, 352)
    Globals.entrance = Portal(416, 192)
    Globals.signs.add(Sign(672, 192 + 64, "The swamps"))
    Globals.visuals.add(Visual("tree",1152 + 128, 512 - 512, 512, 512))
}

fun gotoScene9() {
    scenePresets()
    Globals.scene = 9
    Globals.sceneWidth = 3840.0
    Globals.theme = Themes.TWO
    createPlatform(1088, 512, 640, 384, Decor.FENCE)
    createPlatform(128, 512, 640, 192, Decor.MUSHROOMA)
    createPlatform(1920, 448, 768, 192, Decor.MUSHROOMB)
    createPlatform(2944, 704, 640, 128, Decor.BUSH)
    Globals.lakes.add(Lake(0, 768, 3840, 512))
    Globals.bridges.add(Bridge(960, 704, 128))
    Globals.bridges.add(Bridge(768, 576, 128))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1472, 480, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1344, 480, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2496, 416, 1, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 1152, 480, 1, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2112, 416, 1, false))
    Globals.exitDisabled = false
    Globals.player = Player(416, 448)
    sceneDefaultShadows()
    Globals.entrance = Portal(384, 384)
    Globals.exit = Portal(3328, 576)
}

fun gotoScene10() {
    scenePresets()
    Globals.scene = 10
    Globals.sceneWidth = 3840.0
    Globals.theme = Themes.TWO
    createPlatform(384, 704, 576, 128, Decor.MUSHROOMB)
    createPlatform(1216, 640, 256, 256, Decor.BUSH)
    createPlatform(1728, 704, 576, 128, Decor.FENCE)
    Globals.visuals.add(Visual("tree", 1728, 704 - 512, 512, 512))
    createPlatform(2496, 640, 256, 256, Decor.BUSH)
    createPlatform(2944, 576, 704, 256, Decor.MUSHROOMA)
    Globals.lakes.add(Lake(0, 768, 3840, 512))
    Globals.exitDisabled = false
    Globals.player = Player(544, 640)
    sceneDefaultShadows()
    Globals.entrance = Portal(512, 576)
    Globals.exit = Portal(3328, 448)
}

fun gotoScene11() {
    scenePresets()
    Globals.scene = 11
    Globals.sceneWidth = 1920.0
    Globals.theme = Themes.TWO
    createPlatform(192, 704, 1536, 128, Decor.BUSH)
    createPlatform(1344, 1152, 256, 256, Decor.NONE)
    Globals.lakes.add(Lake(0, 768, 1920, 512))
    Globals.exitDisabled = true
    Globals.player = Player(416, 640)
    sceneDefaultShadows()
    Globals.entrance = Portal(384, 576)
    Globals.exit = Portal(1408, 1024)
    Globals.slimeBoss = SlimeBoss(416 + 768, 480)
}

fun gotoScene12() {
    scenePresets()
    Globals.scene = 12
    Globals.sceneWidth = 6912.0
    Globals.theme = Themes.TWO
    createPlatform(1152, 384, 512, 192, Decor.MUSHROOMB)
    createPlatform(64, 576, 704, 320, Decor.FENCE)
    createPlatform(2048, 640, 640, 192, Decor.BUSH)
    createPlatform(2816, 320, 256, 1024, Decor.NONE)
    createPlatform(3456, 640, 512, 256, Decor.BUSH)
    createPlatform(4224, 320, 256, 832, Decor.BUSH)
    createPlatform(4736, 320, 768, 192, Decor.FENCE)
    createPlatform(6272, 320, 512, 192, Decor.NONE)
    Globals.lakes.add(Lake(0, 768, 6912, 512))
    Globals.bridges.add(Bridge(1024, 512, 128))
    Globals.bridges.add(Bridge(768, 704, 128))
    Globals.bridges.add(Bridge(1920, 704, 128))
    Globals.bridges.add(Bridge(1664, 512, 128))
    Globals.bridges.add(Bridge(3328, 704, 128))
    Globals.bridges.add(Bridge(4480, 320, 256))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 128, 544, 1, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 192, 544, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 256, 544, 1, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 320, 544, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 384, 544, 1, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 448, 544, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 512, 544, 1, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 576, 544, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 640, 544, 1, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 704, 544, 1, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 64, 544, 1, true))
    Globals.swordfishes.add(Swordfish(Animations.loch, 1024, 1024, 2, 240, false))
    Globals.swordfishes.add(Swordfish(Animations.loch, 3136, 960, 2, 390, false))
    Globals.itemSpawns.add(ItemSpawn(Item.DOUBLEJUMP, 2432, 576))
    Globals.signs.add(Sign(2432 - 128, 576, "Double jump powerup:\n\nJump again while in mid-air"))
    Globals.signs.add(Sign(4800, 256, "Hint\n\nParachuting can be mixed with\ndouble jumping to travel\ngreater distances."))
    Globals.exitDisabled = false
    Globals.player = Player(1376, 320)
    sceneDefaultShadows()
    Globals.entrance = Portal(1344, 256)
    Globals.exit = Portal(6464, 192)
    updateCamera()
}

fun gotoScene13() {
    scenePresets()
    Globals.scene = 13
    Globals.sceneWidth = 4288.0
    Globals.theme = Themes.TWO
    createPlatform(192, 320, 512, 576, Decor.MUSHROOMA)
    createPlatform(1344, 512, 768, 192, Decor.FENCE)
    createPlatform(896, 448, 128, 128, Decor.MUSHROOMB)
    createPlatform(2432, 448, 896, 448, Decor.MUSHROOMB)
    createPlatform(2560, 256, 576, 128, Decor.NONE)
    createPlatform(3712, 448, 256, 128, Decor.NONE)
    Globals.lakes.add(Lake(-512, 768, 4800, 512))
    Globals.bridges.add(Bridge(768, 448, 128))
    Globals.bridges.add(Bridge(704, 640, 64))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 1472, 480, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 1664, 480, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 1792, 480, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 1344, 480, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 1920, 480, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 2496, 416, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 2816, 416, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 3136, 416, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 3008, 416, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 2624, 416, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2624, 224, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 2880, 224, 3, true))
    Globals.crawlers.add(Crawler(CrawlerKind.SLIME, 3008, 224, 2, true))
    Globals.swordfishes.add(Swordfish(Animations.loch, 384, 1088, 2, 480, false))
    Globals.exitDisabled = false
    Globals.player = Player(416, 256)
    sceneDefaultShadows()
    Globals.entrance = Portal(384, 192)
    Globals.exit = Portal(3776, 320)
    updateCamera()
}

fun gotoScene14() {
    scenePresets()
    Globals.scene = 14
    Globals.sceneWidth = 6336.0
    Globals.theme = Themes.TWO
    createPlatform(256, 384, 448, 960 - 192, Decor.BUSH)
    createPlatform(1216, 512, 192, 320, Decor.NONE)
    createPlatform(1536, -64, 192, 512, Decor.NONE)
    createPlatform(1728, 576, 640, 256, Decor.BUSH)
    createPlatform(2496, 256, 256, 576, Decor.MUSHROOMA)
    createPlatform(2944, 576, 832, 256, Decor.NONE)
    createPlatform(2880, -64, 192, 512, Decor.NONE)
    createPlatform(4800, 448, 256, 384, Decor.BUSH)
    createPlatform(4160, 320, 256, 192, Decor.MUSHROOMB)
    createPlatform(5312, 576, 832, 256, Decor.MUSHROOMA)
    createPlatform(5248 - 64, -64, 128, 512, Decor.NONE)
    Globals.bridges.add(Bridge(1216 - 64, 512 + 192, 64))
    Globals.lakes.add(Lake(0, 768, 6336, 512))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 2112, 544, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 1856, 544, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 3328, 544, 2, true))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 3648, 544, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 3072, 544, 2, true))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 4224, 288, 2, false))
    Globals.crawlers.add(Crawler(CrawlerKind.FROG, 5568 + 192, 544, 2, false))
    Globals.swordfishes.add(Swordfish(Animations.loch, 768, 1088, 4, 120, false))
    Globals.swordfishes.add(Swordfish(Animations.loch, 1664, 1088, 7, 240, false))
    Globals.swordfishes.add(Swordfish(Animations.loch, 4032, 1088, 6, 150, false))
    Globals.swordfishes.add(Swordfish(Animations.loch, 5440, 1088, 6, 90, false))
    Globals.itemSpawns.add(ItemSpawn(Item.DOUBLEJUMP, 1280, 448))
    Globals.exitDisabled = false
    Globals.player = Player(544, 320)
    sceneDefaultShadows()
    Globals.entrance = Portal(512, 256)
    Globals.exit = Portal(5888, 448)
    updateCamera()
}

fun gotoScene15() {
    scenePresets()
    Globals.scene = 15
    Globals.sceneWidth = 3840.0
    Globals.theme = Themes.TWO
    createPlatform(192, 512, 640, 128, Decor.BUSH)
    createPlatform(1088, 512, 256, 384, Decor.FENCE)
    createPlatform(1600, 512, 640, 128, Decor.MUSHROOMB)
    createPlatform(2496, 512, 256, 384, Decor.FENCE)
    createPlatform(3008, 512, 640, 128, Decor.BUSH)
    Globals.lakes.add(Lake(0, 768, 3840, 512))
    Globals.bridges.add(Bridge(1024, 704, 64))
    Globals.exitDisabled = false
    Globals.player = Player(480, 448)
    sceneDefaultShadows()
    Globals.entrance = Portal(448, 384)
    Globals.exit = Portal(3328, 384)
    updateCamera()
}

fun gotoScene16() {
    scenePresets()
    Globals.scene = 16
    Globals.sceneWidth = 1920.0
    Globals.theme = Themes.TWO
    createPlatform(64, 832, 1792, 192, Decor.NONE)
    Globals.lakes.add(Lake(0, 896, 1920, 384))
    Globals.itemSpawns.add(ItemSpawn(Item.DOUBLEJUMP, 928, 768))
    Globals.exitDisabled = true
    Globals.player = Player(160, 768)
    sceneDefaultShadows()
    Globals.entrance = Portal(128, 704)
    Globals.exit = Portal(1664, 704)
    updateCamera()
    Globals.witchBoss = WitchBoss(416.0 + 768.0, 640.0)
}

fun gotoScene17() {
    scenePresets()
    Globals.scene = 17
    Globals.sceneWidth = 3840.0 + 64.0
    Globals.theme = Themes.THREE
    createPlatform(-64, 768 - 256, 704 + 64, 256 + 256, Decor.ROCKB)
    createPlatform(2240, 768, 512, 512 + 64, Decor.NONE)
    createPlatform(1600, 320, 320, 128, Decor.NONE)
    createPlatform(3584, 448, 256, 384, Decor.NONE)
    createPlatform(2880, 320, 192, 128, Decor.NONE)
    createPlatform(2688, 704, 576, 576, Decor.NONE)
    createPlatform(-64, 1152, 704, 128 + 128, Decor.NONE)
    createPlatform(-64, 832, 128, 320, Decor.NONE)
    createPlatform(448, 1088, 256, 192 + 128, Decor.NONE)
    Globals.lakes.add(Lake(704, 768, 1536, 512))
    Globals.lakes.add(Lake(3264, 768, 576 + 64, 512))
    Globals.bridges.add(Bridge(2176, 576, 64))
    Globals.bridges.add(Bridge(2176, 384, 64))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 2560 + 192, 736 - 32, 3, false))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 2944 + 128, 672 - 32, 3, true))
    Globals.swordfishes.add(Swordfish(Animations.shark, 832, 1088 + 64, 3, 450, false))
    Globals.signs.add(Sign(512, 704 - 256, "the beach"))
    Globals.signs.add(Sign(128, 1088, "andrew + abby"))
    Globals.visuals.add(Visual("nook", 0, 1024, 704,128))
    Globals.visuals.add(Visual("platform_center_th3", 704 - 64, 1024 - 64, 64, 64))
    Globals.visuals.add(Visual("platform_center_th3", 704 - 64, 1024 + 64, 64, 64))
    Globals.visuals.add(Visual("platform_center_th3", 0, 1024 + 64, 64, 64))
    Globals.itemSpawns.add(ItemSpawn(Item.STAR, 1728, 256))
    Globals.exitDisabled = false
    val defaultShadowPlayer = Player(2944, 192 + 64)
    Globals.shadow = defaultShadowPlayer.freeze(NamedImageEx("idle", 0, 0, 64, 64))
    Globals.defaultShadow = defaultShadowPlayer.freeze(NamedImageEx("idle", 0, 0, 64, 64))
    Globals.player = Player(224, 704 - 256)
    Globals.entrance = Portal(192, 640 - 256)
    Globals.exit = Portal(3648, 320)
    updateCamera()
}

fun gotoScene18() {
    scenePresets()
    Globals.scene = 18
    Globals.sceneWidth = 8384.0
    Globals.theme = Themes.THREE
    createPlatform(64, 512, 256, 448, Decor.NONE)
    createPlatform(2816, 704, 704, 576, Decor.STUMP)
    createPlatform(4288, 704, 704, 576, Decor.FENCE)
    createPlatform(4288, 320, 448, 256, Decor.BUSH)
    createPlatform(5888, 640, 896, 256, Decor.NONE)
    createPlatform(7552, 640, 832, 192, Decor.NONE)
    Globals.lakes.add(Lake(0, 768, 2560 + 256, 512))
    Globals.lakes.add(Lake(3520, 768 - 64, 768, 512 + 64))
    Globals.lakes.add(Lake(4992, 704, 3392, 576))
    Globals.bridges.add(Bridge(4736, 512, 128))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 2880, 672 - 32, 3, false))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 3264 - 128, 672 - 32, 3, false))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 6080 + 128, 608 - 32, 3, false))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 6464, 608 - 32, 3, false))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 7680, 608 - 32, 3, false))
    Globals.swordfishes.add(Swordfish(Animations.shark, 5888, 960 + 32, 5, 120, false))
    Globals.swordfishes.add(Swordfish(Animations.shark, 6528, 1152 - 32, 5, 120, true))
    Globals.swordfishes.add(Swordfish(Animations.shark, 2048, 1088 + 64, 5, 240, true))
    Globals.portalEnabler = PortalEnabler(8128, 512, 64, 64)
    Globals.exitDisabled = true
    Globals.player = Player(96 + 64, 576 - 128)
    sceneDefaultShadows()
    Globals.entrance = Portal(64 + 64, 512 - 128)
    Globals.exit = Portal(4544, 192)
    updateCamera()
}

fun gotoScene19() {
    scenePresets()
    Globals.scene = 19
    Globals.sceneWidth = 8768.0
    Globals.theme = Themes.THREE
    createPlatform(128, 576, 576, 128, Decor.STUMP)
    createPlatform(1536, 704, 384, 128, Decor.NONE)
    createPlatform(1856, 640 - 64, 768, 192 + 64, Decor.NONE)
    createPlatform(2560, 704, 1024, 128, Decor.ROCKB)
    createPlatform(3520, 576, 256, 256, Decor.BUSH)
    createPlatform(7744, 320, 640, 128, Decor.BUSH)
    createPlatform(3712, 640, 1408 + 384, 192, Decor.FENCE)
    Globals.lakes.add(Lake(0, 768, 8768, 512))
    Globals.bridges.add(Bridge(5632, 576, 448 * 2))
    Globals.bridges.add(Bridge(6528, 448, 448 * 2))
    Globals.bridges.add(Bridge(7424, 320, 320))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 2880, 672 - 32, 3, true))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 3456, 672 - 32, 3, false))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 2240, 608 - 64 - 32, 3, false))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 3840, 608 - 32, 3, true))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 4224, 608 - 32, 3, false))
    Globals.crawlers.add(Crawler(CrawlerKind.CRAB, 4672, 608 - 32, 3, false))
    Globals.itemSpawns.add(ItemSpawn(Item.PIRATE, 1664, 640))
    Globals.exitDisabled = false
    Globals.player = Player(352, 512)
    sceneDefaultShadows()
    Globals.entrance = Portal(320, 448)
    Globals.exit = Portal(8128, 192)
    updateCamera()
}

fun gotoThemeTestScreen() {
    scenePresets()
    Globals.scene = 2004
    Globals.sceneWidth = 2560.0
    Globals.player = Player(640 - 64, 544 - 64)
    sceneDefaultShadows()
    createPlatform(512, 544, 256, 256, Decor.BUSH)
    createPlatform(1088, 320, 256, 256, Decor.MUSHROOMB)
    createPlatform(64, 704, 256, 128, Decor.FLOWERA)
    Globals.lakes.add(Lake(0, 768, 2560, 512))
    Globals.bridges.add(Bridge(256 + 64, 704, 192))
    Globals.bridges.add(Bridge(256 + 256 + 256 + 192, 320 + 192, 128))
    Globals.exit = Portal(1920 + 64, 480 - 128)
}

fun gotoWinScreen() {
    scenePresets()
    Globals.scene = 2002
    Globals.sceneWidth = 2560.0
    Globals.theme = Themes.ONE
    Globals.player = Player(640 - 64, 544 - 64)
    sceneDefaultShadows()
    createPlatform(128, 544, 896, 384, Decor.BUSH)
    createPlatform(1152, 256, 256, 384, Decor.FLOWERA)
    createPlatform(1920, 480, 256, 512, Decor.MUSHROOMB)
    Globals.lakes.add(Lake(0, 768, 2560, 512))
    Globals.bridges.add(Bridge(1408, 448, 128))
    Globals.bridges.add(Bridge(1920 + 256, 480 + 32, 256))
    Globals.bridges.add(Bridge(1920 - 128, 640, 128))
    Globals.itemSpawns.add(ItemSpawn(Item.WING, Globals.player!!.x, Globals.player!!.y - 128))
    Globals.itemSpawns.add(ItemSpawn(Item.GUN, 384, 544 - 64))
    Globals.itemSpawns.add(ItemSpawn(Item.CANNON, 768, 544 - 64))
    Globals.itemSpawns.add(ItemSpawn(Item.DOUBLEJUMP, 384, 128))
    Globals.itemSpawns.add(ItemSpawn(Item.STAR, 768, 128))
    Globals.itemSpawns.add(ItemSpawn(Item.PIRATE, Globals.player!!.x, 64))
    Globals.entrance = Portal(Globals.player!!.x - 32, Globals.player!!.y - 64)
    Globals.exit = null
}

fun pause() {
    Globals.isPaused = !Globals.isDead && !Globals.isPaused
}

fun teleport() {
    Globals.teleporting = true
    Globals.teleportOpacity = 1.0
}
