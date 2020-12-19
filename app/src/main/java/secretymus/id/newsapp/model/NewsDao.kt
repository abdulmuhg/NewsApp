package secretymus.id.newsapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert
    suspend fun insertAll(vararg article: Article) : List<Long>

    @Query("SELECT * FROM article")
    suspend fun getAllArticle(): List<Article>

    @Query("SELECT * FROM article WHERE uuid = :newsId")
    suspend fun getNews(newsId: Int): Article

    @Query("DELETE FROM article")
    suspend fun deleteAllNews()
}