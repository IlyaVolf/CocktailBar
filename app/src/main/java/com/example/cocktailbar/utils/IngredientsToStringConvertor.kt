package com.example.cocktailbar.utils

class IngredientsToStringConvertor {

    companion object {
        fun toList(string: String): List<String> {
            return if (string != "") {
                string.split(SEPARATOR)
            } else {
                emptyList()
            }
        }

        fun toString(list: List<String>): String {
            return if (list.isNotEmpty()) {
                list.joinToString(SEPARATOR) {
                    it
                }
            } else {
                ""
            }
        }

        private const val SEPARATOR = ", "
    }

}