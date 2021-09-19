import kotlin.math.abs

class SignBoard(worldX: Double, worldY: Double, var message: String){
    var signX = worldX
    var signY = worldY
    var x = worldX - Globals.cameraX
    var y = worldY - Globals.cameraY
    var w = 64.0
    var h = 64.0
    var closing = false

    fun update() {
        if (closing) {
            x = if(abs(signX - Globals.cameraX - x) < 2.0) signX - Globals.cameraX else (x + signX - Globals.cameraX) / 2.0
            y = if(abs(signY - Globals.cameraY - y) < 2.0) signY - Globals.cameraY else (y + signY - Globals.cameraY) / 2.0
            w = if(abs(w - 64.0) < 2.0) 64.0 else (w + 64.0) / 2.0
            h = if(abs(h - 64.0) < 2.0) 64.0 else (h + 64.0) / 2.0
        } else {
            val targetSize = 3.0 * Globals.viewHeight / 4.0
            val targetX = Globals.viewWidth / 2.0 - targetSize / 2.0
            val targetY = Globals.viewHeight / 2.0 - targetSize / 2.0
            x = if(abs(x - targetX) < 2.0) targetX else (x + targetX) / 2.0
            y = if(abs(y - targetY) < 2.0) targetY else (y + targetY) / 2.0
            w = if(abs(w - targetSize) < 2.0) targetSize else (w + targetSize) / 2.0
            h = if(abs(h - targetSize) < 2.0) targetSize else (h + targetSize) / 2.0
        }
    }

    fun close() {
        closing = true
    }

    fun closed(): Boolean {
        return closing &&
                abs(signY - Globals.cameraY - y) < 2.0 &&
                abs(w - 64.0) < 2.0 &&
                abs(h - 64.0) < 2.0
    }

    fun draw() {
        val smallestSize = 64.0
        val largestSize  = 96.0

        val opacity =  kotlin.math.max((w - smallestSize) / (largestSize - smallestSize), 1.0)
        Globals.autoRenderer!!.render("signboard", x, y, w, h, opacity)

        drawText(message, x + w / 6.0, y + h / 4.0, h / 36.0)
    }
}