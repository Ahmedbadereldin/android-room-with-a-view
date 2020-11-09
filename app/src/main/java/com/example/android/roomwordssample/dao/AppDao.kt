package com.example.android.roomwordssample.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.roomwordssample.model.User
import com.example.android.roomwordssample.model.Word

/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */
@Dao
interface AppDao {
    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
//    @Query("SELECT * from word_table ORDER BY word ASC")
//    fun alphabetizedWords(): LiveData<List<Word>>?

    @get:Query("SELECT * from word_table ORDER BY word ASC")
    val alphabetizedWords: LiveData<List<Word>>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word?)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Query("SELECT * from user_table order by name ASC")
    fun getAllUsers(): LiveData<List<User>>?

    @Insert
    suspend fun insertNewUser(user: User)

    @Query("Delete from user_table")
    suspend fun deleteAllUsers()

    @Query("Delete from user_table where id = :user_id")
    suspend fun deleteUser(user_id: Int)

    @Delete
    suspend fun removeUser(vararg user: User)

}