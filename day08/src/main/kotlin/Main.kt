import extensions.collapseToString
import extensions.elementCounts

fun main() {
    val rawImage = Image(pixels = resourceFile("input.txt").readLines().first().toList())
    // It would be nice if toXXX methods were generated for inline types!
    val widthPx = WidthPx(25)
    val heightPx = HeightPx(6)

    println("Part 1 solution: ${computeImageChecksum(rawImage, widthPx, heightPx)}")
    println("Part 2 solution: ")
    decodeImage(rawImage, widthPx, heightPx).also(::println)
}

fun computeImageChecksum(image: Image, widthPx: WidthPx, heightPx: HeightPx): Int {
    return image
        .extractLayers(widthPx, heightPx)
        .map(ImageLayer::countPixels)
        .minBy { pixelCounts -> pixelCounts.getValue('0') }!!
        .run { getValue('1') * getValue('2') }
}

fun decodeImage(image: Image, widthPx: WidthPx, heightPx: HeightPx): String {
    return image
        .extractLayers(widthPx, heightPx)
        .combineLayers()
        .formatForScreen(widthPx)
}

inline class Image(val pixels: List<Char>)
inline class WidthPx(val raw: Int)
inline class HeightPx(val raw: Int)
private inline class ImageLayer(val pixels: List<Char>)

private fun Image.extractLayers(widthPx: WidthPx, heightPx: HeightPx): List<ImageLayer> {
    return pixels
        .chunked(widthPx.raw * heightPx.raw)
        .map(::ImageLayer)
}

private fun ImageLayer.countPixels(): Map<Char, Int> {
    return pixels.elementCounts()
}

private fun List<ImageLayer>.combineLayers(): ImageLayer {
    return reduce { topLayers, nextLayer ->
        topLayers.pixels.zip(nextLayer.pixels)
            .map { (topPixel, nextPixel) ->
                // If top pixel is transparent, show the next pixel.
                // Otherwise, show the top pixel (since it covers every subsequent pixel).
                if (topPixel == '2') nextPixel else topPixel
            }
            .run(::ImageLayer)
    }
}

private fun ImageLayer.formatForScreen(widthPx: WidthPx): String {
    return pixels
        .map { char -> if (char == '1') '█' else ' ' }
        .chunked(widthPx.raw)
        .map { rowChars -> rowChars.collapseToString() } // IntelliJ suggests moving this transform
        .joinToString("\n")                              // into a joinToString overload, but I find
                                                         // this combination more readable.
}
