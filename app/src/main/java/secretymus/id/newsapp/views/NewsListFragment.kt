package secretymus.id.newsapp.views

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.news.ItemListAdapter
import secretymus.id.newsapp.news.NewsViewModel

class NewsListFragment : Fragment() {

    lateinit var mlayoutManager: LinearLayoutManager
    private lateinit var viewModel: NewsViewModel
    private val newsListAdapter = ItemListAdapter(arrayListOf())

    private var listsLoaded = arrayListOf<Article>()
    var currentPage: Int = 1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
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
                    Log.d("NewsListFragment", "lastItemAt = "+ lastItemAt)
                    if (mlayoutManager.findLastCompletelyVisibleItemPosition() == lastItemAt) {
                        Log.d("NewsListFragment", "Is Last Row")
                        viewModel.loadMore(viewModel.currentPage)
                        newsListAdapter.notifyDataSetChanged()
                    }

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_bookmarked -> {
                view?.let { Navigation.findNavController(it).navigate(NewsListFragmentDirections.actionBookmarkFragment()) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}