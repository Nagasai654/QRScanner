package uk.ac.tees.mad.qrscanner.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.qrscanner.ui.components.ProfileButton
import uk.ac.tees.mad.qrscanner.ui.components.ProfileSection
import uk.ac.tees.mad.qrscanner.ui.components.QuarterCircle
import uk.ac.tees.mad.qrscanner.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
    ) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        QuarterCircle()
        ProfileSection(
            modifier = Modifier.align(Alignment.TopCenter),
            viewModel.getName(),
            viewModel.getEmail()
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            ProfileButton(title = "Clear History") { }
            ProfileButton(title = "Log Out") { }
        }
    }
}