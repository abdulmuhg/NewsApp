package secretymus.id.newsapp.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import secretymus.id.newsapp.models.News

class NewsApiService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsApi::class.java)

    fun getNews(page: Int): Single<News> {
        return api.getNews(COUNTRY_IDN_VALUE, "daf70d7cff96457c8c3a1ce3e4dd4a4b", page)
    }

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val COUNTRY_IDN_VALUE = "id"
    }
}