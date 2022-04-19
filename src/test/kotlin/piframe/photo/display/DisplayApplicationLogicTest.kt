package piframe.photo.display

import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage
import java.io.FileInputStream
import java.io.InputStream
import javax.imageio.ImageIO

class DisplayApplicationLogicTest {

    @Test
    fun `get width and height of a photo`(){
        val stream: InputStream = FileInputStream("/Users/wsartin/temp/photos/derzu_uzala.jpg" )
        val dims = getWidthAndHeight(stream)
        println("Width: ${dims.width}, Height: ${dims.height}")
    }

    fun getWidthAndHeight(imagePath: InputStream): SimpleImage {
        val image: BufferedImage = ImageIO.read(imagePath)
        val height = image.height.toDouble()
        val width = image.width.toDouble()
        return SimpleImage(width = width, height = height)
    }

}