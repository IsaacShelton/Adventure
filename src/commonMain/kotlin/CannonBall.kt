class CannonBall(var x: Int, var y: Int, var vx: Int) {
    fun getAABB(): AABB {
        return AABB(x, y, 32, 32)
    }
}