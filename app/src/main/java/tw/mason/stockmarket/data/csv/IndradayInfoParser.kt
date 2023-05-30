package tw.mason.stockmarket.data.csv

import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tw.mason.stockmarket.data.mapper.toIntradayInfo
import tw.mason.stockmarket.data.remote.dto.IntradayInfoDto
import tw.mason.stockmarket.domain.model.IntradayInfo
import java.io.InputStream
import java.time.LocalDate

class IndradayInfoParser : CSVParser<IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(stream.reader())
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { row ->
                    val timestamp = row.getOrNull(0) ?: return@mapNotNull null
                    val close = row.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(timestamp, close.toDouble())
                    dto.toIntradayInfo()
                }
                .filter { info ->
                    info.date.dayOfMonth == LocalDate.now().minusDays(4).dayOfMonth
                }
                .sortedBy { info ->
                    info.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}