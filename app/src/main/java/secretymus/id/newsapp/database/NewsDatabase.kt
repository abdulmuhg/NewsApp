package secretymus.id.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import secretymus.id.newsapp.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase: RoomDatabase(){
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile private var instance: NewsDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "newsdatabase"

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            DB_NAME
        ).build()
    }
}