package secretymus.id.newsapp.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import secretymus.id.newsapp.Keys
import secretymus.id.newsapp.model.News

class NewsApiService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsApi::class.java)

    fun getNews(page: Int): Single<News> {
        return api.getNews(COUNTRY, Keys.apiKey(), page)
    }

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val COUNTRY = "us"
    }
}