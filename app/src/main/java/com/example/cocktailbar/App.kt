package com.example.cocktailbar

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.cocktailbar.utils.image_loader.GlideLoaderCreator
import com.example.cocktailbar.utils.image_loader.ImageLoader
import dagger.hilt.android.HiltAndroidApp

/**
 * Entry point of the app should be annotated with [HiltAndroidApp].
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ImageLoader.loaderCreator = GlideLoaderCreator()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}