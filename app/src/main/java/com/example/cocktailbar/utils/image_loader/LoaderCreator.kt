package com.example.cocktailbar.utils.image_loader

import android.content.Context
import com.example.cocktailbar.utils.image_loader.ImageLoader

/**
 * Создание экземпляра загрузчика
 */
interface LoaderCreator {
    fun getInstance(context: Context): ImageLoader
}