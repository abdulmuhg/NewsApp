package secretymus.id.newsapp.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import secretymus.id.newsapp.foundation.BaseViewModel
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.model.News
import secretymus.id.newsapp.network.NewsApiService

class NewsViewModel(application: Application) : BaseViewModel(application) {

    private val newsApiService = NewsApiService()
    private val disposable = CompositeDisposable()
    val news = MutableLiveData<List<Article>>()
    val newsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private fun getDummyArticle(): Article {
        return Article(
                null,
                "Authors",
                "Some Sample Title",
                "Sample description",
                "",
                "https://www.newsbtc.com/wp-content/uploads/2020/12/shutterstock_1414215365.jpg",
                "2020-01-10T20:45:00Z",
                "lorem ipsum content"
        )
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
                newsApiService.getNews(1)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<News>() {

                            override fun onSuccess(_news: News) {
                                news.value = _news.articles
                                newsLoadError.value = false
                                loading.value = false
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

    fun loadMore(page: Int) {
        disposable.add(
                newsApiService.getNews(page)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<News>() {
                            override fun onSuccess(_news: News) {
                                news.postValue(_news.articles)
                                newsLoadError.value = false
                                loading.value = false
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

    fun getFakeData() {
        news.postValue(
                listOf(
                        getDummyArticle(),
                        getDummyArticle(),
                        getDummyArticle(),
                        getDummyArticle(),
                        getDummyArticle(),
                        getDummyArticle()
                )
        )
        newsLoadError.value = false
        loading.value = false
    }

}