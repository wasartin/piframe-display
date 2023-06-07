package model

import java.io.File
import java.io.FilenameFilter
import java.util.*

class Album(directory: String?) {
    //private val shuffledDirectory = ArrayList<File?>()
    private val shuffledDirectory = ArrayList<String>()
    private var index = 0

    // Debugging memory issue
    private var fullCycle = 1

    init {
        val currentDirectory = File(directory)
        val files = currentDirectory.listFiles()
        val filesNames:MutableList<String> = mutableListOf()
        files?.map{
            if(validImage(it)) {
                filesNames.add(it!!.absolutePath)
            }
        }
        shuffledDirectory.addAll(filesNames)
        shuffledDirectory.shuffle()
    }

    private fun validImage(filename: File): Boolean {
        val extension = filename.name.lowercase(Locale.getDefault())
        return extension.endsWith(".jpg") || extension.endsWith(".jpeg") || extension.endsWith(".png")
    }

    operator fun next(): String {
        if (index >= shuffledDirectory.size) {
            shuffledDirectory.shuffle()
            index = 0
            println("Full cycles: ${fullCycle}, reshuffling images")
            fullCycle++
        }
        return shuffledDirectory[index++]
    }

    fun previous(): String {
        index--
        if (index < 0) {
            index = 0
        }
        return shuffledDirectory[index]
    }
}