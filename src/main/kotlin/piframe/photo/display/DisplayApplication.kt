package piframe.photo.display

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import piframe.photo.display.service.DisplayPhoto


@SpringBootApplication
class DisplayApplication

fun main(args: Array<String>) {
	runApplication<DisplayApplication>(*args)

	val display = DisplayPhoto()
	display.displayImage("/Users/wsartin/temp/photos/derzu_uzala.jpg")
}

@EventListener(ApplicationReadyEvent::class)
fun displayAnImage() {
	println("Hopefully display a photo")
	val display = DisplayPhoto()
	display.displayImage("")
}