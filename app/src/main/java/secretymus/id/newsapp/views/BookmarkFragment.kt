package secretymus.id.newsapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bookmark.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.bookmark.BookmarkAdapter
import secretymus.id.newsapp.bookmark.BookmarkViewModel

class BookmarkFragment : Fragment() {

    private lateinit var viewModel: BookmarkViewModel
    private val newsListAdapter = BookmarkAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookmarkViewModel::class.java)
        articleList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsListAdapter
        }
        viewModel.fetchFromDatabase()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.news.observe(viewLifecycleOwner, { news ->
            news.let {
                articleList.visibility = View.VISIBLE
                newsListAdapter.retrieveBookmarkList(it)
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

}