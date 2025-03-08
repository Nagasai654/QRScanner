package uk.ac.tees.mad.qrscanner.ui.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.qrscanner.R
import uk.ac.tees.mad.qrscanner.navigation.Routes

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0.8f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = tween(1000))
        alpha.animateTo(1f, animationSpec = tween(1000))
        delay(2000)
        alpha.animateTo(0f, animationSpec = tween(500))
        navController.navigate(Routes.MAIN_SCREEN){
            popUpTo(Routes.SPLASH_SCREEN){
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().alpha(alpha.value)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.splash_icon),
                contentDescription = "splash_image",
                modifier = Modifier.size(70.dp).scale(scale.value)
            )
            Text("QR Scanner", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}
