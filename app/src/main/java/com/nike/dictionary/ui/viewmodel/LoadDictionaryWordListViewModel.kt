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

class LoadDictionaryWordListViewModel : MainViewModel() {

    @Inject
    lateinit var dictionaryAPI: DictionaryAPI
    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadWordLists(searchTerm = "") }
    val wordListAdapter: WordListAdapter = WordListAdapter()


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
                { result -> onRetrieveWordListSuccess(result) },
                { onRetrieveWordListError() }
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
                { result -> onSortWordListSuccess(result, thumbsUpOrDown) },
                { onRetrieveWordListError() }
            )
    }

    private fun onRetrieveWordListStart() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveWordListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveWordListSuccess(response: Response<WordsResponse>) {
        val wordList: List<WordsListItem?> = response.body()?.list ?: emptyList()
        wordListAdapter.updatePostList(wordList as List<WordsListItem>)
    }

    private fun onSortWordListSuccess(response: Response<WordsResponse>, thumbsUpOrDown: String) {
        val wordList: List<WordsListItem?> = response.body()?.list ?: emptyList()
        wordListAdapter.sortWordListByThumbsUpOrDown(
            wordList as List<WordsListItem>,
            thumbsUpOrDown
        )
    }

    private fun onRetrieveWordListError() {
        errorMessage.value = R.string.post_error
    }
}