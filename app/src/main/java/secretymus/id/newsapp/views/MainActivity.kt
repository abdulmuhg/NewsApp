package secretymus.id.newsapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import secretymus.id.newsapp.R
import secretymus.id.newsapp.news.NewsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NewsFragment.newInstance())
                    .commitNow()
        }
    }
}