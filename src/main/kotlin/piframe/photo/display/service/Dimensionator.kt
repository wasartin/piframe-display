package piframe.photo.display.service

import java.awt.Dimension
import java.awt.Toolkit

class Dimensionator {

    fun getScreenDimensions(): ScreenSize {
        var screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
        var width = screenSize.getWidth()
        var height = screenSize.getHeight()

        return ScreenSize(width, height)
    }

}
data class ScreenSize(val width: Double, val height: Double)