package com.example.majorproject.repositories

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TFLiteRepo(private val context: Context) {
    private var interpreter :Interpreter?=null
    private val imgWidth = 256
    private val imgHeight = 256
    private val channels = 3
    private lateinit var labels:List<String>

    suspend fun loadModel(){
        Log.d("Test","Model loading start")
        val modelBuffer = loadModelFile("updated_nitk.tflite")
        val options = Interpreter.Options().apply {
            setUseNNAPI(false)
        }
        interpreter = Interpreter(modelBuffer, options)
        labels = loadLabel()

        Log.d("Test","Model loading finish")

    }


    private suspend fun loadModelFile(modelFileName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelFileName)
        FileInputStream(fileDescriptor.fileDescriptor).use { inputStream ->
            FileChannel.MapMode.READ_ONLY.let {
                return inputStream.channel.map(it, fileDescriptor.startOffset, fileDescriptor.declaredLength)
            }
        }
    }
    private suspend fun loadLabel():List<String>{
        return context.assets.open("labels.txt").bufferedReader().readLines()

    }

    suspend fun classifyImage(imagePath: String):Pair<String,Float>{
        if(interpreter==null){
            return "Model not loaded" to   0f
        }
//        Log.d("Test","ClassifyImage repo Function called")
        val bitmap =loadBitmapFromUri(imagePath)
        if(bitmap==null){
            return Pair("Bitmap is Null", 0f)
        }
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap,imgWidth,imgHeight,true)
        val inputBuffer =  convertBitmapToByteBuffer(resizedBitmap)
        val outputArray = Array(1){FloatArray(labels.size)}
            Log.d("Test","In Repo above interPreter")
        try {
            interpreter?.run(inputBuffer, outputArray)
            Log.d("Test", "Inference successful")
        } catch (e: Exception) {
            Log.e("Test", "Error during inference: ${e.message}")
        }
        val resultIndex = outputArray[0].indices.maxByOrNull { outputArray[0][it] } ?: return Pair("Unknown", 0f)
        val confidence = outputArray[0][resultIndex]
//        return Pair(labels[resultIndex],confidence)
        return if(confidence>=0.6) Pair(labels[resultIndex],confidence) else Pair("Unknown",0f)
//
//        Log.d("Test","In Repo after interPreter : confidence : $confidence")
//            val prediction = labels.zip(outputArray[0].toList())
//                .sortedByDescending { it.second }
////            prediction.forEach{
////                Log.d("Test","${it.first} : ${it.second}")
////            }

//            return prediction.first()


    }
    private  fun loadBitmapFromUri(imagePath: String):Bitmap?{
        return try {
            val uri = Uri.parse(imagePath)
            context.contentResolver.openInputStream(uri).use { inputStream->
                BitmapFactory.decodeStream(inputStream)
            }
        }catch (e:Exception){
            Log.d("Test","Failed to load Bitmap from uri : ${e.message}")
            null
        }
    }

    private suspend fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        Log.d("Test","Repo function called convert bitmap called")
        val byteBuffer = ByteBuffer.allocateDirect(4*imgWidth*imgHeight*channels)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(imgWidth*imgHeight)
        bitmap.getPixels(intValues,0,imgWidth,0,0,imgWidth,imgHeight)

        for(pixelValue in intValues){
            val r = (pixelValue shr 16 and 0xFF).toFloat() / 255.0f
            val g = (pixelValue shr 8 and 0xFF).toFloat() / 255.0f
            val b = (pixelValue and 0xFF).toFloat() / 255.0f

            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)

        }
        Log.d("Test","Repo function called convert bitmap finish")
        return byteBuffer

    }





}