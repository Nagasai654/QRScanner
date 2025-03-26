package uk.ac.tees.mad.qrscanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.qrscanner.R
import uk.ac.tees.mad.qrscanner.ui.theme.MyColors

@Composable
fun HistoryItem(
    data: String,
    date: String,
    onDelete:()-> Unit
) {
    Card(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_qr_code_scanner_24),
                contentDescription = "icon",
                tint = MyColors.themeColor,
                modifier = Modifier.size(50.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(data,
                        fontSize = 20.sp,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton({onDelete()}) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "delete_icon",
                            tint = MyColors.themeColor
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Data",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f))
                    Text(date,
                        fontSize = 16.sp,
                        color = Color.Gray
                        )
                }
            }
        }
    }
}