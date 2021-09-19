
fun updateCamera() {
    Globals.cameraX =
        when {
            !Globals.teleporting -> Globals.player!!.x.toDouble() + 32.0 - Globals.viewWidth.toDouble() / 2.0
            else -> Globals.cameraX
        }

    Globals.cameraX =
        when {
            Globals.cameraX < 0.0 -> 0.0
            Globals.cameraX + Globals.viewWidth > Globals.sceneWidth -> Globals.sceneWidth - Globals.viewWidth.toDouble()
            else -> Globals.cameraX
        }
}
