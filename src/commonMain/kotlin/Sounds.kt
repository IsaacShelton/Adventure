import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korio.file.VfsFile

class Sounds {
    companion object {
        var hurt: Sound? = null
        var teleport: Sound? = null
        var splash: Sound? = null
        var heavysplash: Sound? = null
        var splush: Sound? = null
        var item: Sound? = null
        var cannon: Sound? = null
        var bullet: Sound? = null
        var footstep_grass: Sound? = null
        var footstep_sand: Sound? = null
        var footstep_wood: Sound? = null
        var signopen: Sound? = null
        var ribbit: Sound? = null
        var wingjump: Sound? = null
        var ouch: Sound? = null
        var blub: Sound? = null
        var enabler: Sound? = null
        var star: Sound? = null
        var remember: Sound? = null
        var hex: Sound? = null
        var witchouch: Sound? = null
        var doublejump: Sound? = null
        var splat: Sound? = null

        suspend fun load(resourcesVfs: VfsFile) {
            hurt = resourcesVfs["hurt.wav"].readSound()
            teleport = resourcesVfs["teleport.wav"].readSound()
            splash = resourcesVfs["splash.wav"].readSound()
            heavysplash = resourcesVfs["heavysplash.wav"].readSound()
            splush = resourcesVfs["splush.wav"].readSound()
            item = resourcesVfs["item.wav"].readSound()
            cannon = resourcesVfs["cannon.wav"].readSound()
            bullet = resourcesVfs["bullet.wav"].readSound()
            footstep_grass = resourcesVfs["footstep_grass.wav"].readSound()
            footstep_sand = resourcesVfs["footstep_sand.wav"].readSound()
            footstep_wood = resourcesVfs["footstep_wood.wav"].readSound()
            signopen = resourcesVfs["signopen.wav"].readSound()
            ribbit = resourcesVfs["ribbit.wav"].readSound()
            wingjump = resourcesVfs["wingjump.wav"].readSound()
            ouch = resourcesVfs["ouch.wav"].readSound()
            blub = resourcesVfs["blub.wav"].readSound()
            enabler = resourcesVfs["enabler.wav"].readSound()
            star = resourcesVfs["star.wav"].readSound()
            remember = resourcesVfs["remember.wav"].readSound()
            hex = resourcesVfs["hex.wav"].readSound()
            witchouch = resourcesVfs["witchouch.wav"].readSound()
            doublejump = resourcesVfs["doublejump.wav"].readSound()
            splat = resourcesVfs["splat.wav"].readSound()
        }
    }
}
