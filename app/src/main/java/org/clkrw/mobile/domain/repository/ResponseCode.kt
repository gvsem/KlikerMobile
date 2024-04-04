package org.clkrw.mobile.domain.repository


enum class ResponseCode {
    CREATED,
    REPEATED,
    NOT_FOUND,
    UNKNOWN,
}


fun toResponseCode(value: Int): ResponseCode {
    return when(value) {
        200 -> ResponseCode.CREATED
        201 -> ResponseCode.REPEATED
        404 -> ResponseCode.NOT_FOUND
        else -> ResponseCode.UNKNOWN
    }
}
