package tw.mason.stockmarket.data.remote

import okhttp3.ResponseBody


interface StockApi {

    suspend fun getListings(apiKey: String): ResponseBody

    companion object {

    }
}

