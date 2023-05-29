package tw.mason.stockmarket.di

import org.koin.dsl.module
import tw.mason.stockmarket.data.csv.CSVParser
import tw.mason.stockmarket.data.csv.CompanyListingsParser
import tw.mason.stockmarket.data.repository.StockRepositoryImpl
import tw.mason.stockmarket.domain.model.CompanyListing
import tw.mason.stockmarket.domain.repository.StockRepository

val appModule = module {
    single<CSVParser<CompanyListing>> { CompanyListingsParser() }
    single<StockRepository> {
        StockRepositoryImpl(
            db = TODO(),
            api = TODO(),
            companyListingsParser = get()
        )
    }
}