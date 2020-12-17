package secretymus.id.newsapp.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import secretymus.id.newsapp.R
import secretymus.id.newsapp.foundation.BaseRecyclerAdapter
import secretymus.id.newsapp.model.News

class NewsAdapter(
        newsList: MutableList<News> = mutableListOf()
): BaseRecyclerAdapter<News>(newsList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            NewsViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
            )

    class NewsViewHolder(view: View): BaseViewHolder<News>(view) {
        override fun onBind(data: News, listIndex: Int) {

        }
    }
}