package com.rodrigomaldonadov.routing

import com.rodrigomaldonadov.model.UrlModel
import com.rodrigomaldonadov.model.UrlResponse
import com.rodrigomaldonadov.repository.dao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.urlShortRoute() {
    route("/api/v1") {
        post("/encode") {
            val urlRequest = call.receive<UrlModel>()
            val urlResponse: UrlResponse = urlRequest.getUrlEncode()

            call.respond(urlResponse)
        }

        get("/{code}") {
            val code = call.parameters["code"] ?: return@get call.respondText("Parameter is not valid", status = HttpStatusCode.NotAcceptable)

            val urlObject = dao.getUrlByCode(code)

            if (urlObject != null) {
                call.respondRedirect(urlObject.url)
            } else {
                return@get call.respondText("Url Not found", status = HttpStatusCode.NotFound)
            }
        }
    }
}