package com.kedit.database

import com.kedit.database.tables.DocumentsTable
import com.kedit.database.tables.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {

        val databaseUrl =
            System.getenv("DATABASE_URL")
                ?: "jdbc:postgresql://localhost:5432/kedit_db"

        val databaseUser =
            System.getenv("DB_USER")
                ?: "postgres"

        val databasePassword =
            System.getenv("DB_PASSWORD")
                ?: "toor"

        Database.connect(
            url = databaseUrl,
            driver = "org.postgresql.Driver",
            user = databaseUser,
            password = databasePassword
        )

        transaction {
            SchemaUtils.create(
                UsersTable,
                DocumentsTable
            )
        }
    }
}