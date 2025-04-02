package uk.ac.tees.mad.qrscanner.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.qrscanner.navigation.Routes
import uk.ac.tees.mad.qrscanner.ui.components.ProfileButton
import uk.ac.tees.mad.qrscanner.ui.components.ProfileSection
import uk.ac.tees.mad.qrscanner.ui.components.QuarterCircle
import uk.ac.tees.mad.qrscanner.ui.theme.MyColors
import uk.ac.tees.mad.qrscanner.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
    ) {
    val user by viewModel.user.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
    }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        QuarterCircle()
        if (user!=null) {
            ProfileSection(
                modifier = Modifier.align(Alignment.TopCenter),
                viewModel.getName(),
                viewModel.getEmail()
            )
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                ProfileButton(title = "Clear History") { viewModel.clearHistory(context)}
                ProfileButton(title = "Log Out") { viewModel.logOut()}
            }
        }
        else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)) {
                Spacer(Modifier.height(150.dp))
                Text("No user available, please login!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Button(
                    onClick = {
                        navController.navigate(Routes.AUTH_SCREEN)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MyColors.themeColor
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Login")
                }
            }
        }
    }
}