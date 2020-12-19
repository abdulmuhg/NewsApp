package secretymus.id.newsapp.news

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import secretymus.id.newsapp.foundation.BaseViewModel
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.model.NewsDatabase

class DetailViewModel(application: Application): BaseViewModel(application) {
    val newsLiveData = MutableLiveData<Article>()
    private var actionListener: NewsActionListener? = null

    fun fetch(article: Article) {
        newsLiveData.postValue(article)
    }

    fun saveNews(article: Article) {
        launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            dao.insert(article)
            actionListener?.onSavingNews()
        }
    }
}