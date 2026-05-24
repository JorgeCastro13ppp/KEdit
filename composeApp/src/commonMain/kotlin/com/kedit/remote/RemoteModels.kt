package com.kedit.remote

data class RemoteUser(
    val userId: Int,
    val email: String,
    val message: String
)

data class RemoteDocument(
    val id: Int,
    val userId: Int,
    val name: String,
    val content: String = ""
)