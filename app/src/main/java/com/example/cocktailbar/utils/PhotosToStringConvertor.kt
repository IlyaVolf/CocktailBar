package com.example.cocktailbar.utils

import android.net.Uri

class PhotosToStringConvertor {

    companion object {
        fun toList(string: String): List<Uri> {
            return if (string != "") {
                string.split(SEPARATOR).map { Uri.parse(it) }
            } else {
                emptyList()
            }
        }

        fun toString(list: List<Uri>): String {
            return if (list.isNotEmpty()) {
                list.joinToString(SEPARATOR) {
                    it.toString()
                }
            } else {
                ""
            }
        }

        private const val SEPARATOR = ", "
    }

}