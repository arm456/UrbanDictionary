package com.nike.dictionary.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nike.dictionary.R
import com.nike.dictionary.databinding.ItemWordBinding
import com.nike.dictionary.domain.WordsListItem
import com.nike.dictionary.ui.viewmodel.DictionaryWordViewModel

class WordListAdapter : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {
    private lateinit var wordsList:List<WordsListItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWordBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_word, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wordsList[position])
    }

    override fun getItemCount(): Int {
        return if(::wordsList.isInitialized) wordsList.size else 0
    }

    fun updatePostList(postList:List<WordsListItem>){
        this.wordsList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemWordBinding ):RecyclerView.ViewHolder(binding.root){
        private val viewModel = DictionaryWordViewModel()

        fun bind(wordItem:WordsListItem){
            viewModel.bind(wordItem)
            binding.viewModel = viewModel
        }
    }
}