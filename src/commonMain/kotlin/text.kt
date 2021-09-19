
fun drawText(rawContent: String, xOffset: Double, yOffset: Double, scale: Double = 14.0, opacity: Double = 1.0) {
    val content = rawContent.lowercase()
    val newline = 0x0A.toChar()
    val autoRenderer = Globals.autoRenderer!!
    var x = 0.0
    var y = 0.0

    for (c in content) {
        if (c == newline) {
            x = 0.0
            y += scale * 1.5
            continue
        }

        if (c != ' ')
            autoRenderer.renderCharacter(c, x + xOffset, y + yOffset, scale * 5.0 / 7.0, scale, opacity)

        x += scale * 6.0 / 7.0
    }
}
