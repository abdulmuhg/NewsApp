package secretymus.id.newsapp.news

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.databinding.ItemNewsBinding
import secretymus.id.newsapp.databinding.ItemNewsExtendedBinding
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.views.NewsListFragmentDirections

class NewsListAdapter(
    private val articleList: ArrayList<Article>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ItemClickListener {

    val urlToImage: String? = null

    private val dummyArticle = Article(
        null,
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )

    companion object {
        const val ITEM_VIEW_TYPE_CONTENT = 1
        const val ITEM_VIEW_TYPE_LOADING = 2
        const val ITEM_VIEW_TYPE_CONTENT_EXTENDED = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            articleList.lastIndex -> {
                ITEM_VIEW_TYPE_LOADING
            }
            0 -> {
                ITEM_VIEW_TYPE_CONTENT
            }
            else -> {
                ITEM_VIEW_TYPE_CONTENT_EXTENDED
            }
        }
    }

    fun loadMore(nArticleList: List<Article>) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (articleList.size > 0) {
                articleList.apply {
                    removeAt(articleList.lastIndex)
                }
            }
            articleList.addAll(nArticleList)
            articleList.add(dummyArticle)
            notifyDataSetChanged()
        }, 2500)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_CONTENT -> {
                NewsViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_news,
                        parent,
                        false
                    )
                )
            }
            ITEM_VIEW_TYPE_CONTENT_EXTENDED -> {
                NewsExtendedViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_news_extended,
                        parent,
                        false
                    )
                )
            }
            else -> {
                ViewHolderLoading(inflater.inflate(R.layout.item_loading, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            holder.view.article = articleList[position]
            holder.view.listener = this
        } else if (holder is NewsExtendedViewHolder) {
            holder.view.article = articleList[position]
            holder.view.listener = this
        }
    }

    class NewsViewHolder(var view: ItemNewsBinding) : RecyclerView.ViewHolder(view.root)

    class NewsExtendedViewHolder(var view: ItemNewsExtendedBinding) :
        RecyclerView.ViewHolder(view.root)

    class ViewHolderLoading(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    override fun getItemCount(): Int = articleList.size

    override fun onNewsClicked(view: View) {
        val article = Article(
            source = null,
            author = view.author.text.toString(),
            title = view.titleText.text.toString(),
            description = view.description.text.toString(),
            url = view.url.text.toString(),
            urlToImage = view.urlText.text.toString(),
            publishedAt = view.publishTimeText.text.toString(),
            content = view.contentText.text.toString()
        )
        Log.d("Adapters", view.urlText.text.toString() + "")
        val action = NewsListFragmentDirections.actionDetailFragment(article)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onBookmarkClicked(view: View) {
        //No Implement
    }

}