package com.kedit.repository

actual class FileRepository actual constructor() {

    actual fun saveFile(
        path: String,
        content: String
    ) {

        println("Save not implemented on Web")
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
}