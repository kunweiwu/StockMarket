package tw.mason.stockmarket.domain.repository

import kotlinx.coroutines.flow.Flow
import tw.mason.stockmarket.domain.model.CompanyInfo
import tw.mason.stockmarket.domain.model.CompanyListing
import tw.mason.stockmarket.domain.model.IntradayInfo
import tw.mason.stockmarket.util.Resource

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>


    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}