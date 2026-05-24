package com.kedit.repository

import com.kedit.database.tables.DocumentsTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class DocumentRepository {

    fun saveDocument(
        userId: Int,
        name: String,
        content: String
    ): DocumentDto {

        return transaction {

            val insertedDocument =
                DocumentsTable.insert {
                    it[DocumentsTable.userId] = userId
                    it[DocumentsTable.name] = name
                    it[DocumentsTable.content] = content
                }

            DocumentDto(
                id = insertedDocument[DocumentsTable.id],
                userId = insertedDocument[DocumentsTable.userId],
                name = insertedDocument[DocumentsTable.name],
                content = insertedDocument[DocumentsTable.content]
            )
        }
    }

    fun listDocuments(
        userId: Int
    ): List<DocumentDto> {

        return transaction {

            DocumentsTable
                .selectAll()
                .where {
                    DocumentsTable.userId eq userId
                }
                .map {
                    it.toDocumentDto()
                }
        }
    }

    fun getDocument(
        id: Int
    ): DocumentDto? {

        return transaction {

            DocumentsTable
                .selectAll()
                .where {
                    DocumentsTable.id eq id
                }
                .singleOrNull()
                ?.toDocumentDto()
        }
    }

    fun updateDocument(
        id: Int,
        userId: Int,
        name: String,
        content: String
    ): Boolean {

        return transaction {

            val updatedRows =
                DocumentsTable.update(
                    where = {
                        (DocumentsTable.id eq id) and
                                (DocumentsTable.userId eq userId)
                    }
                ) {
                    it[DocumentsTable.name] = name
                    it[DocumentsTable.content] = content
                }

            updatedRows > 0
        }
    }

    private fun ResultRow.toDocumentDto(): DocumentDto {
        return DocumentDto(
            id = this[DocumentsTable.id],
            userId = this[DocumentsTable.userId],
            name = this[DocumentsTable.name],
            content = this[DocumentsTable.content]
        )
    }
}

data class DocumentDto(
    val id: Int,
    val userId: Int,
    val name: String,
    val content: String
)