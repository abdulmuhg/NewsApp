package secretymus.id.newsapp.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import secretymus.id.newsapp.model.News
import java.text.AttributedString

class NewsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 1
) : ConstraintLayout(context, attrs, defStyleAttr){

    fun initView(note: News){

    }
}