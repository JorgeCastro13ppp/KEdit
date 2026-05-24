package com.kedit.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.request.put

class RemoteRepository {

    private val baseUrl =
        "http://localhost:8082"

    private val client =
        HttpClient()

    suspend fun register(
        email: String,
        password: String
    ): RemoteUser? {

        val response =
            client.post("$baseUrl/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    {
                        "email": "$email",
                        "password": "$password"
                    }
                    """.trimIndent()
                )
            }

        val body =
            response.bodyAsText()

        return parseUser(body)
    }

    suspend fun login(
        email: String,
        password: String
    ): RemoteUser? {

        val response =
            client.post("$baseUrl/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    {
                        "email": "$email",
                        "password": "$password"
                    }
                    """.trimIndent()
                )
            }

        val body =
            response.bodyAsText()

        return parseUser(body)
    }

    suspend fun saveDocument(
        userId: Int,
        name: String,
        content: String
    ): Boolean {

        val safeContent =
            content
                .replace("\"", "\\\"")
                .replace("\n", "\\n")

        val response =
            client.post("$baseUrl/documents") {
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    {
                        "userId": $userId,
                        "name": "$name",
                        "content": "$safeContent"
                    }
                    """.trimIndent()
                )
            }

        return response.status.value in 200..299
    }

    suspend fun listDocuments(
        userId: Int
    ): List<RemoteDocument> {

        val response =
            client.get("$baseUrl/documents/$userId")

        val body =
            response.bodyAsText()

        return parseDocumentList(body)
    }

    suspend fun getDocument(
        documentId: Int
    ): RemoteDocument? {

        val response =
            client.get("$baseUrl/documents/file/$documentId")

        val body =
            response.bodyAsText()

        return parseDocument(body)
    }

    suspend fun updateDocument(
        documentId: Int,
        userId: Int,
        name: String,
        content: String
    ): Boolean {

        val safeContent =
            content
                .replace("\"", "\\\"")
                .replace("\n", "\\n")

        val response =
            client.put("$baseUrl/documents/$documentId") {
                contentType(ContentType.Application.Json)
                setBody(
                    """
                {
                    "userId": $userId,
                    "name": "$name",
                    "content": "$safeContent"
                }
                """.trimIndent()
                )
            }

        return response.status.value in 200..299
    }

    private fun parseUser(
        body: String
    ): RemoteUser? {

        if (!body.contains("userId")) {
            return null
        }

        return RemoteUser(
            userId = extractInt(body, "userId"),
            email = extractString(body, "email"),
            message = extractString(body, "message")
        )
    }

    private fun parseDocumentList(
        body: String
    ): List<RemoteDocument> {

        if (body.isBlank() || body == "[]") {
            return emptyList()
        }

        return body
            .removePrefix("[")
            .removeSuffix("]")
            .split("},")
            .mapNotNull { raw ->

                val item =
                    if (raw.trim().endsWith("}"))
                        raw
                    else
                        "$raw}"

                if (!item.contains("id")) {
                    null
                } else {
                    RemoteDocument(
                        id = extractInt(item, "id"),
                        userId = extractInt(item, "userId"),
                        name = extractString(item, "name")
                    )
                }
            }
    }

    private fun parseDocument(
        body: String
    ): RemoteDocument? {

        if (!body.contains("id")) {
            return null
        }

        return RemoteDocument(
            id = extractInt(body, "id"),
            userId = extractInt(body, "userId"),
            name = extractString(body, "name"),
            content =
                extractString(body, "content")
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"")
        )
    }

    private fun extractString(
        body: String,
        key: String
    ): String {

        return body
            .substringAfter("\"$key\"")
            .substringAfter(":")
            .substringAfter("\"")
            .substringBefore("\"")
    }

    private fun extractInt(
        body: String,
        key: String
    ): Int {

        return body
            .substringAfter("\"$key\"")
            .substringAfter(":")
            .substringBefore(",")
            .substringBefore("}")
            .trim()
            .toInt()
    }
}