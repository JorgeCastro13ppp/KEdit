package com.kedit.repository

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

}