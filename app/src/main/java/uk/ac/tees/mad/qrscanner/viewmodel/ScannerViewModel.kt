package uk.ac.tees.mad.qrscanner.viewmodel

import android.content.Context
import android.media.Image
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.qrscanner.data.model.ScanFavorite
import uk.ac.tees.mad.qrscanner.data.model.ScanHistory
import uk.ac.tees.mad.qrscanner.data.repository.Repository
import uk.ac.tees.mad.qrscanner.helper.NotificationHelper
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val repository: Repository
): ViewModel() {

    private val _scannedText = MutableStateFlow("")
    val scannedText: StateFlow<String> = _scannedText

    private val _isScanning = MutableStateFlow(true)
    val isScanning: StateFlow<Boolean> = _isScanning

    private val scanner: BarcodeScanner = BarcodeScanning.getClient()

    @OptIn(ExperimentalGetImage::class)
    fun scanQRCode(imageProxy: ImageProxy, scannerBox: Rect, context: Context,onResult:()-> Unit) {
        if (!_isScanning.value) {
            imageProxy.close()
            return
        }
        val mediaImage: Image? = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val boundingBox = barcode.boundingBox
                        if (boundingBox != null) {
                            val centerX = boundingBox.centerX().toFloat()
                            val centerY = boundingBox.centerY().toFloat()

                            if (scannerBox.contains(Offset(centerX, centerY))) {
                                val qrText = barcode.rawValue
                                viewModelScope.launch {
                                    _scannedText.emit(qrText ?: "")
                                    _isScanning.emit(false)
                                    NotificationHelper.showNotification(
                                        context,
                                        "Scanner Result",
                                        _scannedText.value
                                    )
                                    saveHistory(_scannedText.value)
                                    onResult()
                                }
                            }
                        }
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    fun resetScanning() {
        viewModelScope.launch {
            _isScanning.emit(true)
        }
    }

    fun saveHistory(data: String){
        val user = auth.currentUser
        if (user!=null){
            val entity = ScanHistory(
                userId = user.uid,
                data = data,
            )
            viewModelScope.launch {
                repository.addHistory(entity)
            }
        }
    }

    fun saveFavorite(context: Context){
        val user = auth.currentUser
        if (user!=null){
            val entity = ScanFavorite(
                userId = user.uid,
                data = _scannedText.value
            )
            db.collection("USERS")
                .document(user.uid)
                .collection("FAVORITE")
                .add(entity)
                .addOnSuccessListener { document->
                    document.update("id",document.id)
                    viewModelScope.launch {
                        repository.addFavorite(entity.copy(id = document.id))
                        Toast.makeText(context,"Saved to favorite", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context,"Failed to save, please check network", Toast.LENGTH_SHORT).show()
                }
        }
        else{
            Toast.makeText(context, "Login before saving to favorite", Toast.LENGTH_SHORT).show()
        }
    }

}