package tw.mason.stockmarket.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.serialization.json.Json
import tw.mason.stockmarket.data.remote.dto.CompanyInfoDto
import java.io.File

class StockApiImpl(
    private val baseUrl: String,
    private val apiKey: String
) : StockApi {

    private val client: HttpClient = HttpClient(OkHttp) {
        install(Logging) {
            level = LogLevel.BODY
        }
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl
                parameters.append("apikey", apiKey)
            }
        }
    }

    override suspend fun getListingsCsv(file: File): File {
        val channel: ByteReadChannel = client.get("query?function=LISTING_STATUS").bodyAsChannel()
        while (!channel.isClosedForRead) {
            val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
            while (!packet.isEmpty) {
                val bytes = packet.readBytes()
                file.appendBytes(bytes)
            }
        }
        return file
    }

    override suspend fun getIntradayInfoCsv(file: File, symbol: String): File {
        val channel: ByteReadChannel =
            client.get("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv") {
                parameter("symbol", symbol)
            }.bodyAsChannel()
        while (!channel.isClosedForRead) {
            val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
            while (!packet.isEmpty) {
                val bytes = packet.readBytes()
                file.appendBytes(bytes)
            }
        }
        return file
    }

    override suspend fun getCompanyInfo(symbol: String): CompanyInfoDto {
        val response = client.get("query?function=OVERVIEW") {
            parameter("symbol", symbol)
        }
        return response.body()
    }
}