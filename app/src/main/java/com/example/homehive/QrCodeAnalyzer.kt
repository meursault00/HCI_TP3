package com.example.homehive

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QrCodeAnalyzer (
    private val onQrCodeScanned: (String) -> Unit
): ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
        ImageFormat.NV21,
        ImageFormat.NV16
    )
    override fun analyze(image: ImageProxy) {
        if(image.format in supportedImageFormats){
            val bytes = image.planes.first().buffer.toByteArray()


            // Scanner formateo,
            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )

            // informacion del qr code
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

            // si el qr code es valido
            try{
                val result = MultiFormatReader().apply{
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE
                            )
                        )
                    )
                }.decode(binaryBitmap)
                onQrCodeScanned(result.text)
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }
            finally {
                image.close()
            }
        }
    }


    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also{
            get(it)
        }
    }

}