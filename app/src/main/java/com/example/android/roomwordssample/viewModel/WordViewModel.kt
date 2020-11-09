package com.example.android.roomwordssample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomwordssample.dao.AppRoomDatabase
import com.example.android.roomwordssample.model.User
import com.example.android.roomwordssample.model.Word
import com.example.android.roomwordssample.repository.DaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */
class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: DaoRepository

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>>
    val allUsers: LiveData<List<User>>

    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        mRepository.insert(word)
    }

    fun insert(user: User) = viewModelScope.launch(Dispatchers.IO) {
        mRepository.insertNewUser(user)
    }

    init {
        val wordsDao = AppRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        mRepository = DaoRepository(wordsDao)
        allWords = mRepository.allWords
        allUsers = mRepository.allUsers
    }


}