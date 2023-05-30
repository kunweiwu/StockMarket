package tw.mason.stockmarket.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import tw.mason.stockmarket.data.csv.CSVParser
import tw.mason.stockmarket.data.local.StockDatabase
import tw.mason.stockmarket.data.mapper.toCompanyInfo
import tw.mason.stockmarket.data.mapper.toCompanyListingEntities
import tw.mason.stockmarket.data.mapper.toCompanyListings
import tw.mason.stockmarket.data.remote.StockApi
import tw.mason.stockmarket.domain.model.CompanyInfo
import tw.mason.stockmarket.domain.model.CompanyListing
import tw.mason.stockmarket.domain.model.IntradayInfo
import tw.mason.stockmarket.domain.repository.StockRepository
import tw.mason.stockmarket.util.Resource
import java.io.File

class StockRepositoryImpl(
    db: StockDatabase,
    private val api: StockApi,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = dao.searchCompanyListings(query)
            emit(Resource.Success(localListings.toCompanyListings()))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings: List<CompanyListing>? = try {
                val tmpFile = File.createTempFile("companyListing", ".csv")
                val csvFile = api.getListingsCsv(tmpFile)
                val list = companyListingsParser.parse(csvFile.inputStream())
                csvFile.delete()
                list
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data!"))
                null
            }
            if (remoteListings != null) {
                dao.clearCompanyListings()
                dao.insertCompanyListings(remoteListings.toCompanyListingEntities())
                emit(Resource.Success(dao.searchCompanyListings("").toCompanyListings()))
                emit(Resource.Loading(false))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val tmpFile = withContext(Dispatchers.IO) {
                File.createTempFile("intradayInfo", ".csv")
            }
            val csvFile = api.getIntradayInfoCsv(tmpFile, symbol)
            val list = intradayInfoParser.parse(csvFile.inputStream())
            tmpFile.delete()
            Resource.Success(list)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Couldn't load $symbol Intraday info!")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Couldn't load $symbol Company info!")
        }
    }
}