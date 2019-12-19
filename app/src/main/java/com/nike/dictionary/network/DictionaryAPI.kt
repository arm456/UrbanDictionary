package com.nike.dictionary.network

import com.nike.dictionary.domain.WordsResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryAPI {

    @GET("/define")
    fun searchWordFromDictionary(
        @Query("term") searchTerm: String
    ): Observable<WordsResponse>

}