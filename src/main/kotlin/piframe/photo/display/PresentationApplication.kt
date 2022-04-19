package piframe.photo.display

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.effect.ImageInput
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component

@Component
class PresentationApplication : Application() {

    private var applicationContext: ConfigurableApplicationContext? = null

    override fun start(stage: Stage?) {
        val imagePath = "/Users/wsartin/temp/photos/derzu_uzala.jpg"
        val imginput = ImageInput()
        val rect = Rectangle()
        imginput.source = javafx.scene.image.Image(imagePath)
        imginput.x = 20.0
        imginput.y = 100.0
        val root = Group()
        rect.setEffect(imginput)
        root.children.add(rect)
        val scene = Scene(root, 530.0, 500.0, Color.BLACK)
        stage?.scene = scene
        stage?.title = "ImageInput Example"
        stage?.show()

    }
}