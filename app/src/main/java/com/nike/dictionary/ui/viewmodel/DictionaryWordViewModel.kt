package com.nike.dictionary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nike.dictionary.domain.WordsListItem

class DictionaryWordViewModel : MainViewModel() {

    private val wordTitle = MutableLiveData<String>()
    private val wordDef = MutableLiveData<String>()

    fun bind(word: WordsListItem){
        wordTitle.value = word.word
        wordDef.value = word.definition
    }

    fun getWordTitle():MutableLiveData<String>{
        return wordTitle
    }

    fun getWordDefinition():MutableLiveData<String>{
        return wordDef
    }
}