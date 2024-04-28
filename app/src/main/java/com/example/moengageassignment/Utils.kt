package com.example.moengageassignment

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * This function will load the images and if any image is unable to load or any error occurred during
 * downloading image it will show a default image to the user
 **/
@BindingAdapter("loadImageIntoView")
fun loadImageIntoView(imageView : ImageView,url : String?) {
    url?.let {
        Glide.with(imageView.context).load(url).placeholder(R.drawable.news_default_image).into(imageView)
    }
}

@BindingAdapter("setText")
fun setText(textView: TextView,text : String?) {
    textView.text = text ?: "N/A"
}


@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("parseDateAndTime")
fun parseDateAndTime(textView: TextView,time : String?) {
    time?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTime = ZonedDateTime.parse(time)
            val formatter2: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm a") // if you want to convert it any other format
            textView.text = dateTime.format(formatter2)
        }
    } ?: "N/A"
}