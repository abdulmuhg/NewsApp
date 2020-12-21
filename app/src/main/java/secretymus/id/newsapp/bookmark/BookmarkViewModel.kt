package secretymus.id.newsapp.bookmark

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import secretymus.id.newsapp.database.NewsDatabase
import secretymus.id.newsapp.foundation.BaseViewModel
import secretymus.id.newsapp.models.Article

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
            Toast.makeText(getApplication(), "News retrieved from internal storage", Toast.LENGTH_SHORT).show()
        }
    }
    private fun newsRetrieved(list: List<Article>) {
        news.postValue(list)
        newsLoadError.value = false
        loading.value = false
    }
}