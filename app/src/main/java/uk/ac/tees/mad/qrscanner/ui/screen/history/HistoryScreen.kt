package uk.ac.tees.mad.qrscanner.ui.screen.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import uk.ac.tees.mad.qrscanner.navigation.Routes

@Composable
fun HistoryScreen(modifier: Modifier = Modifier, navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        if (user==null){
            Button({
                navController.navigate(Routes.AUTH_SCREEN)
            }) {
                Text("Login")
            }
        }
        else {
            Text("History Screen")
        }
    }
}