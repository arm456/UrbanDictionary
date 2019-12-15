package com.nike.dictionary.dagger.module

import com.nike.dictionary.network.CommonHeaderInterceptor
import com.nike.dictionary.network.DictionaryAPI
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

/**
 * Module which provides all required dependencies about network
 */
@Module
object NetworkModule {

    private const val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com"
    private const val OK_HTTP_CLIENT = "BaseOkHttp3"

    /**
     * Provides the Dictionary service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Dictionary service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideDictionaryAPI(retrofit: Retrofit): DictionaryAPI {
        return retrofit.create(DictionaryAPI::class.java)
    }

    /**
     * Provides the OkHttpClient object.
     * @return the OkHttpClient object
     */
    @Provides
    @Reusable
    @Named(OK_HTTP_CLIENT)
    internal fun provideOkHttClient(
        commonHeaderInterceptor: CommonHeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(commonHeaderInterceptor)
            .build()
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofit(
        @Named(OK_HTTP_CLIENT) okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}