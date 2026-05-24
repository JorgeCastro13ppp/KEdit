package com.kedit.repository

import java.io.File
import javax.swing.JFileChooser
import com.kedit.model.FileItem

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

    actual fun pickDirectory(): String? {

        val chooser =
            JFileChooser()

        chooser.fileSelectionMode =
            JFileChooser.DIRECTORIES_ONLY

        val result =
            chooser.showOpenDialog(null)

        return if (
            result == JFileChooser.APPROVE_OPTION
        ) {

            chooser.selectedFile.absolutePath

        } else {

            null
        }
    }

    actual fun listFiles(
        directoryPath: String
    ): List<FileItem> {

        val directory =
            File(directoryPath)

        if (!directory.exists() || !directory.isDirectory)
            return emptyList()

        return directory
            .listFiles()
            ?.filter { file ->
                file.isDirectory ||
                        file.extension.lowercase() in listOf(
                    "txt",
                    "kt",
                    "kts",
                    "md",
                    "java",
                    "json",
                    "xml",
                    "html",
                    "css",
                    "js",
                    "ts"
                )
            }
            ?.sortedWith(
                compareBy<File> {
                    !it.isDirectory
                }.thenBy {
                    it.name.lowercase()
                }
            )
            ?.map { file ->
                FileItem(
                    name = file.name,
                    path = file.absolutePath,
                    isDirectory = file.isDirectory
                )
            }
            ?: emptyList()
    }

    actual fun getParentDirectory(
        path: String
    ): String? {

        return File(path).parent
    }
}