package uk.ac.tees.mad.qrscanner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.qrscanner.ui.theme.MyColors

@Composable
fun NavItem(
    selected: Boolean,
    onClick:()-> Unit,
    icon: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable{
            onClick()
        }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = label,
            tint = if (selected) MyColors.themeColor else Color.White
        )
        Text(label, color = if (selected) MyColors.themeColor else Color.White)
    }
}