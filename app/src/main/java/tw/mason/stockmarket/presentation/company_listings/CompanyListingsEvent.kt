package tw.mason.stockmarket.presentation.company_listings

sealed class CompanyListingsEvent {
    object Refresh : CompanyListingsEvent()
    data class OnSearchQueryChanged(val query: String) : CompanyListingsEvent()

}
