package secretymus.id.newsapp.views

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import secretymus.id.newsapp.R
import secretymus.id.newsapp.databinding.FragmentDetailBinding
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.news.DetailViewModel
import secretymus.id.newsapp.news.NewsActionListener
import secretymus.id.newsapp.utils.toast

class DetailFragment : Fragment(), NewsActionListener {
    private lateinit var mArticle: Article
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            mArticle = DetailFragmentArgs.fromBundle(it).mArticle
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(mArticle)

        observeViewModel()

    }
    
    private fun observeViewModel() {
        viewModel.newsLiveData.observe(viewLifecycleOwner, { article ->
            article?.let {
                dataBinding.article = mArticle
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveNews(mArticle)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSavingNews() {
        context?.toast("News has been saved")
    }


}