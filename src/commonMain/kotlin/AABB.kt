class AABB(var x: Int, var y: Int, var w: Int, var h: Int){
    fun intersecting(other: AABB): Boolean {
        return x < other.x + other.w && x + w > other.x && y < other.y + other.h && y + h > other.y
    }

    fun intersectingPoint(pointX: Int, pointY: Int): Boolean {
        return x <= pointX && x + w >= pointX && y < pointY && y + h >= pointY
    }
}