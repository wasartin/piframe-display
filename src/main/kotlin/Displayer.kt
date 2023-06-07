import model.Album
import service.ImageManipulation

import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.io.File
import java.time.LocalTime
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
            //var directoryFilePath = "/Users/wsartin/dev/workshop/piframe-display/resrc/jpgPosters"
            //var directoryFilePath = "/home/piframe/dev/photo-db/posters/jpg/"
            var directoryFilePath = "/Users/wsartin/dev/workshop/photo-db/posters/jpg"
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
                println(e)
            }
        }

        private fun showPoster(screen: GraphicsDevice) {
            val currentImage = File(photoAlbum!!.next())
            println("Counter: ${imageIteration}, Current Image: " + currentImage.toPath())
            imageIteration++
            val rotatedImage = ImageManipulation().rotateImage(currentImage)
            screen.fullScreenWindow = Displayer(rotatedImage)
        }

        private fun setUp(): GraphicsDevice {
            val graphicEnv = GraphicsEnvironment.getLocalGraphicsEnvironment()
            val mainScreen = graphicEnv.defaultScreenDevice
            if (!mainScreen.isFullScreenSupported) {
                println("Full screen mode not supported")
                exitProcess(1)
            }
            return mainScreen
        }
    }
}