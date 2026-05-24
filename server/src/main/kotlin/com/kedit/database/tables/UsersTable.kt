package com.kedit.database.tables

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {

    val id = integer("id").autoIncrement()

    val email = varchar("email", 255).uniqueIndex()

    val password = varchar("password", 255)

    override val primaryKey =
        PrimaryKey(id)
}