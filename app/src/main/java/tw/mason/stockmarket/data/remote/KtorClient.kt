package tw.mason.stockmarket.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import tw.mason.stockmarket.BuildConfig

object KtorClient {
    private const val API_KEY = BuildConfig.API_KEY
    private val client = HttpClient(OkHttp) {

    }
}