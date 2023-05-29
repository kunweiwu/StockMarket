package tw.mason.stockmarket.data.csv

import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tw.mason.stockmarket.domain.model.CompanyListing
import java.io.InputStream

class CompanyListingsParser : CSVParser<CompanyListing> {

    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(stream.reader())
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { row ->
                    val symbol = row.getOrNull(0)
                    val name = row.getOrNull(1)
                    val exchange = row.getOrNull(2)
                    CompanyListing(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }.also {
                    csvReader.close()
                }
        }
    }
}