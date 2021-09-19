open class Region(var collision: AABB, var visual: AABB) {

    constructor(x: Int, y: Int, w: Int, h: Int)
        : this(AABB(x, y, w, h), AABB(x, y, w, h))

    constructor(x: Int, y: Int, w: Int, h: Int, x2: Int, y2: Int, w2: Int, h2: Int)
            : this(AABB(x, y, w, h), AABB(x2, y2, w2, h2))

    fun getX(): Int = collision.x
    fun getY(): Int = collision.y
    fun getMaxX(): Int = collision.x + collision.w
    fun getMaxY(): Int = collision.y + collision.h
    fun getVisualX(): Int = visual.x
    fun getVisualY(): Int = visual.y
    fun getVisualMaxX(): Int = visual.x + visual.w
    fun getVisualMaxY(): Int = visual.y + visual.h
    fun getWidth(): Int = collision.w
    fun getHeight(): Int = collision.h
    fun getVisualWidth(): Int = visual.w
    fun getVisualHeight(): Int = visual.h
}