package tw.mason.stockmarket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tw.mason.stockmarket.data.csv.CSVParser
import tw.mason.stockmarket.data.local.StockDatabase
import tw.mason.stockmarket.data.mapper.toCompanyListingEntities
import tw.mason.stockmarket.data.mapper.toCompanyListings
import tw.mason.stockmarket.data.remote.StockApi
import tw.mason.stockmarket.domain.model.CompanyListing
import tw.mason.stockmarket.domain.repository.StockRepository
import tw.mason.stockmarket.util.Resource

class StockRepositoryImpl(
    db: StockDatabase,
    private val api: StockApi,
    private val companyListingsParser: CSVParser<CompanyListing>
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

            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
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
        }
    }

}