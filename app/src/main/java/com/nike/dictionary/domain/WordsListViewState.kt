package com.nike.dictionary.domain


class WordsListViewState private constructor(data: WordsResponse?, currentState: Int, error: Throwable?) :
    BaseViewState<WordsResponse>() {
    init {
        if (data != null) {
            this.data = data
        }
        if (error != null) {
            this.error = error
        }
        this.currentState = currentState
    }

    companion object {
        var ERROR_STATE = WordsListViewState(null, State.FAILED.value, Throwable())
        var LOADING_STATE = WordsListViewState(null, State.LOADING.value, null)
        var SUCCESS_STATE = WordsListViewState(WordsResponse(emptyList()), State.SUCCESS.value, null)
    }
}