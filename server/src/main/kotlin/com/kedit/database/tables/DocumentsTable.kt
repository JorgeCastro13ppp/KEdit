package com.kedit.database.tables

import org.jetbrains.exposed.sql.Table

object DocumentsTable : Table("documents") {

    val id = integer("id").autoIncrement()

    val userId = integer("user_id")
        .references(UsersTable.id)

    val name = varchar("name", 255)

    val content = text("content")

    override val primaryKey =
        PrimaryKey(id)
}