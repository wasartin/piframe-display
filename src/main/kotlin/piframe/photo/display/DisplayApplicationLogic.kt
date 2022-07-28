package piframe.photo.display

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
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
 
    override fun start(primaryStage: Stage?) {
        val file = ResourceUtils.getFile("classpath:static/posters/derzu_uzala.jpg")
        val buf: BufferedImage = ImageIO.read(file)

        val rotate = rotateClockwise90(buf)
        val os = ByteArrayOutputStream()
        ImageIO.write(rotate, "jpeg", os)
        val es: InputStream = ByteArrayInputStream(os.toByteArray())

        showImage(primaryStage, Image(es))
    }

    private fun showImage(primaryStage: Stage?, image: Image) {
        val imageView = ImageView()
        imageView.image = image
        imageView.x = 10.0
        imageView.y = 10.0
        imageView.fitWidth = 1200.0
        imageView.fitHeight = 900.0
        imageView.isPreserveRatio = true
        val root = Group(imageView)

        val scene = Scene(root, 1200.0, 900.0, Color.BLACK)
        primaryStage?.scene = scene
        primaryStage?.title = "Derzu-Uzala"
        primaryStage?.show()
    }

    private fun rotateClockwise90(src: BufferedImage): BufferedImage? {
        val width = src.width
        val height = src.height
        val result = BufferedImage(height, width, src.type)
        val graphics2D = result.createGraphics()
        graphics2D.translate((height - width) / 2, (height - width) / 2)
        graphics2D.rotate(Math.PI / 2, (height / 2).toDouble(), (width / 2).toDouble())
        graphics2D.drawRenderedImage(src, null)
        return result
    }
}