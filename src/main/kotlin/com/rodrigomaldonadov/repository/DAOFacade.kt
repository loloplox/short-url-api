package com.rodrigomaldonadov.repository

import com.rodrigomaldonadov.model.UrlResponse

interface DAOFacade {
    suspend fun getUrlByCode(code: String): UrlResponse?
    suspend fun addUrl(url: String, shortUrl: String): UrlResponse?
    suspend fun deleteUrl(id: Int): Boolean
}