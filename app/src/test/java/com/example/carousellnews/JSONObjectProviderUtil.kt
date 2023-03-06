package com.example.carousellnews

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class JSONObjectProviderUtil {
    companion object {
        fun getJsonString(fileName: String?): String {
            try {
                return String(Files.readAllBytes(Paths.get(fileName)))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}
