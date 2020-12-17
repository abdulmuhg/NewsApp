package secretymus.id.newsapp.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiService {
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsApi::class.java)

    fun getNews(): Single<News> {
        return api.getNews(COUNTRY_IDN_VALUE, "key")
    }

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val COUNTRY_IDN_VALUE = "id"
    }
}