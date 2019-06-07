package co.strk9.coroutinessample

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var wordsAdapter: WordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_words.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        wordsAdapter = WordsAdapter(this)
        rv_words.adapter = wordsAdapter
        rv_words.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        et_word_count.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    val t = s.toString().toInt()
                    mainViewModel.count.value = t
                } catch (e: NumberFormatException) {
                    Log.e("MainActivity", e.message)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // DO NOTHING
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // DO NOTHING
            }
        })

        mainViewModel.snackBar.observe(this, Observer {
            if (it != null) {
                Snackbar.make(rv_words, it, Snackbar.LENGTH_LONG).show()
                mainViewModel.onSnackbarShown()
            }
        })

        mainViewModel.words.observe(this, Observer { words ->
            if (words.isNullOrEmpty()) {
                wordsAdapter.setWords(emptyList())
            } else {
                wordsAdapter.setWords(words)
            }
        })

        bt_get_words.setOnClickListener {
            mainViewModel.onGetWordsClicked()
        }
    }
}
