import kotlin.math.abs

class Sign(x: Int, y: Int, var text: String) : Region(x, y, 64, 64) {
    var pressHOpacity = 0.0

    fun inRange(): Boolean {
        return abs(collision.x - Globals.player!!.x) < 80 && abs(collision.y - Globals.player!!.y) < 80
    }

    fun read() {
        if (Globals.signboard != null) return
        Globals.signboard = SignBoard(visual.x.toDouble(), visual.y.toDouble(), text)
        Globals.playSound(Sounds.signopen)
    }
}