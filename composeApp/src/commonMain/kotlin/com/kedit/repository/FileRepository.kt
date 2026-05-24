package com.kedit.repository

import com.kedit.model.FileItem

expect class FileRepository() {

    fun saveFile(
        path: String,
        content: String
    )

    fun readFile(
        path: String
    ): String

    fun pickFile(): String?
    fun pickSaveFile(): String?
    fun getFileName(
        path: String
    ): String

    fun pickDirectory(): String?

    fun listFiles(
        directoryPath: String
    ): List<FileItem>
    fun getParentDirectory(
        path: String
    ): String?


}