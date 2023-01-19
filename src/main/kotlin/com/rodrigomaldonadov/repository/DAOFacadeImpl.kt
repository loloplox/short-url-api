package com.rodrigomaldonadov.repository

import com.rodrigomaldonadov.DatabaseFactory.dbQuery
import com.rodrigomaldonadov.model.UrlResponse
import com.rodrigomaldonadov.model.Urls
import io.ktor.server.util.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToUrlResponse(row: ResultRow) = UrlResponse(
        id = row[Urls.id],
        url = row[Urls.url],
        urlEncode = row[Urls.shortUrl],
    )

    override suspend fun getUrlByCode(code: String): UrlResponse? = dbQuery {
        Urls
            .select(Urls.shortUrl eq code)
            .map(::resultRowToUrlResponse)
            .singleOrNull()
    }

    override suspend fun addUrl(url: String, shortUrl: String): UrlResponse? = dbQuery {
        val insertStatement = Urls.insert {
            it[Urls.url] = url
            it[Urls.shortUrl] = shortUrl
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUrlResponse)
    }

    override suspend fun deleteUrl(id: Int): Boolean = dbQuery {
        Urls.deleteWhere { Urls.id eq id } > 0
    }
}

val dao: DAOFacade = DAOFacadeImpl()