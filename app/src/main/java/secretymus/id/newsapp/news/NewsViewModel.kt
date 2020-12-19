package secretymus.id.newsapp.news

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import secretymus.id.newsapp.foundation.BaseViewModel
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.model.News
import secretymus.id.newsapp.model.NewsApiService
import secretymus.id.newsapp.model.NewsDatabase

class NewsViewModel(application: Application): BaseViewModel(application) {

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

    private fun storeNewsLocally(list: List<Article>) {
        launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            dao.deleteAllNews()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            newsRetrieved(list)
        }
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
        val dummyArticle = Article(
            "unknown source",
                "Authors",
                "Some Sample Title",
                "Sample description",
                "",
                "",
                "10 January 2020",
                "lorem ipsum content")
        val dummyArticle_ = Article(
            "unknown source",
            "Authors 2",
            "Some Sample Title 2",
            "Sample description 2",
            "",
            "",
            "11 January 2020",
            "lorem ipsum content content")
        news.postValue(
                listOf(
                        dummyArticle_,
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