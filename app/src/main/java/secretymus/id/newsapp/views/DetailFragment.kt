package secretymus.id.newsapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import secretymus.id.newsapp.R
import secretymus.id.newsapp.databinding.FragmentDetailBinding
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.news.DetailViewModel

class DetailFragment : Fragment() {
    private lateinit var mArticle: Article
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            mArticle = DetailFragmentArgs.fromBundle(it).mArticle
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java
        )
        viewModel.fetch(mArticle)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { article ->
            article?.let {
                dataBinding.article = mArticle
            }
        })
    }

}