package com.rodrigomaldonadov.model

import com.rodrigomaldonadov.repository.dao
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.floor

const val MAX_LENGTH_ENCODE = 6

@Serializable
data class UrlModel(val url: String) {
    suspend fun getUrlEncode(): UrlResponse {
        var urlEncode: String = ""
        val alphabet: List<Char> = listOf('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z')

        while (urlEncode.length < MAX_LENGTH_ENCODE) {
            if (floor(Math.random() * 10) <= 5) {
//            Escoger un numero aleatorio del 0 al 10
                val numberRandom = floor(Math.random() * 10).toInt()
                urlEncode += numberRandom
            } else  {
//            Escoger un numero aleatorio para escoger una letra del alfabeto
                if (floor(Math.random() * 10) <= 5) {
//                    Colocar la letra en minusculas
                    val numberRandom = floor(Math.random() * 26).toInt()
                    urlEncode += alphabet[numberRandom].lowercaseChar()
                } else {
//                    Colocar la letra en mayusculas
                    val numberRandom = floor(Math.random() * 26).toInt()
                    urlEncode += alphabet[numberRandom].uppercaseChar()
                }
            }
        }

        return UrlResponse.newEntry(this.url, urlEncode)
    }
}


@Serializable
class UrlResponse(val id: Int, val url: String, val urlEncode: String) {
    companion object {
        suspend fun newEntry(url: String, urlEncode: String): UrlResponse {
            val urlInsert = dao.addUrl(url, urlEncode)
            if (urlInsert != null) {
                return UrlResponse(urlInsert.id, urlInsert.url, urlInsert.urlEncode)
            } else {
                throw Error("Error DB")
            }
        }
    }
}

object Urls: Table() {
    val id = integer("id").autoIncrement()
    val url = varchar("url", 10000)
    val shortUrl = varchar("short_url", 6)

    override val primaryKey = PrimaryKey(id)
}
