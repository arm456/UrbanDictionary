package com.nike.dictionary.dagger.component

import com.nike.dictionary.dagger.module.NetworkModule
import com.nike.dictionary.ui.viewmodel.LoadDictionaryWordListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface AppComponent {
    /**
     * Injects required dependencies into the specified LoadDictionaryWordListViewModel.
     * @param loadDictionaryWordListViewModel LoadDictionaryWordListViewModel in which to inject the dependencies
     */
    fun inject(loadDictionaryWordListViewModel: LoadDictionaryWordListViewModel)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
        fun networkModule(networkModule: NetworkModule): Builder
    }

}