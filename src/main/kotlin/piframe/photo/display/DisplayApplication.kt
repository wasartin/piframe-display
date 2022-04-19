package piframe.photo.display

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import piframe.photo.display.service.DisplayPhoto

@SpringBootApplication
class DisplayApplication

fun main(args: Array<String>) {
	runApplication<DisplayApplication>(*args)
}
