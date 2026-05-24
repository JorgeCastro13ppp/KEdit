package com.kedit.repository

import com.kedit.model.FileItem

actual class FileRepository actual constructor() {

    actual fun saveFile(
        path: String,
        content: String
    ) {

        println("Save not implemented on iOS")
    }

    actual fun readFile(
        path: String
    ): String {

        return ""
    }

    actual fun pickFile(): String? {

        return null
    }

    actual fun pickSaveFile(): String? {

        return null
    }

    actual fun getFileName(
        path: String
    ): String {

        return path.substringAfterLast("/")
    }

    actual fun pickDirectory(): String? {

        return null
    }

    actual fun listFiles(
        directoryPath: String
    ): List<FileItem> {

        return emptyList()
    }

    actual fun getParentDirectory(
        path: String
    ): String? {

        return null
    }
}