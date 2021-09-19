import com.soywiz.korge.view.Image
import com.soywiz.korge.view.Stage
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korim.bitmap.slice
import com.soywiz.korim.bitmap.sliceWithBounds
import com.soywiz.korio.resources.Resourceable
import kotlin.random.Random

/*
 * Automatically keeps track of the best way to render images on screen
 * using KorGE Image views
 *
 * Weirdly enough, it seems like the best way
 * to do this is to recreate the Image views each frame
 *
 * The benefit of doing it this way, is that Images that
 * are no longer needed aren't taking up vertices (which they do even if they are !visible for some reason)
 *
 * We also then don't have to waste CPU power fighting against the GC
 *
 * For JVM Target:
 * Can handle: ~500 images per frame for reliable 60 fps
 * Can handle: ~700 images per frame for mostly reliable 60 fps
 *
 * For JS Target:
 * Can handle: ~700 images per frame for reliable 60 fps
 * Can handle: ~1000 images per frame for mostly reliable 60 fps
 */
class AutoRenderer(val stage: Stage){
    private var map: HashMap<String, ImageSource> = HashMap()
    private var characters: Array<ImageSource?> = arrayOfNulls(256)
    private var imageViews: MutableList<Image> = mutableListOf()
    val specialCharacters = listOf('.', ',', '!', '?','-', '+', '/', '~', ':')
    val moreSpecialCharacters = listOf('\'', '%')

    fun register(name: String, bitmap: Resourceable<out BmpSlice>) {
        map.put(name, ImageSource(bitmap))
    }

    fun registerFontCharacters(font: Bitmap) {
        for (c in 'a'..'z') {
            characters[c.code % 256] = ImageSource(getFontSlice(font, getFontCharacterIndexFor(c)))
        }
        for (c in '0'..'9') {
            characters[c.code % 256] = ImageSource(getFontSlice(font, getFontCharacterIndexFor(c)))
        }
        for (c in specialCharacters) {
            characters[c.code % 256] = ImageSource(getFontSlice(font, getFontCharacterIndexFor(c)))
        }
        for (c in moreSpecialCharacters) {
            characters[c.code % 256] = ImageSource(getFontSlice(font, getFontCharacterIndexFor(c)))
        }
    }

    fun lookup(name: String): ImageSource? {
        return map.get(name)
    }

    fun clear() {
        for(imageView in imageViews){
            stage.removeChild(imageView)
        }
        imageViews.clear()
    }

    fun renderAt(name: String, x: Double, y: Double){
        val imageSource = map.get(name)!!
        val image = acquireImageFrom(imageSource)
        imageViews.add(image)

        if(x + image.width <= 0.0 || x >= Globals.viewWidth.toDouble()){
            // Don't draw offscreen images
            stage.removeChild(image)
            imageViews.removeAt(imageViews.size - 1)
            return
        }

        image.x = x
        image.y = y
        image.visible = true
        image.scaleX = 1.0
        image.scaleY = 1.0
        image.alpha = 1.0
    }

    fun render(name: String, x: Double, y: Double, w: Double, h: Double, alpha: Double = 1.0){
        val source: ImageSource? = lookup(name)

        if (source == null) {
            throw IllegalStateException("Unknown texture named '" + name + "'")
        }

        renderImage(source, x, y, w, h, alpha)
    }

    fun renderImage(source: ImageSource, x: Double, y: Double, w: Double, h: Double, alpha: Double = 1.0){
        val image = acquireImageFrom(source)
        val scaleW = w / image.width
        val scaleH = h / image.height

        var right = x
        var left = x + w

        if (right < left) {
            val tmp = right
            right = left
            left = tmp
        }

        if(right <= 0.0 || left >= Globals.viewWidth.toDouble()){
            // Don't draw offscreen images
            stage.removeChild(image)
            imageViews.removeAt(imageViews.size - 1)
            return
        }

        image.x = x
        image.y = y
        image.scaleX = scaleW
        image.scaleY = scaleH
        image.visible = true
        image.alpha = alpha
    }

    fun renderCharacter(c: Char, x: Double, y: Double, w: Double, h: Double, alpha: Double = 1.0){
        val imageSource = characters[c.code % 256]!!
        val image = acquireImageFrom(imageSource)
        image.x = x
        image.y = y
        image.scaleX = w / image.width
        image.scaleY = h / image.height
        image.visible = true
        image.alpha = alpha
    }

    fun acquireImageFrom(imageSource: ImageSource): Image {
        val image = imageSource.makeImage()
        imageViews.add(image)
        stage.addChild(image)
        return image
    }

    fun random(random: Random): String {
        return map.keys.elementAt(random.nextInt(map.size))
    }
}

class ImageSource(var resource: Resourceable<out BmpSlice>) {
    fun makeImage(): Image {
        return Image(resource, smoothing = false)
    }
}

fun getFontCharacterIndexFor(character: Char): Int {
    if (character in 'A'..'Z') return character - 'A'
    if (character in 'a'..'z') return character - 'a'
    if (character in '0'..'9') return character - '0' + 35

    var index = Globals.autoRenderer!!.specialCharacters.indexOf(character)
    if (index != -1) return index + 26

    index = Globals.autoRenderer!!.moreSpecialCharacters.indexOf(character)
    return if (index != -1) index + 45 else 29
}

fun getFontSlice(bitmap: Bitmap, index: Int): Resourceable<out BmpSlice> {
    val left = 6 * index
    val right = 6 * index + 5
    val top = 0
    val bottom = 7
    return bitmap.sliceWithBounds(left, top, right, bottom)
}
