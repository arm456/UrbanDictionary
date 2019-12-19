package com.nike.dictionary.ui.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.nike.dictionary.domain.WordsListItem;
import com.nike.dictionary.domain.WordsListViewState;
import com.nike.dictionary.domain.WordsResponse;
import com.nike.dictionary.network.DictionaryAPI;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LoadDictionaryWordListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    DictionaryAPI apiClient;
    private LoadDictionaryWordListViewModel viewModel;
    @Mock
    Observer<WordsListViewState> observer;
    private List<WordsListItem> wordsListItem = new ArrayList<>();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new LoadDictionaryWordListViewModel();
        viewModel.getWordsListState().observeForever(observer);
    }

    @Test
    public void testNull() {
        when(apiClient.searchWordFromDictionary("test")).thenReturn(null);
        assertNotNull(viewModel.getWordsListState());
        assertTrue(viewModel.getWordsListState().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        // Mock API response
        when(apiClient.searchWordFromDictionary("test")).thenReturn(Observable.just(new WordsResponse(wordsListItem)));
        viewModel.loadWordLists("test");
        verify(observer).onChanged(WordsListViewState.Companion.getLOADING_STATE());
        verify(observer).onChanged(WordsListViewState.Companion.getSUCCESS_STATE());
    }

    @Test
    public void testApiFetchDataError() {
        when(apiClient.searchWordFromDictionary("test")).thenReturn(Observable.<WordsResponse>error(new Throwable("\"Api Error")));
        viewModel.loadWordLists("test");
        verify(observer).onChanged(WordsListViewState.Companion.getLOADING_STATE());
        verify(observer).onChanged(WordsListViewState.Companion.getERROR_STATE());
    }

    @After
    public void tearDown() {
        apiClient = null;
        viewModel = null;
    }
}

