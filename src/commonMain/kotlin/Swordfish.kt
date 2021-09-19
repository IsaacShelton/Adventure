class Swordfish (sourceAnimation: Flipbook, var x: Int, var y: Int, var speed: Int, var interval: Int, reverse: Boolean = false) {
    var vx = if (reverse) -1 else 1
    var animation = sourceAnimation.newInstance()
    var ticker = 0
    var initialY = y
    var sinceSeenPlayer = 1000

    fun update() {
        animation.update()

        if (ticker++ == interval) {
            ticker = 0
            vx *= -1
        }
    }

    fun now(): String {
        return animation.now()
    }

    fun touchingAnother(): Boolean {
        val aabb = getAABB()
        for (other in Globals.swordfishes) {
            if (this == other) continue
            if (aabb.intersecting(other.getAABB())) {
                return true
            }
        }
        return false
    }

    fun getAABB(adjX: Int = 0, adjY: Int = 0): AABB {
        return AABB(x + adjX, y + adjY, 256, 64)
    }
}