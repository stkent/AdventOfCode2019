import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ImageTests {

    @Test
    fun `decoded example`() {
        // Arrange
        val image = Image(pixels = "0222112222120000".toList())

        // Act
        val decodedImage = decodeImage(image, WidthPx(2), HeightPx(2))

        // Assert
        assertEquals(
            """
             █
            █ 
            """.trimIndent(),
            decodedImage
        )
    }

}
