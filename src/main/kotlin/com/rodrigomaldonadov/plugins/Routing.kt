package com.rodrigomaldonadov.plugins

import com.rodrigomaldonadov.routing.urlShortRoute
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        urlShortRoute()
    }
}
