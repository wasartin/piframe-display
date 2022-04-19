package piframe.photo.display.service

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.effect.ImageInput
import org.springframework.stereotype.Component
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired


import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JOptionPane


@Component
class DisplayPhoto {
    private var directory = ""

    fun loadDirectory(directoryLocation: String): List<String> {
        var result: MutableList<String> = mutableListOf()

        val directory = File(directoryLocation)
        for(file in directory.listFiles()) {
            result.add(file.absolutePath)
        }
        return result
    }

    fun displayImage(imageFilePath: String) {
        val imagePath = imageFilePath.ifEmpty { "/Users/wsartin/temp/photos/derzu_uzala.jpg" }

        try {
            System.setProperty("java.awt.headless", "false")
            val img: BufferedImage = ImageIO.read(File(imagePath))
            val icon = ImageIcon(img)
            val label = JLabel(icon)
            JOptionPane.showMessageDialog(null, label)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
