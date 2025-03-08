package uk.ac.tees.mad.qrscanner.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.qrscanner.ui.components.MainBottomNavigation
import uk.ac.tees.mad.qrscanner.ui.screen.history.HistoryScreen
import uk.ac.tees.mad.qrscanner.ui.screen.profile.ProfileScreen
import uk.ac.tees.mad.qrscanner.ui.screen.scanner.ScannerScreen

@Composable
fun MainScreen() {
    val selectedScreen = remember { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            MainBottomNavigation(
                selectedScreen.intValue,
                {selectedScreen.intValue = it}
            )
        }
    ) { paddingValues->
        val modifier = Modifier.padding(paddingValues)
        when(selectedScreen.intValue){
            0 -> ScannerScreen(modifier)
            1 -> HistoryScreen(modifier)
            2 -> ProfileScreen(modifier)
        }
    }
}