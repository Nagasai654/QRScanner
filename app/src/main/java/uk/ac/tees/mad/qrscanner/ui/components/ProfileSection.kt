package uk.ac.tees.mad.qrscanner.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.qrscanner.R

@Composable
fun ProfileSection(
    modifier: Modifier,
    name: String,
    email: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(70.dp))
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "profile",
            modifier = Modifier.size(100.dp)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(Modifier.height(16.dp))
        Text(name, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(email, fontSize = 16.sp)
    }
}