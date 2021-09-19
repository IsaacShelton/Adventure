import com.soywiz.korev.Key
import kotlin.math.*

class TitleState(var frame: Int) {
    var activated: Boolean = false
    var vy = 0.0
    var y = 0.0
    var groundY = 0.0
    var titleY = 0.0
    var opacity = -3.0

    constructor() : this(0)

    fun step() {
        frame++

        if (activated) {
            y += vy
            vy += 1.2
            groundY += 12.0
            titleY -= 8.0
            opacity += 1.0 / 30.0
        }

        if (opacity > 3.0) {
            gotoScene1()
        }
    }

    fun onKey(key: Key) {
        // Activate if 'space' pressed
        if (key == Key.SPACE && !activated) {
            vy = -20.0
            activated = true
        }
    }

    fun draw() {
        val titleWiggle = sin((frame % 120).toDouble() / 120.0 * 2.0 * PI) * 16.0
        Globals.autoRenderer!!.render("title", 0.0, titleWiggle + titleY, Globals.viewWidth.toDouble(), Globals.viewHeight.toDouble())

        Globals.autoRenderer!!.render("idle", Globals.viewWidth.toDouble() / 2.0 - 64.0, y + Globals.viewHeight.toDouble() / 2.0 + 64.0, 128.0, 128.0)

        fun drawCol(head: String, middle: String, xOffset: Double) {
            Globals.autoRenderer!!.render(head, xOffset + Globals.viewWidth.toDouble() / 2.0 - 64.0, groundY + Globals.viewHeight.toDouble() / 2.0 + 64.0 + 128.0 * 1.0, 128.0, 128.0)
            Globals.autoRenderer!!.render(middle, xOffset + Globals.viewWidth.toDouble() / 2.0 - 64.0, groundY + Globals.viewHeight.toDouble() / 2.0 + 64.0 + 128.0 * 2.0, 128.0, 128.0)
            Globals.autoRenderer!!.render(middle, xOffset + Globals.viewWidth.toDouble() / 2.0 - 64.0, groundY + Globals.viewHeight.toDouble() / 2.0 + 64.0 + 128.0 * 3.0, 128.0, 128.0)
            Globals.autoRenderer!!.render(middle, xOffset + Globals.viewWidth.toDouble() / 2.0 - 64.0, groundY + Globals.viewHeight.toDouble() / 2.0 + 64.0 + 128.0 * 4.0, 128.0, 128.0)
        }

        drawCol("platform_north_west", "platform_west",128.0 * -2.0)
        drawCol("platform_north", "platform_center", 128.0 * -1.0)
        drawCol("platform_north", "platform_center", 128.0 * 0.0)
        drawCol("platform_north", "platform_center", 128.0 * 1.0)
        drawCol("platform_north_east", "platform_east", 128.0 * 2.0)

        if (frame % 60 <= 30 && !activated) {
            Globals.autoRenderer!!.render("pressspace", Globals.viewWidth.toDouble() / 3.0, Globals.viewHeight.toDouble() / 2.0 + Globals.viewHeight.toDouble() / 8.0 - 192.0, Globals.viewWidth.toDouble() / 3.0, Globals.viewHeight.toDouble() / 8.0)
        }

        if (opacity != 0.0) {
            Globals.autoRenderer!!.render("bullet", 0.0, 0.0, Globals.viewWidth.toDouble(), Globals.viewHeight.toDouble(), min(max(0.0, opacity), 1.0))
        }

        // If we wanted some text before game start,
        // this would do the trick.
        // But for now, I think it seems better without
        /*
        if (opacity > 2.0) {
            val timer = min(1.0, (opacity - 2.0) / 3.0)
            var text = "Unused"
            text = text.substring(0, (text.length.toDouble() * timer).toInt())
            var alpha = 1.0 - min(max(0.0, opacity - 8.0), 1.0)

            drawText(text, Globals.viewWidth / 2.0 - 0.5 * 64.0 * text.length, Globals.viewHeight / 2.0 - 32.0, 64.0 * 7.0 / 6.0, alpha)
        }
        */
    }
}