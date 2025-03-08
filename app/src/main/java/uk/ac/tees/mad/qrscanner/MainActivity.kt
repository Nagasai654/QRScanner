package uk.ac.tees.mad.qrscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.qrscanner.navigation.AppNavigation
import uk.ac.tees.mad.qrscanner.ui.theme.QRScannerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QRScannerTheme(true) {
                AppNavigation()
            }
        }
    }
}