package com.jeanharris.androidbankapp

import java.sql.Connection
import java.sql.DriverManager
import com.jeanharris.androidbankapp.data.Env


object DbRepository {
    fun connectToDatabase(dbUrl: String, dbUser: String, dbPassword: String): Connection? {
        return try {
            Class.forName("org.postgresql.Driver")
            DriverManager.getConnection(dbUrl, dbUser, dbPassword)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun validateUserLogin(username: String, password: String): Boolean {
        return try {
            Class.forName("org.postgresql.Driver")
            DriverManager.getConnection(
                Env.dbUrl,
                Env.dbUser,
                Env.dbPassword
            ).use { conn ->

                val sql =
                """
                    SELECT u.user_id
                    FROM users u
                    JOIN passwords p ON u.password_id = p.password_id
                    WHERE u.username = ? AND p.hash = ?
                """.trimIndent()

                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, username)
                    stmt.setString(2, password)
                    val rs = stmt.executeQuery()
                    return rs.next()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}