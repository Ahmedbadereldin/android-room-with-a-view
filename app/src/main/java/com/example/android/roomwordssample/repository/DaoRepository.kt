package com.example.android.roomwordssample.repository

import androidx.lifecycle.LiveData
import com.example.android.roomwordssample.dao.AppDao
import com.example.android.roomwordssample.model.User
import com.example.android.roomwordssample.model.Word

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class DaoRepository(private val dao: AppDao) {


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Word>>
    val allUsers: LiveData<List<User>>

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    suspend fun insert(word: Word) {
        dao.insert(word)
    }

    suspend fun insertNewUser(user: User) {
        dao.insertNewUser(user)
    }

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    init {
        allWords = dao.alphabetizedWords!!
        allUsers = dao.getAllUsers()!!
    }
}