package com.example.apprepositories

import android.app.Application
import com.example.apprepositories.data.di.DataModule
import com.example.apprepositories.domain.di.DomainModule
import com.example.apprepositories.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}