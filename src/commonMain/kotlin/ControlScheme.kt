import com.soywiz.korev.Key

data class ControlScheme(
    val up: Key,
    val down: Key,
    val left: Key,
    val right: Key,
    val a: Key,
    val b: Key,
    val pause: Key,
    val altUp: Key,
    val interact: Key
) {
    fun testKeysDown() {
        testKeyDown(up)
        testKeyDown(down)
        testKeyDown(left)
        testKeyDown(right)
        testKeyDown(a)
        testKeyDown(b)
        testKeyDown(pause)
        testKeyDown(altUp)
        testKeyDown(interact)
        testKeyDown(Key.N1)
    }

    fun testKeyDown(key: Key) {
        if (Globals.input!!.keys.justPressed(key)) {
            keyDown(key)
        }
    }
}
