import com.soywiz.korau.sound.Sound
import com.soywiz.korev.Key
import com.soywiz.korge.input.Input
import kotlin.coroutines.CoroutineContext

class Globals {
    companion object {
        var autoRenderer: AutoRenderer? = null
        val viewWidth = 1920
        val viewHeight = 1280
        var input: Input? = null
        var titleState = TitleState()
        var blackOverlay = 0.0
        var coroutineContext: CoroutineContext? = null

        var controls = ControlScheme(Key.SPACE, Key.S, Key.A, Key.D, Key.J, Key.K, Key.P, Key.W, Key.H)
        var player: Player? = null
        var shadow: Player? = null
        var defaultShadow: Player? = null
        var cameraX: Double = 0.0
        var cameraY: Double = 0.0
        var skyShift: Double = 0.0
        var teleporting = false
        var teleportOpacity = 1.0
        var isPaused = false
        var isDead = false
        var respawnTimer = 0
        var deathX = 0.0
        var deathY = 0.0
        var deathVx = 0.0
        var deathVy = 0.0
        var deathImage: NamedImageEx? = null
        var scene = 0
        var sceneWidth = 0.0
        var platforms: MutableList<Platform> = mutableListOf()
        var lakes: MutableList<Lake> = mutableListOf()
        var bridges: MutableList<Bridge> = mutableListOf()
        var crawlers: MutableList<Crawler> = mutableListOf()
        var swordfishes: MutableList<Swordfish> = mutableListOf()
        var itemSpawns: MutableList<ItemSpawn> = mutableListOf()
        var signs: MutableList<Sign> = mutableListOf()
        var visuals: MutableList<Visual> = mutableListOf()
        var entrance: Portal? = null
        var exit: Portal? = null
        var exitDisabled = false
        var portalEnabler: PortalEnabler? = null
        var item: Item = Item.NONE
        var itemScreenX = 0.0
        var itemScreenY = 0.0
        var particles: MutableList<Particle> = mutableListOf()
        var bullets: MutableList<Bullet> = mutableListOf()
        var cannon: Cannon? = null
        var cannonBall: CannonBall? = null
        var slimeBoss: SlimeBoss? = null
        var witchBoss: WitchBoss? = null
        var doubleJumped = false
        var signboard: SignBoard? = null
        var theme = Themes.ONE
        var sinceFootstep = 0
        var sinceLastBlub = 0
        var sinceLastSplat = 1000

        fun killPlayer() {
            isDead = true
            respawnTimer = 135
            deathX = player!!.x.toDouble()
            deathY = player!!.y.toDouble()
            deathVx = 0.0
            deathVy = -30.0
            signboard?.close()
            sinceFootstep = 0
            sinceLastBlub = 0
            sinceLastSplat = 1000

            deathImage = if (item == Item.GUN) {
                NamedImageEx("gun_death", 0, -24, 64, 96)
            } else if (item == Item.CANNON) {
                NamedImageEx("cannoneer_death", 0, -24, 64, 88)
            } else if (item == Item.PIRATE) {
                NamedImageEx("pirate_death", -4, -24, 92, 96)
            } else {
                NamedImageEx("death", 0, 0, 64, 64)
            }

            playSound(Sounds.ouch)
        }

        fun playSound(sound: Sound?) {
            // Splat guard
            // (this is a bad way to do this, but I don't care)
            if (sound == Sounds.splat) {
                if (sinceLastSplat < 15) return
                sinceLastSplat = 0
            }

            sound?.play(coroutineContext!!)
        }
    }
}
