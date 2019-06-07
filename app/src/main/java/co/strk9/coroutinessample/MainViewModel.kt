package co.strk9.coroutinessample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // In a real project you would use some kind of DI
    private val repository = WordsRepository()

    val count = MutableLiveData<Int>()

    private val _words = MutableLiveData<List<String>>()

    val words: LiveData<List<String>>
        get() = _words

    private val _snackBar = MutableLiveData<String>()

    val snackBar: LiveData<String>
        get() = _snackBar

    fun onGetWordsClicked() {
        val t = count.value

        if (t == null) {
            _snackBar.value = "Cannot use an empty value"
            return
        }

        repository.getWords(t) { words, exception ->

            if (words != null && exception == null) {
                // Success
                _words.postValue(words)
            } else if (exception != null) {
                // Failure
                _snackBar.postValue(exception.message)
            }
        }
    }

    fun onSnackbarShown() {
        _snackBar.value = null
    }
}