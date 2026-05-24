package com.kedit.database

import com.kedit.database.tables.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.kedit.database.tables.DocumentsTable

object DatabaseFactory {

    fun init() {

        Database.connect(
            url = "jdbc:postgresql://localhost:5432/kedit_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "toor"
        )

        transaction {
            SchemaUtils.create(
                UsersTable,
                DocumentsTable
            )
        }
    }
}