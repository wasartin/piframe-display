package service

import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageManipulation {

    fun rotateImage(inputImage: File): BufferedImage {
        val image = ImageIO.read(inputImage)
        val width = image.width
        val height = image.height
        val newImage = BufferedImage(height, width, image.type)
        val g = newImage.createGraphics()
        val at = AffineTransform()
        at.translate(((height - width) / 2).toDouble(), ((height - width) / 2).toDouble())
        at.rotate(Math.toRadians(90.0), (height / 2).toDouble(), (width / 2).toDouble())
        g.transform = at
        g.drawImage(image, 0, 0, null)
        g.dispose()
        return newImage
    }
}