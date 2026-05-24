package com.kedit.repository

import com.kedit.database.tables.UsersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    fun createUser(
        email: String,
        password: String
    ): UserDto? {

        return transaction {

            val existingUser =
                UsersTable
                    .selectAll()
                    .where {
                        UsersTable.email eq email
                    }
                    .singleOrNull()

            if (existingUser != null) {
                return@transaction null
            }

            val insertedUser =
                UsersTable.insert {
                    it[UsersTable.email] = email
                    it[UsersTable.password] = password
                }

            UserDto(
                id = insertedUser[UsersTable.id],
                email = insertedUser[UsersTable.email]
            )
        }
    }

    fun login(
        email: String,
        password: String
    ): UserDto? {

        return transaction {

            UsersTable
                .selectAll()
                .where {
                    (UsersTable.email eq email) and
                            (UsersTable.password eq password)
                }
                .singleOrNull()
                ?.toUserDto()
        }
    }

    private fun ResultRow.toUserDto(): UserDto {
        return UserDto(
            id = this[UsersTable.id],
            email = this[UsersTable.email]
        )
    }
}

data class UserDto(
    val id: Int,
    val email: String
)