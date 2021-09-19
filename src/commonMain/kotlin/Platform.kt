class Platform(x: Int, y: Int, w: Int, h: Int, var decorations: List<Decoration>, var decor_id: Decor)
    : Region(x, y, w, h) {

    constructor(x: Int, y: Int, w: Int, h: Int, decor: Decor)
        : this(x, y, w, h, decorationsForDecor(x, y, w, decor), decor)
}

fun decorationsForDecor(x: Int, y: Int, w: Int, decor: Decor): List<Decoration> {
    val decorations: MutableList<Decoration> = mutableListOf()

    when(decor) {
        Decor.NONE -> {}
        Decor.BUSH -> {
            decorations.add(Decoration(Globals.theme.bushl, x + 64, y - 64, 64, 64))
            decorations.add(Decoration(Globals.theme.bushr, x + 128, y - 64, 64, 64))
        }
        Decor.FLOWERA -> {
            decorations.add(Decoration("flower_a", x + 64, y - 64, 64, 64))
        }
        Decor.FLOWERB -> {
            decorations.add(Decoration("flower_b", x + 64, y - 64, 64, 64))
        }
        Decor.ROCKA -> {
            decorations.add(Decoration("rock_a", x + 64, y - 64, 64, 64))
        }
        Decor.ROCKB -> {
            decorations.add(Decoration("rock_b", x + 128, y - 64, 64, 64))
        }
        Decor.MUSHROOMA -> {
            decorations.add(Decoration("mushroom_a", x + 64, y - 64, 64, 64))
        }
        Decor.MUSHROOMB -> {
            decorations.add(Decoration("mushroom_b", x + w - 128, y - 64, 64, 64))
        }
        Decor.STUMP -> {
            decorations.add(Decoration("stump", x + 64, y - 64, 64, 64))
        }
        Decor.GRASSA -> {
            decorations.add(Decoration("grass_a", x + 64, y - 64, 64, 64))
        }
        Decor.GRASSB -> {
            decorations.add(Decoration("grass_b", x + 64, y - 64, 64, 64))
        }
        Decor.GRASSC -> {
            decorations.add(Decoration("grass_c", x + 64, y - 64, 64, 64))
        }
        Decor.FENCE -> {
            decorations.add(Decoration("fence_a", x + w - 128, y - 64, 64, 64))
            decorations.add(Decoration("fence_b", x + w - 192, y - 64, 64, 64))
        }
    }

    return decorations
}

fun createPlatform(x: Int, y: Int, w: Int, h: Int, decor: Decor) {
    Globals.platforms.add(Platform(x, y, w, h, decor))
}