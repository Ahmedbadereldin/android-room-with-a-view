package com.example.android.roomwordssample

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordssample.dao.AppDao
import com.example.android.roomwordssample.dao.AppRoomDatabase
import com.example.android.roomwordssample.model.User
import com.example.android.roomwordssample.model.Word
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */
@RunWith(AndroidJUnit4::class)
class AppDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private var mAppDao: AppDao? = null
    private var mDb: AppRoomDatabase? = null

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDb = Room.inMemoryDatabaseBuilder(context, AppRoomDatabase::class.java) // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        mAppDao = mDb!!.wordDao()
    }

    @After
    fun closeDb() {
        mDb!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() = runBlocking {
        val word = Word("word", "bbb")
        mAppDao!!.insert(word)
        val allWords = LiveDataTestUtil.getValue(mAppDao!!.alphabetizedWords)
        Assert.assertEquals(allWords[0].word, word.word)
    }

    @Throws(Exception::class)
    @Test
    fun allWords() = runBlocking {
        val word = Word("aaa", "bbb")
        mAppDao!!.insert(word)
        val word2 = Word("bbb", "ccc")
        mAppDao!!.insert(word2)
        val allWords = LiveDataTestUtil.getValue(mAppDao!!.alphabetizedWords)
        Assert.assertEquals(allWords[0].word, word.word)
        Assert.assertEquals(allWords[1].word, word2.word)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val word = Word("word", "bbb")
        mAppDao!!.insert(word)
        val word2 = Word("word2", "bbb")
        mAppDao!!.insert(word2)
        mAppDao!!.deleteAll()
        val allWords = LiveDataTestUtil.getValue(mAppDao!!.alphabetizedWords)
        Assert.assertTrue(allWords.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetUsers() = runBlocking {
        val user = User(1, "a@a.a", "ahmed")
        mAppDao!!.insertNewUser(user)
        val listUsers = LiveDataTestUtil.getValue(mAppDao!!.getAllUsers())
        Assert.assertEquals(listUsers[0].id, user.id)
    }

    @Throws(Exception::class)
    @Test
    fun allUsers() = runBlocking {
        val user1 = User(1, "a@a.a", "ahmed")
        mAppDao!!.insertNewUser(user1)
        val user2 = User(2, "b@b.b", "ahmed")
        mAppDao!!.insertNewUser(user2)
        val userList = LiveDataTestUtil.getValue(mAppDao!!.getAllUsers())
        Assert.assertEquals(userList[0].id, user1.id)
        Assert.assertEquals(userList[1].id, user2.id)
    }

    @Test
    @Throws(Exception::class)
    fun deleteUserById() = runBlocking {
        val user1 = User(1, "a@a.a", "ahmed")
        mAppDao!!.insertNewUser(user1)
        val user2 = User(2, "b@b.b", "ahmed")
        mAppDao!!.insertNewUser(user2)
        val user3 = User(3, "c@c.c", "ahmed")
        mAppDao!!.insertNewUser(user3)
        val userList = LiveDataTestUtil.getValue(mAppDao!!.getAllUsers())
        Assert.assertEquals(userList.size, 3)
        mAppDao!!.deleteUser(user3.id!!)
        val userList1 = LiveDataTestUtil.getValue(mAppDao!!.getAllUsers())
        Assert.assertEquals(userList1.size, 2)
    }
}