package uk.ac.tees.mad.qrscanner.ui.screen.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import uk.ac.tees.mad.qrscanner.ui.components.ScannerResultDialog
import uk.ac.tees.mad.qrscanner.ui.theme.MyColors
import uk.ac.tees.mad.qrscanner.viewmodel.ScannerViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun ScannerScreen(modifier: Modifier = Modifier, viewModel: ScannerViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    var previewView = remember { PreviewView(context) }
    var hasCameraPermission by remember { mutableStateOf(false) }
    val scannedText by viewModel.scannedText.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    val showResult = remember { mutableStateOf(false) }

    val scannerBoxRect = rememberScannerBoxRect()
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            hasCameraPermission = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        LaunchedEffect(Unit) {
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also { it.surfaceProvider = previewView.surfaceProvider }
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(executor) { imageProxy ->
                        if (isScanning) {
                            viewModel.scanQRCode(imageProxy, scannerBoxRect, context){showResult.value = true}
                        } else {
                            imageProxy.close()
                        }
                    }
                }

            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.Center)
                    .border(3.dp, MyColors.themeColor)
                    .background(Color.Transparent)
            ) {
                val infiniteTransition = rememberInfiniteTransition()
                val lineOffset by infiniteTransition.animateFloat(
                    initialValue = 50f,
                    targetValue = 600f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawLine(
                        color = MyColors.themeColor,
                        start = androidx.compose.ui.geometry.Offset(40f, lineOffset),
                        end = androidx.compose.ui.geometry.Offset(size.width-40f, lineOffset),
                        strokeWidth = 4f
                    )
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Camera permission is required to scan QR codes", fontSize = 18.sp, color = Color.White)
        }
    }

    if (showResult.value){
        ScannerResultDialog(
            scannedText,
            {showResult.value = false
                viewModel.resetScanning()}
        ) {
            showResult.value = false
            viewModel.resetScanning()
        }
    }
}

@Composable
fun rememberScannerBoxRect(): Rect {
    val density = LocalDensity.current
    val context = LocalContext.current
    val screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
    val screenHeight = context.resources.displayMetrics.heightPixels.toFloat()

    val scannerBoxSize = with(density) { 250.dp.toPx() }

    return Rect(
        (screenWidth - scannerBoxSize) / 2,
        (screenHeight - scannerBoxSize) / 2,
        (screenWidth + scannerBoxSize) / 2,
        (screenHeight - scannerBoxSize) / 2 + scannerBoxSize
    )
}
