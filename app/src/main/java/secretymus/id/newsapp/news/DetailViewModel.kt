package secretymus.id.newsapp.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import secretymus.id.newsapp.model.Article

class DetailViewModel: ViewModel() {
    val newsLiveData = MutableLiveData<Article>()

    fun fetch(article: Article) {
        newsLiveData.postValue(article)
    }
}