package secretymus.id.newsapp.bookmark

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import secretymus.id.newsapp.database.NewsDatabase
import secretymus.id.newsapp.foundation.BaseViewModel
import secretymus.id.newsapp.model.Article

class BookmarkViewModel (application: Application): BaseViewModel(application) {
    private val disposable = CompositeDisposable()
    val news = MutableLiveData<List<Article>>()
    val newsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
    fun fetchFromDatabase() {
        loading.value = true
        launch {
            val news = NewsDatabase(getApplication()).newsDao().getAllArticle()
            newsRetrieved(news)
        }
    }
    private fun newsRetrieved(list: List<Article>) {
        news.postValue(list)
        newsLoadError.value = false
        loading.value = false
    }
}