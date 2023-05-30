package tw.mason.stockmarket.data.remote

import tw.mason.stockmarket.data.remote.dto.CompanyInfoDto
import java.io.File


interface StockApi {

    suspend fun getListingsCsv(file: File): File

    suspend fun getIntradayInfoCsv(file: File, symbol: String): File

    suspend fun getCompanyInfo(symbol: String): CompanyInfoDto
}

