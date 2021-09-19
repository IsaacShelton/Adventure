
class Player(
    var x: Int,
    var y: Int,
    var vx: Int,
    var vy: Int,
    var direction: Int,
    var swimming: Boolean,
    var moving: Boolean,
    var movingHorizontally: Boolean,
    var parachuting: Boolean,
    var onGround: Region?,
    var frozenImage: NamedImageEx?,
) {
    var pirateShootCooldown: Int = 0
    var pirateClawCooldown: Int = 0

    constructor(x: Int, y: Int, vy: Int = 0)
        : this(x, y, 0, vy, 1, false, false, false, false, null, null)

    fun freeze(currentImage: NamedImageEx): Player {
        return Player(x, y, vx, vy, direction, swimming, moving, movingHorizontally, parachuting, onGround, currentImage)
    }

    fun thaw(): Player {
        return Player(x, y, vx, vy, direction, swimming, moving, movingHorizontally, parachuting, onGround, null)
    }

    fun getAABB(adjX: Int = 0, adjY: Int = 0): AABB {
        return AABB(x + adjX, y + adjY, 64 ,64)
    }
}