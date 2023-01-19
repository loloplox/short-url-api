package com.rodrigomaldonadov

import com.rodrigomaldonadov.model.Urls
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val database = Database.connect("jdbc:mysql://localhost:3306/short_url_db", driver = "com.mysql.cj.jdbc.Driver", user = "root", password = "")

        transaction(database) {
            SchemaUtils.create(Urls)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
