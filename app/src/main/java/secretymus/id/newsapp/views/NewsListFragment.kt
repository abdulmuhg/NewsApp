package secretymus.id.newsapp.views

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.news.NewsListAdapter
import secretymus.id.newsapp.news.NewsViewModel

class NewsListFragment : Fragment() {

    lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var viewModel: NewsViewModel
    private val newsListAdapter = NewsListAdapter(arrayListOf())
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

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.refreshBypassCache()
        //viewModel.getFakeData()
        articleList.apply {
            mLayoutManager = LinearLayoutManager(context)
            layoutManager = mLayoutManager
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
        addScrollerListener()


    }


    private fun observeViewModel() {
        viewModel.news.observe(viewLifecycleOwner, { news ->
            news.let {
                articleList.visibility = View.VISIBLE
                if (!it.isNullOrEmpty()){
                    Log.d("NewsFragment", "Cant get more from API")
                    newsListAdapter.loadMore(it)
                }
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

    private fun addScrollerListener() {
        articleList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                    val lastItemAt = viewModel.news.value!!.lastIndex
                    Log.d("NewsListFragment", "lastItemAt = $lastItemAt")
                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() == lastItemAt) {
                        Log.d("NewsListFragment", "Is Last Row")
                        currentPage++
                        viewModel.loadMore(currentPage)
                        newsListAdapter.notifyDataSetChanged()
                    }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_bookmarked -> {
                view?.let {
                    Navigation.findNavController(it).navigate(NewsListFragmentDirections.actionBookmarkFragment()) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}