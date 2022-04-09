package piframe.photo.display.service

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JOptionPane


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



//    fun didntWork() {
//        val myPicture = ImageIO.read(File(imagePath))
//        val g = myPicture.graphics as Graphics2D
//        g.stroke = BasicStroke(3F)
//        g.color = Color.BLUE
//        g.drawRect(10, 10, myPicture.width - 20, myPicture.height - 20)
//
//        val picLabel = JLabel(ImageIcon(myPicture))
//        val jPanel = JPanel()
//        jPanel.add(picLabel)
//
//        println("Might look good")
//        System.setProperty("java.awt.headless", "false")
//        val f = JFrame("Poster")
//        f.size = Dimension(myPicture.width, myPicture.height)
//        f.add(jPanel)
//        f.isVisible = true
//    }


}
