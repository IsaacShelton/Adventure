
class Bullet(var x: Int, var y: Int, var vx: Int, var vy: Int = 0) {
    fun getAABB(): AABB {
        return AABB(x, y, 8, 8)
    }
}
