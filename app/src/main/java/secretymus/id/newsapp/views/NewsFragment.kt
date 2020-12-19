package secretymus.id.newsapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.news.ItemListAdapter
import secretymus.id.newsapp.news.NewsViewModel

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }
    lateinit var mlayoutManager: LinearLayoutManager
    private lateinit var viewModel: NewsViewModel
    private val newsListAdapter = ItemListAdapter(arrayListOf())
    private var listsLoaded = arrayListOf<Article>()

    var currentPage: Int = 1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        viewModel.getFakeData()

        articleList.apply {
            mlayoutManager = LinearLayoutManager(context)
            layoutManager = mlayoutManager
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

    private fun observeViewModel() {
        viewModel.news.observe(viewLifecycleOwner, { news ->
            news.let {
                articleList.visibility = View.VISIBLE
                newsListAdapter.loadMore(it)
                //addScrollerListener(it)
            }
        })
        viewModel.newsLoadError.observe(viewLifecycleOwner, { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    articleList.visibility = View.GONE
                }
            }
        })
    }

    private fun addScrollerListener(list: List<Article>) {
        //attaches scrollListener with RecyclerView
        articleList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                    //findLastCompletelyVisibleItemPosition() returns position of last fully visible view.
                    ////It checks, fully visible view is the last one.
                    val lastItemAt = viewModel.news.value?.lastIndex
                    Log.d("NewsFragment", "lastItemAt = "+ lastItemAt)
                    if (mlayoutManager.findLastCompletelyVisibleItemPosition() == lastItemAt) {
                        Log.d("NewsFragment", "Is Last Row")
                        viewModel.loadMore(viewModel.currentPage)
                        newsListAdapter.notifyDataSetChanged()
                    }

            }
        })
    }

}