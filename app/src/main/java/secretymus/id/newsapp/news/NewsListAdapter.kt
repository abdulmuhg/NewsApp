package secretymus.id.newsapp.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.android.synthetic.main.item_news.view.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.databinding.ItemNewsBinding
import secretymus.id.newsapp.model.Article

class NewsListAdapter(
        val articleList: ArrayList<Article>): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    NewsClickListener {

    companion object {
        const val ITEM_VIEW_TYPE_CONTENT = 1
        const val ITEM_VIEW_TYPE_LOADING = 2
    }

    override fun getItemViewType(position: Int): Int = if (position == 5) {
        ITEM_VIEW_TYPE_LOADING
    } else {
        ITEM_VIEW_TYPE_CONTENT
    }

    fun updateNewsList(nArticleList: List<Article>){
        articleList.clear()
        articleList.addAll(nArticleList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType){
            ITEM_VIEW_TYPE_CONTENT -> {
                NewsViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_news, parent, false))
            }
            else -> {
                ViewHolderLoading(inflater.inflate(R.layout.item_loading, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder){
            holder.view.article = articleList[position]
            holder.view.listener = this
        }else {

        }
    }

    class NewsViewHolder(var view: ItemNewsBinding): RecyclerView.ViewHolder(view.root)

    class ViewHolderLoading(var itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    override fun getItemCount(): Int = articleList.size

    override fun onNewsClicked(view: View) {
        val articleId = view.titleText.text.toString()
    }
}