package piframe.photo.display.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DisplayPhotoTest(
    @Value("\${image.directory}") private val tempDirectory: String
) {

    private val display = DisplayPhoto()

    @Test
    fun `given a directory then loadDirectory should list the files`(){
        val files = display.loadDirectory(tempDirectory)
        Assertions.assertEquals("/Users/wsartin/temp/photos/derzu_uzala.jpg", files[0])
    }
//
//    @Test
//    fun `just running, not sure how to test this`(){
//        val files = display.loadDirectory(tempDirectory)
//        display.displayImage(files[0])
//    }
}