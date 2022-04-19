package piframe.photo.display.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DimensionatorTest {

    @Test
    fun `should get the correct screen size`(){
        val dims = Dimensionator()
        val actualScreenSize = dims.getScreenDimensions()

        val expectedScreenSizeOfMac = ScreenSize(1680.0, 1050.0)
        Assertions.assertEquals(expectedScreenSizeOfMac, actualScreenSize)
    }
}