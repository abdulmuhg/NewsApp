package secretymus.id.newsapp.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.model.News
import secretymus.id.newsapp.model.NewsApiService

class NewsViewModel : ViewModel() {
    private val newsApiService = NewsApiService()
    private val disposable = CompositeDisposable()

    val news = MutableLiveData<List<Article>>()
    val newsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            newsApiService.getNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<News>() {
                    override fun onSuccess(_news: News) {
                        news.postValue(_news.articles)
                        newsLoadError.value = false
                        loading.value = false
//                            for (item in _news.articles){
//                                news.value = item
//                                newsLoadError.value = false
//                                loading.value = false
//                                //storeNewsLocally(articleList)
//                            }


                    }

                    override fun onError(e: Throwable) {
                        newsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                        Log.e("API", e.message.toString())
                    }
                })
        )
    }
}