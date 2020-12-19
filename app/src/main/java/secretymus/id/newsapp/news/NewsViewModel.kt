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
import secretymus.id.newsapp.model.Source

class NewsViewModel : ViewModel() {

    private val newsApiService = NewsApiService()
    private val disposable = CompositeDisposable()

    var currentPage: Int = 1
    val news = MutableLiveData<List<Article>>()
    val newsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote(currentPage)
    }

    fun refreshBypassCache() {
        fetchFromRemote(currentPage)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun fetchFromRemote(page: Int) {
        loading.value = true
        disposable.add(
            newsApiService.getNews(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<News>() {
                    override fun onSuccess(_news: News) {
                        news.postValue(_news.articles)
                        newsLoadError.value = false
                        loading.value = false
                        currentPage++
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

    fun getFakeData(){
        val dummyArticle = Article(Source("", "Id"),
                "Authors",
                "Some Sample Title",
                "Sample description",
                "",
                "",
                "10 January 2020",
                "lorem ipsum content")
        news.postValue(
                listOf(
                        dummyArticle.copy(),
                        dummyArticle.copy(),
                        dummyArticle.copy(),
                        dummyArticle.copy(),
                        dummyArticle.copy(),
                        dummyArticle.copy(),
                        dummyArticle.copy(),
                        )
        )
        newsLoadError.value = false
        loading.value = false
        currentPage++
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
                                currentPage++
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