package com.example.android.roomwordssample.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.android.roomwordssample.R
import kotlinx.android.synthetic.main.activity_new_word.*

class NewWordActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        button_save.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edit_word.text)) {
                setResult(RESULT_CANCELED, replyIntent)
            } else {
                val word = edit_word.text.toString()
                val details = edit_word_details.text.toString()
                replyIntent.putExtra(EXTRA_TITLE, word)
                replyIntent.putExtra(EXTRA_DETAILS, details)
                setResult(RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DETAILS = "EXTRA_DETAILS"
    }
}