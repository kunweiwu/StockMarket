package tw.mason.stockmarket.data.remote

import okhttp3.ResponseBody


interface StockApi {

    suspend fun getListings(): ResponseBody

    companion object {

    }
}

