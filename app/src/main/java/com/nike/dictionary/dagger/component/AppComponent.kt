package com.nike.dictionary.dagger.component

import com.nike.dictionary.dagger.module.NetworkModule
import com.nike.dictionary.ui.viewmodel.SearchWordInDictionaryViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface AppComponent {
    /**
     * Injects required dependencies into the specified SearchWordInDictionaryViewModel.
     * @param searchWordInDictionaryViewModel SearchWordInDictionaryViewModel in which to inject the dependencies
     */
    fun inject(searchWordInDictionaryViewModel: SearchWordInDictionaryViewModel)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
        fun networkModule(networkModule: NetworkModule): Builder
    }

}