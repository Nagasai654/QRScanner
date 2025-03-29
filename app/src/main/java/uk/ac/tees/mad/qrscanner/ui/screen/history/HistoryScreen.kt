package uk.ac.tees.mad.qrscanner.ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import uk.ac.tees.mad.qrscanner.navigation.Routes
import uk.ac.tees.mad.qrscanner.ui.components.HistoryItem
import uk.ac.tees.mad.qrscanner.ui.theme.MyColors
import uk.ac.tees.mad.qrscanner.viewmodel.HistoryViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val user = FirebaseAuth.getInstance().currentUser
    val selectedTab = remember { mutableIntStateOf(0) }
    val history by viewModel.history.collectAsState()
    val favorite by viewModel.favorite.collectAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        if (user == null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "To save history and favorite, you have login!",
                    fontSize = 22.sp, fontWeight = FontWeight.Bold
                )
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
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                SingleChoiceSegmentedButtonRow(
                    space = 16.dp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    SegmentedButton(
                        onClick = { selectedTab.intValue = 0 },
                        selected = selectedTab.intValue == 0,
                        shape = RoundedCornerShape(12.dp),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = MyColors.themeColor
                        )
                    ) {
                        Text("History")
                    }
                    SegmentedButton(
                        onClick = { selectedTab.intValue = 1 },
                        selected = selectedTab.intValue == 1,
                        shape = RoundedCornerShape(12.dp),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = MyColors.themeColor
                        )
                    ) {
                        Text("Favorites")
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (selectedTab.intValue==0){
                        items(history) { item ->
                            HistoryItem(
                                item.data,
                                formatTimestamp(item.time)
                            ) {
                                viewModel.deleteHistory(item)
                            }
                        }
                    }
                    else{
                        items(favorite) { item ->
                            HistoryItem(
                                item.data,
                                formatTimestamp(item.time)
                            ) {
                                viewModel.deleteFavorite(item)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale.getDefault())
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}