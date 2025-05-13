package com.jeanharris.androidbankapp.data

import java.io.File
import java.util.*

object Env {
    private val props: Properties by lazy {
        val p = Properties()
        val envFile = File(System.getProperty("user.dir") + "/.env")
        if (envFile.exists()) {
            envFile.inputStream().use { p.load(it) }
        }
        p
    }

    val dbUrl get() = props.getProperty("DB_URL") ?: ""
    val dbUser get() = props.getProperty("DB_USER") ?: ""
    val dbPassword get() = props.getProperty("DB_PASSWORD") ?: ""
}