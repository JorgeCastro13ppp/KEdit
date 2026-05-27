package com.kedit.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText

import io.ktor.http.ContentType
import io.ktor.http.contentType

class AndroidRemoteRepository {

    private val baseUrl =
        "https://kedit-backend.onrender.com/"

    private val client =
        HttpClient(OkHttp)

    suspend fun login(
        email: String,
        password: String
    ): AndroidRemoteUser? {

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

        if (response.status.value !in 200..299) {
            return null
        }

        return parseUser(response.bodyAsText())
    }

    suspend fun register(
        email: String,
        password: String
    ): AndroidRemoteUser? {

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

        if (response.status.value !in 200..299) {
            return null
        }

        return parseUser(response.bodyAsText())
    }

    private fun parseUser(
        json: String
    ): AndroidRemoteUser? {

        val userId =
            Regex(""""userId"\s*:\s*(\d+)""")
                .find(json)
                ?.groupValues
                ?.get(1)
                ?.toIntOrNull()

        val email =
            Regex(""""email"\s*:\s*"([^"]+)"""")
                .find(json)
                ?.groupValues
                ?.get(1)

        if (userId == null || email == null) {
            return null
        }

        return AndroidRemoteUser(
            userId = userId,
            email = email
        )
    }
}

data class AndroidRemoteUser(
    val userId: Int,
    val email: String
)