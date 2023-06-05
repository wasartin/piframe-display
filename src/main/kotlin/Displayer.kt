import model.Album

import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import java.time.LocalTime
import javax.imageio.ImageIO
import kotlin.system.exitProcess

class Displayer(private val pic: BufferedImage) : Window(Frame()) {

    init {
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.button == MouseEvent.BUTTON1) { //Left
                    exitProcess(0)
                } else if (e.button == MouseEvent.BUTTON3) { // Right
                    displayCurrentPoster = false
                }
            }
        })
    }

    override fun paint(g: Graphics) {
        g.color = Color.BLACK
        g.drawRect(0, 0, width - 1, height - 1)
        g.drawImage(pic, 0, 0, width, height, this)
    }

    companion object {

        // Debugging current memory issue
        private var imageIteration = 0

        private var photoAlbum: Album? = null
        private var displayCurrentPoster = true

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            println("Startup")
            //var directoryFilePath = "/Users/wsartin/dev/workshop/photo-album/resrc/jpgPosters"
            var directoryFilePath = "/home/piframe/dev/photo-db/posters/jpg/"
            var intervalInMinutes = 15
            if (args.isNotEmpty()) {
                directoryFilePath = args[0]
                intervalInMinutes = args[1].toInt()
                println("New intervalInMinutes: $intervalInMinutes")
            }
            photoAlbum = Album(directoryFilePath)
            val screen = setUp()
            run(intervalInMinutes, screen)
        }

        private fun run(intervalInMinutes: Int, screen: GraphicsDevice) {
            try {
                while (true) {
                    displayCurrentPoster = true
                    val startTime = LocalTime.now()
                    val endTime = startTime.plusMinutes(intervalInMinutes.toLong())
                    showPoster(screen)
                    while (displayCurrentPoster) {
                        Thread.sleep(5)
                        if (LocalTime.now().isAfter(endTime)) {
                            displayCurrentPoster = false
                        }
                    }
                }
            } catch (e: Exception) {
                println("What the hell is going on here?")
            }
        }

        private fun showPoster(screen: GraphicsDevice) {
            screen.fullScreenWindow = null
            val currentPhoto: File = photoAlbum!!.next()!!
            println("Counter: ${imageIteration}, Current Image: " + currentPhoto.toPath())
            imageIteration++
            val image = ImageIO.read(currentPhoto)
            val rotatedImage = rotate(image)
            screen.fullScreenWindow = Displayer(rotatedImage)
        }

        private fun rotate(image: BufferedImage): BufferedImage {
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

        private fun setUp(): GraphicsDevice {
            val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
            val screen = ge.defaultScreenDevice
            if (!screen.isFullScreenSupported) {
                println("Full screen mode not supported")
                System.exit(1)
            }
            return screen
        }
    }
}