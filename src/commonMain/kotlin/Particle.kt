class Particle(sourceAnimation: Flipbook, x: Int, y: Int, w: Int, h: Int) : Region(x, y, w, h) {
    var animation = sourceAnimation.newInstance()

    fun update(): Boolean {
        if (animation.atEnd()) return true
        animation.update()
        return false
    }

    fun now(): String {
        return animation.now()
    }
}