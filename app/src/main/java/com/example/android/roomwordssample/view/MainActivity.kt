package com.example.android.roomwordssample.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordssample.R
import com.example.android.roomwordssample.adapter.WordListAdapter
import com.example.android.roomwordssample.viewModel.WordViewModel
import com.example.android.roomwordssample.model.User
import com.example.android.roomwordssample.model.Word
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mWordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = WordListAdapter(this)
        recyclerview.adapter = adapter

        // Get a new or existing ViewModel from the ViewModelProvider.

        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.allWords.observe(this, { words: List<Word> ->
            adapter.setWords(words)
        })
        
        mWordViewModel.allUsers.observe(this, { users: List<User> ->
            Log.d("users", "onCreate: $users")
        })

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val word = Word(data!!.getStringExtra(NewWordActivity.EXTRA_TITLE).toString(),
                    data.getStringExtra(NewWordActivity.EXTRA_DETAILS).toString())
            mWordViewModel.insert(word)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
    }
}