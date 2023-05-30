package tw.mason.stockmarket.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyInfoDto(
    @SerialName("Address")
    val address: String?,
    @SerialName("AnalystTargetPrice")
    val analystTargetPrice: String?,
    @SerialName("AssetType")
    val assetType: String?,
    @SerialName("Beta")
    val beta: String?,
    @SerialName("BookValue")
    val bookValue: String?,
    @SerialName("CIK")
    val cIK: String?,
    @SerialName("Country")
    val country: String?,
    @SerialName("Currency")
    val currency: String?,
    @SerialName("50DayMovingAverage")
    val fiftyDayMovingAverage: String?,
    @SerialName("200DayMovingAverage")
    val twentyDayMovingAverage: String?,
    @SerialName("Description")
    val description: String?,
    @SerialName("DilutedEPSTTM")
    val dilutedEPSTTM: String?,
    @SerialName("DividendDate")
    val dividendDate: String?,
    @SerialName("DividendPerShare")
    val dividendPerShare: String?,
    @SerialName("DividendYield")
    val dividendYield: String?,
    @SerialName("EBITDA")
    val eBITDA: String?,
    @SerialName("EPS")
    val ePS: String?,
    @SerialName("EVToEBITDA")
    val eVToEBITDA: String?,
    @SerialName("EVToRevenue")
    val eVToRevenue: String?,
    @SerialName("ExDividendDate")
    val exDividendDate: String?,
    @SerialName("Exchange")
    val exchange: String?,
    @SerialName("FiscalYearEnd")
    val fiscalYearEnd: String?,
    @SerialName("ForwardPE")
    val forwardPE: String?,
    @SerialName("GrossProfitTTM")
    val grossProfitTTM: String?,
    @SerialName("Industry")
    val industry: String?,
    @SerialName("LatestQuarter")
    val latestQuarter: String?,
    @SerialName("MarketCapitalization")
    val marketCapitalization: String?,
    @SerialName("Name")
    val name: String?,
    @SerialName("OperatingMarginTTM")
    val operatingMarginTTM: String?,
    @SerialName("PEGRatio")
    val pEGRatio: String?,
    @SerialName("PERatio")
    val pERatio: String?,
    @SerialName("PriceToBookRatio")
    val priceToBookRatio: String?,
    @SerialName("PriceToSalesRatioTTM")
    val priceToSalesRatioTTM: String?,
    @SerialName("ProfitMargin")
    val profitMargin: String?,
    @SerialName("QuarterlyEarningsGrowthYOY")
    val quarterlyEarningsGrowthYOY: String?,
    @SerialName("QuarterlyRevenueGrowthYOY")
    val quarterlyRevenueGrowthYOY: String?,
    @SerialName("ReturnOnAssetsTTM")
    val returnOnAssetsTTM: String?,
    @SerialName("ReturnOnEquityTTM")
    val returnOnEquityTTM: String?,
    @SerialName("RevenuePerShareTTM")
    val revenuePerShareTTM: String?,
    @SerialName("RevenueTTM")
    val revenueTTM: String?,
    @SerialName("Sector")
    val sector: String?,
    @SerialName("SharesOutstanding")
    val sharesOutstanding: String?,
    @SerialName("Symbol")
    val symbol: String?,
    @SerialName("TrailingPE")
    val trailingPE: String?,
    @SerialName("52WeekHigh")
    val weekHigh: String?,
    @SerialName("52WeekLow")
    val weekLow: String?
)