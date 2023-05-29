package tw.mason.stockmarket.data.mapper

import tw.mason.stockmarket.data.local.CompanyListingEntity
import tw.mason.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() = CompanyListing(
    name = name,
    symbol = symbol,
    exchange = exchange
)

fun CompanyListing.toCompanyListingEntity() = CompanyListingEntity(
    name = name,
    symbol = symbol,
    exchange = exchange
)

fun List<CompanyListingEntity>.toCompanyListings() = map { it.toCompanyListing() }
fun List<CompanyListing>.toCompanyListingEntities() = map { it.toCompanyListingEntity() }