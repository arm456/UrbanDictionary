package com.nike.dictionary.domain


class WordsListViewState private constructor(
    data: WordsResponse?,
    currentState: Int,
    error: Throwable?
) : BaseViewState<WordsResponse>() {

    init {
        this.data = data
        this.error = error
        this.currentState = currentState
    }

    companion object {

        @JvmStatic
        var ERROR_STATE =
            WordsListViewState(null, State.FAILED.value, Throwable())
        @JvmStatic
        var LOADING_STATE =
            WordsListViewState(null, State.LOADING.value, null)
        @JvmStatic
        var SUCCESS_STATE =
            WordsListViewState(WordsResponse(emptyList()), State.SUCCESS.value, null)
    }
}