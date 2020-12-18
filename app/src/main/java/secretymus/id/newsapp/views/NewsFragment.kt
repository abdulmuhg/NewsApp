package secretymus.id.newsapp.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.news.NewsListAdapter
import secretymus.id.newsapp.news.NewsViewModel

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel
    private val newsListAdapter = NewsListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NewsViewModel::class.java)
        viewModel.refresh()

        articleList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsListAdapter
        }

        refreshLayout.setOnRefreshListener {
            articleList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.news.observe(this, { news ->
            news.let {
                articleList.visibility = View.VISIBLE
                newsListAdapter.updateNewsList(news)
            }
        })
        viewModel.newsLoadError.observe(this, { isError ->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listError.visibility = View.GONE
                    articleList.visibility = View.GONE
                }
            }
        })
    }

}