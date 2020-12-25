package secretymus.id.newsapp.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import secretymus.id.newsapp.R


class SplashActivity : AppCompatActivity() {
    private val loadingTime = 3000

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }, loadingTime.toLong())

//        val video = Uri.parse("android.resource://secretymus.id.newsapp/raw/fortnightly_logo.mp4")
//        videoView.setVideoURI(video)
//        videoView.requestFocus()
//
//        videoView.setOnCompletionListener(OnCompletionListener { startNextActivity() })
//
//        videoView.start()
    }

    private fun startNextActivity() {
        if (isFinishing) return
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}