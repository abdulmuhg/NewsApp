package secretymus.id.newsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import secretymus.id.newsapp.model.Article

@Dao
interface NewsDao {
    @Insert
    suspend fun insert(article: Article)

    @Query("SELECT * FROM article")
    suspend fun getAllArticle(): List<Article>

    @Query("SELECT * FROM article WHERE uuid = :newsId")
    suspend fun getNews(newsId: Int): Article

    @Query("DELETE FROM article")
    suspend fun deleteAllNews()
}