package com.nike.dictionary.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nike.dictionary.R
import com.nike.dictionary.databinding.ItemWordBinding
import com.nike.dictionary.domain.WordsListItem
import com.nike.dictionary.ui.viewmodel.DictionaryWordViewModel
import java.util.*
import kotlin.Comparator


class WordListAdapter : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {
    private lateinit var wordsList: List<WordsListItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWordBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_word,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wordsList[position])
    }

    override fun getItemCount(): Int {
        return if (::wordsList.isInitialized) wordsList.size else 0
    }

    fun updatePostList(wordsList: List<WordsListItem>) {
        this.wordsList = wordsList
        notifyDataSetChanged()
    }

    fun sortWordListByThumbsUpOrDown(wordsList: List<WordsListItem>, thumbsUpOrDown: String) {
        this.wordsList = wordsList
        when (thumbsUpOrDown) {
            R.string.thumbs_up.toString() -> Collections.sort(
                this.wordsList,
                thumbsUpOrderComparator
            )
            R.string.thumbs_down.toString() -> Collections.sort(
                this.wordsList,
                thumbsDownOrderComparator
            )
        }
        notifyDataSetChanged()
    }

    private var thumbsUpOrderComparator: Comparator<WordsListItem> =
        Comparator { wordsListItem1, wordsListItem2 -> if (wordsListItem1.thumbs_up!! > wordsListItem2.thumbs_up!!) -1 else if (wordsListItem1.thumbs_up === wordsListItem2.thumbs_up) 0 else 1 }

    private var thumbsDownOrderComparator: Comparator<WordsListItem> =
        Comparator { wordsListItem1, wordsListItem2 -> if (wordsListItem1.thumbs_down!! > wordsListItem2.thumbs_down!!) -1 else if (wordsListItem1.thumbs_down === wordsListItem2.thumbs_down) 0 else 1 }

    class ViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = DictionaryWordViewModel()
        fun bind(wordItem: WordsListItem) {
            viewModel.bind(wordItem)
            binding.viewModel = viewModel
        }
    }
}