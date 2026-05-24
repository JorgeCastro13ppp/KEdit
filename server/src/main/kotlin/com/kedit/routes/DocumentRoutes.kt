package com.kedit.routes

import com.kedit.repository.DocumentRepository
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.documentRoutes(
    documentRepository: DocumentRepository
) {

    post("/documents") {

        val body =
            call.receiveText()

        val userId =
            extractJsonInt(
                body = body,
                key = "userId"
            )

        val name =
            extractJsonValue(
                body = body,
                key = "name"
            )

        val content =
            extractJsonValue(
                body = body,
                key = "content"
            )
                .replace("\\n", "\n")
                .replace("\\\"", "\"")

        val document =
            documentRepository.saveDocument(
                userId = userId,
                name = name,
                content = content
            )

        call.respondText(
            text =
                """
                {
                    "id": ${document.id},
                    "userId": ${document.userId},
                    "name": "${document.name}",
                    "message": "Documento guardado correctamente"
                }
                """.trimIndent(),

            contentType = ContentType.Application.Json,
            status = HttpStatusCode.Created
        )
    }

    get("/documents/{userId}") {

        val userId =
            call.parameters["userId"]?.toIntOrNull()

        if (userId == null) {

            call.respondText(
                text = """{"message":"userId inválido"}""",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.BadRequest
            )

            return@get
        }

        val documents =
            documentRepository.listDocuments(
                userId = userId
            )

        val json =
            documents.joinToString(
                separator = ",",
                prefix = "[",
                postfix = "]"
            ) {
                """
                {
                    "id": ${it.id},
                    "userId": ${it.userId},
                    "name": "${it.name}"
                }
                """.trimIndent()
            }

        call.respondText(
            text = json,
            contentType = ContentType.Application.Json
        )
    }

    get("/documents/file/{id}") {

        val id =
            call.parameters["id"]?.toIntOrNull()

        if (id == null) {

            call.respondText(
                text = """{"message":"id inválido"}""",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.BadRequest
            )

            return@get
        }

        val document =
            documentRepository.getDocument(
                id = id
            )

        if (document == null) {

            call.respondText(
                text = """{"message":"Documento no encontrado"}""",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.NotFound
            )

        } else {

            call.respondText(
                text =
                    """
                    {
                        "id": ${document.id},
                        "userId": ${document.userId},
                        "name": "${document.name}",
                        "content": "${document.content.replace("\n", "\\n").replace("\"", "\\\"")}"
                    }
                    """.trimIndent(),

                contentType = ContentType.Application.Json
            )
        }
    }

    put("/documents/{id}") {

        val id =
            call.parameters["id"]?.toIntOrNull()

        if (id == null) {
            call.respondText(
                text = """{"message":"id inválido"}""",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.BadRequest
            )

            return@put
        }

        val body =
            call.receiveText()

        val userId =
            extractJsonInt(
                body = body,
                key = "userId"
            )

        val name =
            extractJsonValue(
                body = body,
                key = "name"
            )

        val content =
            extractJsonValue(
                body = body,
                key = "content"
            )
                .replace("\\n", "\n")
                .replace("\\\"", "\"")

        val updated =
            documentRepository.updateDocument(
                id = id,
                userId = userId,
                name = name,
                content = content
            )

        if (updated) {
            call.respondText(
                text = """{"message":"Documento actualizado correctamente"}""",
                contentType = ContentType.Application.Json
            )
        } else {
            call.respondText(
                text = """{"message":"Documento no encontrado o sin permisos"}""",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.NotFound
            )
        }
    }
}