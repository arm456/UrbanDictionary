package com.nike.dictionary.ui.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.nike.dictionary.R
import com.nike.dictionary.domain.WordsListItem
import com.nike.dictionary.domain.WordsResponse
import com.nike.dictionary.network.DictionaryAPI
import com.nike.dictionary.ui.main.WordListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import com.nike.dictionary.domain.WordsListViewState


class LoadDictionaryWordListViewModel : MainViewModel() {
    @Inject
    lateinit var dictionaryAPI: DictionaryAPI
    private lateinit var subscription: Disposable
    private val wordsListViewState = MutableLiveData<WordsListViewState>()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadWordLists(searchTerm = "") }
    val wordListAdapter: WordListAdapter = WordListAdapter()

    fun getWordsListState(): MutableLiveData<WordsListViewState> {
        return wordsListViewState
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun loadWordLists(searchTerm: String) {
        subscription = dictionaryAPI
            .searchWordFromDictionary(searchTerm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveWordListStart() }
            .doOnTerminate { onRetrieveWordListFinish() }
            .subscribe(
//                { result -> onRetrieveWordListSuccess(result) },
                { result -> onRetrieveWordListSuccess(result) },
                { error -> onRetrieveWordListError(error) }
            )
    }

    fun sortWordListByThumbsUpOrDown(searchTerm: String, thumbsUpOrDown: String) {
        subscription = dictionaryAPI
            .searchWordFromDictionary(searchTerm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveWordListStart() }
            .doOnTerminate { onRetrieveWordListFinish() }
            .subscribe(
//                { result -> onSortWordListSuccess(result, thumbsUpOrDown) },
                { result -> onSortWordListSuccess(result, thumbsUpOrDown) },
                { error -> onRetrieveWordListError(error) }
            )
    }

    private fun onRetrieveWordListStart() {
        wordsListViewState.postValue(WordsListViewState.LOADING_STATE)
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveWordListFinish() {
        loadingVisibility.value = View.GONE
    }

//    private fun onRetrieveWordListSuccess(response: Response<WordsResponse>) {
    private fun onRetrieveWordListSuccess(response: WordsResponse) {
//        val wordList: List<WordsListItem?> = response.body()?.list ?: emptyList()
        val wordList: List<WordsListItem?> = response.list
        wordListAdapter.updatePostList(wordList as List<WordsListItem>)

//        WordsListViewState.SUCCESS_STATE.data = response.body()!!
        WordsListViewState.SUCCESS_STATE.data = response
        wordsListViewState.postValue(WordsListViewState.SUCCESS_STATE)
    }

//    private fun onSortWordListSuccess(response: Response<WordsResponse>, thumbsUpOrDown: String) {
    private fun onSortWordListSuccess(response:WordsResponse, thumbsUpOrDown: String) {
        val wordList: List<WordsListItem?> = response.list
        wordListAdapter.sortWordListByThumbsUpOrDown(
            wordList as List<WordsListItem>,
            thumbsUpOrDown
        )

//        WordsListViewState.SUCCESS_STATE.data = response.body()!!
        WordsListViewState.SUCCESS_STATE.data = response
        wordsListViewState.postValue(WordsListViewState.SUCCESS_STATE)
    }

    private fun onRetrieveWordListError(error: Throwable) {
        WordsListViewState.ERROR_STATE.error = error
        wordsListViewState.postValue(WordsListViewState.ERROR_STATE)
        errorMessage.value = R.string.post_error
    }
}