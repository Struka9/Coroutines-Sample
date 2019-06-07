package co.strk9.coroutinessample

import androidx.annotation.MainThread
import org.json.JSONArray
import java.io.IOException
import java.net.URL
import kotlin.concurrent.thread

class WordsRepository {

    @MainThread
    fun getWords(count: Int, callback: (List<String>?, WordException?) -> Unit) {
        thread {
            try {
                // TODO: Always get a new API key from
                // https://random-word-api.herokuapp.com
                val jsonStr =
                    URL("https://random-word-api.herokuapp.com/word?key=<!--API KEY-->&number=$count").readText()
                val wordsArray = JSONArray(jsonStr)

                callback(toList(wordsArray), null)
            } catch (e: IOException) {
                callback(null, WordException(e.message))
            }
        }
    }

    private fun toList(jsonArray: JSONArray): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }

    class WordException(message: String?) : Exception(message)
}