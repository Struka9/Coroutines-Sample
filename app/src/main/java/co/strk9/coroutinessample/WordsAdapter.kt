package co.strk9.coroutinessample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.word_item.*

class WordsAdapter(context: Context) : RecyclerView.Adapter<WordsAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private val words = mutableListOf<String>()

    fun setWords(words: List<String>) {
        this.words.clear()
        this.words.addAll(words)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.word_item, parent, false))
    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(words[position])
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(word: String) {
            tv_word.text = word
        }
    }
}