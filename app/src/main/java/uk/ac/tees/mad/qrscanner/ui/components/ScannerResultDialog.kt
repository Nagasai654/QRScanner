package uk.ac.tees.mad.qrscanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import uk.ac.tees.mad.qrscanner.ui.theme.MyColors

@Composable
fun ScannerResultDialog(
    result: String,
    onDismiss:()-> Unit,
    onSave:()-> Unit
){

    Dialog(onDismissRequest = {onDismiss()}) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Scanner Result", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(result, fontSize = 20.sp, color = MyColors.themeColor)
                Row(
                    modifier = Modifier.padding(16.dp)
                        .align(Alignment.End)
                ) {
                    TextButton({onDismiss()}) {
                        Text("Cancel")
                    }

                    TextButton({onSave()}, colors = ButtonDefaults.textButtonColors()) {
                        Text("Add to Favorite")
                    }
                }
            }
        }
    }
}