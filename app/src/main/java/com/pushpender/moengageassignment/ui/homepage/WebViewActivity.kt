package com.pushpender.moengageassignment.ui.homepage

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pushpender.moengageassignment.R
import com.pushpender.moengageassignment.databinding.ActivityWebViewBinding
import com.pushpender.moengageassignment.makeGone
import com.pushpender.moengageassignment.makeVisible

const val KEY_NEWS_URL = "news_url"

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        setClickListener()
        setWebViewForNews()
    }

    private fun setClickListener() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebViewForNews() {
        val newsUrl = intent.getStringExtra(KEY_NEWS_URL)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBar.makeVisible()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.makeGone()
            }
        }

        binding.webView.apply {
            settings.javaScriptEnabled = true
            newsUrl?.let { loadUrl(newsUrl) }
        }
    }


}