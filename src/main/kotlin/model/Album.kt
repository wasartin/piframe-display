package model

import java.io.File
import java.util.*

class Album(directory: String?) {
    private val shuffledDirectory = ArrayList<File?>()
    private var index = 0

    // Debugging memory issue
    private var fullCycle = 1

    init {
        val currentDirectory = File(directory)
        val files = currentDirectory.listFiles { pathname: File ->
            val extension = pathname.name.lowercase(Locale.getDefault())
            extension.endsWith(".jpg") || extension.endsWith(".jpeg") || extension.endsWith(".png")
        } ?: throw Exception("Given a bad directory. Couldn't find any files at: $currentDirectory")
        shuffledDirectory.addAll(listOf(*files))
        shuffledDirectory.shuffle()
    }

    operator fun next(): File? {
        if (index >= shuffledDirectory.size) {
            shuffledDirectory.shuffle()
            index = 0
            println("Full cycles: ${fullCycle}, reshuffling images")
            fullCycle++
        }
        return shuffledDirectory[index++]
    }

    fun previous(): File? {
        index--
        if (index < 0) {
            index = 0
        }
        return shuffledDirectory[index]
    }
}