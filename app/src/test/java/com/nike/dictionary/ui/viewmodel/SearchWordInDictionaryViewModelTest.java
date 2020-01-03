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
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SearchWordInDictionaryViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    DictionaryAPI apiClient;
    @Mock
    Observer<WordsListViewState> observer;

    private List<WordsListItem> wordsListItem ;
    private SearchWordInDictionaryViewModel viewModel;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        wordsListItem = new ArrayList<>();
        viewModel = new SearchWordInDictionaryViewModel();
        viewModel.getWordsListState().observeForever(observer);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> scheduler) {
                return Schedulers.trampoline();
            }
        });
    }

    @Test
    public void testNull() {
        when(apiClient.searchWordFromDictionary("test")).thenReturn(null);
        assertNotNull(viewModel.getWordsListState());
        assertTrue(viewModel.getWordsListState().hasObservers());
    }

    @Test
    public void testFetchDataSuccess() {
        // Mock API response
        when(apiClient.searchWordFromDictionary("test")).thenReturn(Observable.just(new WordsResponse(wordsListItem)));
        viewModel.loadWordLists("test");
        verify(observer).onChanged(WordsListViewState.getLOADING_STATE());
        verify(observer).onChanged(WordsListViewState.getSUCCESS_STATE());

    }

    @Test
    public void testFetchDataError() {
        when(apiClient.searchWordFromDictionary("test")).thenReturn(Observable.<WordsResponse>error(new Throwable("\"Api Error")));
        viewModel.loadWordLists("test");
        verify(observer).onChanged(WordsListViewState.getLOADING_STATE());
        verify(observer).onChanged(WordsListViewState.getERROR_STATE());
    }

    @After
    public void tearDown() {
        apiClient = null;
        viewModel = null;
    }
}

