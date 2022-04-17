package piframe.photo.display

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage
import java.io.*
import javax.annotation.PostConstruct
import javax.imageio.ImageIO


@Component
class DisplayApplicationLogic : Application() {

    @PostConstruct
    fun run(){
        launch()
    }

//    override fun start(primaryStage: Stage?) {
//        val stream: InputStream = FileInputStream("/Users/wsartin/temp/photos/derzu_uzala.jpg" )
//
//        val image = Image(stream)
//        val imageView = ImageView()
//        imageView.image = image
//        //imageView.x = 10.0
//        //imageView.y = 10.0
//        //imageView.fitWidth = 575.0
//        imageView.isPreserveRatio = true
//        imageView.rotate += 90
//        imageView.fitHeight
//        val root = Group(imageView)
//
//        //val scene = Scene(root, 600.0, 900.0, Color.BLACK)
//        val scene = Scene(root, 1800.0, 1200.0, Color.BLACK)
//        primaryStage?.scene = scene
//        primaryStage?.title = "Derzu-Uzala"
//        primaryStage?.show()
//    }

    fun rotateClockwise90(src: BufferedImage): BufferedImage? {
        val width = src.width
        val height = src.height
        val dest = BufferedImage(height, width, src.type)
        val graphics2D = dest.createGraphics()
        graphics2D.translate((height - width) / 2, (height - width) / 2)
        graphics2D.rotate(Math.PI / 2, (height / 2).toDouble(), (width / 2).toDouble())
        graphics2D.drawRenderedImage(src, null)
        return dest
    }

    override fun start(primaryStage: Stage?) {
        val buf: BufferedImage = ImageIO.read(File("/Users/wsartin/temp/photos/derzu_uzala.jpg"))
        val rotate = rotateClockwise90(buf)
        val os = ByteArrayOutputStream()
        ImageIO.write(rotate, "jpeg", os) // Passing: ​(RenderedImage im, String formatName, OutputStream output)
        val es: InputStream = ByteArrayInputStream(os.toByteArray())

        //val stream: InputStream = FileInputStream("/Users/wsartin/temp/photos/derzu_uzala.jpg" )
        val image = Image(es)
        val imageView = ImageView()
        imageView.image = image
        imageView.x = 10.0
        imageView.y = 10.0
        imageView.fitWidth = 1200.0
        imageView.fitHeight = 900.0
        imageView.isPreserveRatio = true
        //imageView.rotate += 90
        val root = Group(imageView)

        val scene = Scene(root, 1200.0, 900.0, Color.BLACK)
        primaryStage?.scene = scene
        primaryStage?.title = "Derzu-Uzala"
        primaryStage?.show()
    }

//    fun getWidthAndHeight(imagePath: InputStream): SimpleImage {
//        val image: BufferedImage = ImageIO.read(imagePath)
//        val height = image.height.toDouble()
//        val width = image.width.toDouble()
//        return SimpleImage(width = width, height = height)
//    }
}

data class SimpleImage(val width: Double, val height: Double)