package com.jeanharris.androidbankapp

import java.sql.Connection
import java.sql.DriverManager

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
}