package com.kedit.repository

import java.io.File
import javax.swing.JFileChooser

actual class FileRepository actual constructor() {

    actual fun saveFile(
        path: String,
        content: String
    ) {

        File(path).writeText(content)
    }

    actual fun readFile(
        path: String
    ): String {

        return File(path).readText()
    }

    actual fun pickFile(): String? {

        val chooser = JFileChooser()

        val result =
            chooser.showOpenDialog(null)

        return if (
            result ==
            JFileChooser.APPROVE_OPTION
        ) {

            chooser.selectedFile.absolutePath

        } else {

            null
        }
    }

    actual fun pickSaveFile(): String? {

        val chooser = JFileChooser()

        val result =
            chooser.showSaveDialog(null)

        return if (
            result == JFileChooser.APPROVE_OPTION
        ) {

            chooser.selectedFile.absolutePath

        } else {

            null
        }
    }

    actual fun getFileName(
        path: String
    ): String {

        return File(path).name
    }
}