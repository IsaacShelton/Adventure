import kotlin.math.max

class Crawler(
    var kind: CrawlerKind,
    var x: Int,
    var y: Int,
    var vx: Int,
    var vy: Int,
    var speed: Int,
    var animation: Flipbook,
    private var hp: Int
){
    constructor(kind: CrawlerKind, x: Int, y: Int, speed: Int, reverse: Boolean)
            : this(kind, x, y, if (reverse) -1 else 1, 0, speed, getCrawlerAnimationFor(kind), getDefaultCrawlerHPFor(kind))

    constructor(kind: CrawlerKind, x: Int, y: Int)
        : this(kind, x, y, 1, false)

    constructor(kind: CrawlerKind, x: Int, y: Int, speed: Int)
            : this(kind, x, y, speed, false)

    fun update() {
        animation.update()
    }

    fun hurt(damage: Int): Boolean {
        // Returns whether should die
        hp = max(0, hp - damage)
        return hp == 0
    }

    fun now(): String {
        return animation.now()
    }

    fun getAABB(adjX: Int = 0, adjY: Int = 0): AABB {
        val size = getSizeMultiplier()
        return AABB(x + adjX, y + adjY, 64 * size, 32 * size)
    }

    fun getSizeMultiplier(): Int {
        return if (kind == CrawlerKind.CRAB) 2 else 1
    }
}

fun getCrawlerAnimationFor(kind: CrawlerKind): Flipbook {
    return when(kind){
        CrawlerKind.SPIDER -> Animations.spider_animation.newInstance()
        CrawlerKind.BEETLE -> Animations.beetle_animation.newInstance()
        CrawlerKind.SLIME -> Animations.slime_animation.newInstance()
        CrawlerKind.FROG -> Animations.frog_animation.newInstance()
        CrawlerKind.CRAB -> Animations.crab_animation.newInstance()
    }
}

fun getDefaultCrawlerHPFor(kind: CrawlerKind): Int {
    return when(kind){
        CrawlerKind.CRAB -> 2
        else -> 1
    }
}
