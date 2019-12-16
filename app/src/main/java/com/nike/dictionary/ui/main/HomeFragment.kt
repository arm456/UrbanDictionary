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
import com.nike.dictionary.databinding.ActivityWordsListBinding
import com.nike.dictionary.ui.viewmodel.LoadDictionaryWordListViewModel
import androidx.appcompat.widget.SearchView
import com.nike.dictionary.R


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

        viewModel = ViewModelProviders.of(this).get(LoadDictionaryWordListViewModel::class.java)
        configureSearchViewForDynamicStringSearch()
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel
    }

    private fun configureSearchViewForDynamicStringSearch(){
        binding.search.isActivated = true
        binding.search.queryHint = getString(R.string.query_hint)
        binding.search.onActionViewExpanded()
        binding.search.isIconified = false
        binding.search.clearFocus()

        binding.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(searchTerm: String): Boolean {
                viewModel.loadWordLists(searchTerm)
                return false
            }
        })
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
