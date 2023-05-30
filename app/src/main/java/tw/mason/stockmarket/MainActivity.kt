package tw.mason.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import tw.mason.stockmarket.presentation.company_info.CompanyInfoScreen
import tw.mason.stockmarket.presentation.company_listings.CompanyListingsScreen
import tw.mason.stockmarket.ui.theme.StockMarketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockMarketTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val destinationsNavigator = DestinationsNavigator(navController)
                    NavHost(navController = navController, startDestination = "listings") {
                        composable("listings") {
                            CompanyListingsScreen(destinationsNavigator)
                        }
                        composable(
                            "info/{symbol}",
                            arguments = listOf(
                                navArgument("symbol") { type = NavType.StringType }
                            )
                        ) {
                            CompanyInfoScreen()
                        }
                    }
                }
            }
        }
    }
}

class DestinationsNavigator(
    private val navController: NavController
) {
    fun navigateToCompanyInfo(symbol: String) {
        navController.navigate("info/$symbol")
    }
}