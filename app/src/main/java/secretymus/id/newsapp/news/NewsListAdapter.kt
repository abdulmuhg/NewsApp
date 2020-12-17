package secretymus.id.newsapp.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import secretymus.id.newsapp.R
import secretymus.id.newsapp.databinding.ItemNewsBinding
import secretymus.id.newsapp.model.Article

class NewsListAdapter(
        val articleList: ArrayList<Article>): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>(),
    NewsClickListener {

    fun updateNewsList(nArticleList: List<Article>){
        articleList.clear()
        articleList.addAll(nArticleList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemNewsBinding>(inflater, R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.view.article = articleList[position]
        holder.view.listener = this
    }

    class NewsViewHolder(var view: ItemNewsBinding): RecyclerView.ViewHolder(view.root)

    override fun getItemCount(): Int = articleList.size

    override fun onNewsClicked(view: View) {
//        val uuid = view.dogId.text.toString().toInt()
//        val action = ListFragmentDirections.actionDetailFragment()
//        action.dogUuid = uuid
//        Navigation.findNavController(v).navigate(action)
    }
}