package tw.mason.stockmarket.presentation.company_info

import tw.mason.stockmarket.domain.model.CompanyInfo
import tw.mason.stockmarket.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
