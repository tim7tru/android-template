package com.timmytruong.template.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.timmytruong.template.databinding.ActivityWebViewBinding
import com.timmytruong.template.ui.main.URL_KEY

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val url = intent.getStringExtra(URL_KEY)

        if (url.isNullOrEmpty()) finish()
        else binding.webview.loadUrl(url)
    }
}