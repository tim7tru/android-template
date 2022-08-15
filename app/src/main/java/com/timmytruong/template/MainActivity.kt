package com.timmytruong.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.timmytruong.template.data.Result
import com.timmytruong.template.data.TopStoriesRepository
import com.timmytruong.template.data.model.Section
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            when (val result = TopStoriesRepository().getTopStories(Section.ARTS)) {
                is Result.Success<*> -> println(result.data)
                is Result.Error -> println(result.message)
            }
        }
    }
}