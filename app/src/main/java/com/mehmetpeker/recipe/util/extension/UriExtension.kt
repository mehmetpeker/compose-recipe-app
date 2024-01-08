package com.mehmetpeker.recipe.util.extension

import android.content.ContentResolver
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

fun Uri.toFile(contentResolver: ContentResolver): File? {
    val fileName = "${System.currentTimeMillis()}_image.jpg"
    val tempFile = File.createTempFile("temp_", fileName)

    return try {
        contentResolver.openInputStream(this)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                copyStream(inputStream, outputStream)
            }
        }
        tempFile
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

@Throws(IOException::class)
private fun copyStream(inputStream: InputStream, outputStream: FileOutputStream) {
    val buffer = ByteArray(1024)
    var bytesRead: Int
    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
        outputStream.write(buffer, 0, bytesRead)
    }
}
