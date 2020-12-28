package secretymus.id.newsapp.utils

import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import secretymus.id.newsapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

@JvmName("loadImage_")
fun ImageView.loadImage(uri: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.image_placeholder)
        .error(R.drawable.ic_photo)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url)
}

@RequiresApi(Build.VERSION_CODES.O)
fun TextView.dateFormat(inputFormat: String?){
    if (inputFormat != null) {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(inputFormat, inputFormatter)
        val formattedDate = outputFormatter.format(date)
        this.text = formattedDate
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("android:dateFormat")
fun setDateFormat(view: TextView, inputFormat: String?){
    view.dateFormat(inputFormat)
}