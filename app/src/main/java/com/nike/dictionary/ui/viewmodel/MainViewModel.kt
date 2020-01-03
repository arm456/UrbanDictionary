package com.nike.dictionary.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.nike.dictionary.dagger.component.AppComponent
import com.nike.dictionary.dagger.component.DaggerAppComponent
import com.nike.dictionary.dagger.module.NetworkModule

abstract class MainViewModel : ViewModel() {

    private val injector: AppComponent = DaggerAppComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is SearchWordInDictionaryViewModel -> injector.inject(this)
        }
    }
}
