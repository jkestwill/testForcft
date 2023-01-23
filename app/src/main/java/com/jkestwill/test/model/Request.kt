package com.jkestwill.test.model


inline fun <reified T> apiRequest(block: () -> T) = try {
    Response.success(block())
} catch (e: Throwable) {
    Response.failure(e)
}


sealed class Response<out T>() {

    open fun getSuccess(): T =
        throw IllegalStateException("getSuccess() can be called only on successful response")

    open suspend fun onSuccess(block:suspend (T) -> Unit): Response<T> = this
    open fun getFailure(): Throwable =
        throw IllegalStateException("getFailureCause() can be called only on failed response")

    open fun onFailure(block: (Throwable) -> Unit): Response<T> = this


    private class Success<out T>(val value: T) : Response<T>() {
        override suspend fun onSuccess(block:suspend (T) -> Unit): Response<T> {
            block(value)
            return this
        }

        override fun getSuccess(): T {
            return value
        }
    }

    private class Failure<out T>(val throwable: Throwable) : Response<T>() {
        override fun onFailure(block: (Throwable) -> Unit): Response<T> {
            block(throwable)
            return this
        }

        override fun getFailure(): Throwable {
            return throwable
        }
    }

    companion object {
        @JvmStatic
        fun <T> success(t: T): Response<T> = Success(t)

        @JvmStatic
        fun <T> failure(t: Throwable): Response<T> = Failure(t)
    }
}