package uk.ac.tees.mad.qrscanner.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.translate
import uk.ac.tees.mad.qrscanner.ui.theme.MyColors

@Composable
fun QuarterCircle() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = 450f,top = -1000f) {
            drawCircle(MyColors.themeColor, radius = 1000f)
        }
    }
}