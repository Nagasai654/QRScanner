package uk.ac.tees.mad.qrscanner.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.qrscanner.R
import uk.ac.tees.mad.qrscanner.ui.theme.MyColors

@Composable
fun MainBottomNavigation(
    selected:Int,
    onClick:(Int)-> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ){
        Card(modifier = Modifier.padding(top = 30.dp, bottom = 16.dp)) {
            Row(modifier = Modifier.padding(12.dp)) {
                NavItem(
                    selected = selected==1,
                    onClick = {onClick(1)},
                    icon = R.drawable.baseline_history_24,
                    label = "History",
                )
                Spacer(Modifier.width(100.dp))
                NavItem(
                    selected = selected==2,
                    onClick = {onClick(2)},
                    icon = R.drawable.baseline_person_24,
                    label = "Profile",
                )
            }
        }
        IconButton(
            {onClick(0)},
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MyColors.themeColor
            ),
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_qr_code_scanner_24),
                contentDescription = "qr_icon",
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}
