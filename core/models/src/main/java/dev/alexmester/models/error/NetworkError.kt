package dev.alexmester.models.error


sealed class NetworkError : Exception() {

    data object NoInternet : NetworkError()

    data class HttpError(val code: Int, override val message: String? = null) : NetworkError()

    data class RateLimit(val retryAfterSeconds: Long? = null) : NetworkError()

    data object Unauthorized : NetworkError()

    data object Timeout : NetworkError()

    data class ParseError(override val cause: Throwable? = null) : NetworkError()

    data class Unknown(override val cause: Throwable? = null, override val message: String? = null) : NetworkError()
}