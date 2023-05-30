package tw.mason.stockmarket.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tw.mason.stockmarket.BuildConfig
import tw.mason.stockmarket.data.csv.CSVParser
import tw.mason.stockmarket.data.csv.CompanyListingsParser
import tw.mason.stockmarket.data.csv.IndradayInfoParser
import tw.mason.stockmarket.data.local.StockDatabase
import tw.mason.stockmarket.data.remote.StockApi
import tw.mason.stockmarket.data.remote.StockApiImpl
import tw.mason.stockmarket.data.repository.StockRepositoryImpl
import tw.mason.stockmarket.domain.model.CompanyListing
import tw.mason.stockmarket.domain.model.IntradayInfo
import tw.mason.stockmarket.domain.repository.StockRepository
import tw.mason.stockmarket.presentation.company_info.CompanyInfoViewModel
import tw.mason.stockmarket.presentation.company_listings.CompanyListingsViewModel

val appModule = module {
    single<CSVParser<CompanyListing>>(named("CompanyListingsParser")) { CompanyListingsParser() }
    single<CSVParser<IntradayInfo>>(named("IndradayInfoParser")) { IndradayInfoParser() }

    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = StockDatabase::class.java,
            name = "stock.db"
        ).build()
    }

    single<StockApi> {
        StockApiImpl(
            baseUrl = BuildConfig.API_HOST,
            apiKey = BuildConfig.API_KEY
        )
    }

    single<StockRepository> {
        StockRepositoryImpl(
            db = get(),
            api = get(),
            companyListingsParser = get(named("CompanyListingsParser")),
            intradayInfoParser = get(named("IndradayInfoParser"))
        )
    }

    viewModel { CompanyListingsViewModel(get()) }
    viewModel { CompanyInfoViewModel(get(), get()) }
}