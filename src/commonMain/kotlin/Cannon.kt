
class Cannon(var x: Int, var y: Int, var vy: Int, var direction: Int) {
    constructor(x: Int, y: Int, direction: Int)
        : this(x, y, 0, direction)

    fun getAABB(adjX: Int = 0, adjY: Int = 0): AABB {
        return AABB(x + adjX + 32, y + adjY, 88, 72)
    }
}