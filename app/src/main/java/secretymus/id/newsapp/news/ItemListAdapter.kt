package secretymus.id.newsapp.news

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.databinding.ItemNewsBinding
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.views.NewsListFragmentDirections

class ItemListAdapter(
    private val articleList: ArrayList<Article>): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ItemClickListener {

    companion object {
        const val ITEM_VIEW_TYPE_CONTENT = 1
        const val ITEM_VIEW_TYPE_LOADING = 2
    }

    override fun getItemViewType(position: Int): Int = if (position == articleList.size-1) {
        ITEM_VIEW_TYPE_LOADING
    } else {
        ITEM_VIEW_TYPE_CONTENT
    }

    fun loadMore(nArticleList: List<Article>) {
        val handler = Handler()
        handler.postDelayed( {
            if (articleList.size > 0) {
                articleList.apply {
                    removeAt(articleList.size - 1)
                }
            }
            articleList.addAll(nArticleList)
            articleList.add(nArticleList[0].copy())
            notifyDataSetChanged()
        }, 2500)
    }

    fun retrieveBookmarkList(nArticleList: List<Article>){
        articleList.clear()
        articleList.addAll(nArticleList)
        articleList.add(nArticleList[0])
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
        }
    }

    class NewsViewHolder(var view: ItemNewsBinding): RecyclerView.ViewHolder(view.root)

    class ViewHolderLoading(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    override fun getItemCount(): Int = articleList.size

    override fun onNewsClicked(view: View) {
        val articleTitle = view.titleText.text.toString()
        val article = Article(
            view.categoryText.text.toString(),
            view.author.text.toString(),
            articleTitle,
            view.description.text.toString(),
            view.url.text.toString(),
            view.contentImage.resources.toString(),
            view.publishTimeText.text.toString(),
            view.contentText.text.toString()
        )
        val action = NewsListFragmentDirections.actionDetailFragment(article)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onBookmarkClicked(view: View) {
        //No Implement
    }

}