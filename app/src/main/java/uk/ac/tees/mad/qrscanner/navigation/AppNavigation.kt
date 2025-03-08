package uk.ac.tees.mad.qrscanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.qrscanner.ui.screen.main.MainScreen
import uk.ac.tees.mad.qrscanner.ui.screen.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.SPLASH_SCREEN){

        composable(Routes.SPLASH_SCREEN) {
            SplashScreen(navController)
        }

        composable(Routes.MAIN_SCREEN) {
            MainScreen()
        }
    }
}