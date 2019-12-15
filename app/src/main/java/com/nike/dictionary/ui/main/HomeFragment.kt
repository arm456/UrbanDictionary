package com.nike.dictionary.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nike.dictionary.R
import com.nike.dictionary.databinding.ActivityWordsListBinding
import com.nike.dictionary.ui.viewmodel.LoadDictionaryWordListViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: ActivityWordsListBinding
    private lateinit var viewModel: LoadDictionaryWordListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_words_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.activity_words_list)
        binding.wordsList.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        var searchTerm: String="home"
        viewModel = ViewModelProviders.of(this).get(LoadDictionaryWordListViewModel::class.java)

        viewModel.loadWordLists(searchTerm)
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

}
