package com.example.ucbpos.utils



import android.content.Context
import java.io.IOException

object JsonUtils {

    fun loadJSONFromAsset(context: Context, fileName: String): String? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charsets.UTF_8)

        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}