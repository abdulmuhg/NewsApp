package secretymus.id.newsapp.views

import android.os.Bundle
import secretymus.id.newsapp.R
import secretymus.id.newsapp.foundation.BaseActivity

class MainActivity : BaseActivity() {

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