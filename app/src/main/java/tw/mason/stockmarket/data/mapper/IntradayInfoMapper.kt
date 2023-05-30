package tw.mason.stockmarket.data.mapper

import tw.mason.stockmarket.data.remote.dto.IntradayInfoDto
import tw.mason.stockmarket.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(this.timestamp, formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}