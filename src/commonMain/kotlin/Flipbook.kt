import com.soywiz.korge.render.Texture
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

/**
 * Flipbook is used for simple frame-based Animation
 */
open class Flipbook(
    val currentAnimation: List<String>,
    var interval: Int
) {
    var currentFrame: Int = 0
    var ticker: Int = 0

    constructor(pattern: String, start: Int, end: Int, interval: Int) : this(patternRangeExpand(pattern, start, end), interval)

    fun restart() {
        currentFrame = 0
        ticker = 0
    }

    fun update() {
        if (++ticker == interval) {
            ticker = 0
            advance()
        }
    }

    fun advance() {
        if (++currentFrame >= currentAnimation.size) {
            currentFrame = 0
        }
    }

    fun atEnd(): Boolean {
        return currentFrame + 1 == currentAnimation.size && ticker + 1 == interval
    }

    fun newInstance(): Flipbook {
        return Flipbook(currentAnimation, interval)
    }

    fun now(): String {
        return currentAnimation.get(currentFrame)
    }
}

fun patternRangeExpand(pattern: String, start: Int, end: Int): List<String> {
    val list: MutableList<String> = mutableListOf()
    val range = if (end > start) start..end else start downTo end

    for (i in range) {
        list.add(pattern.replace("%", i.toString()))
    }

    return list
}
