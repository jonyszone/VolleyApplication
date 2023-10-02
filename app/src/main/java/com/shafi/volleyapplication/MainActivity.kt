package com.shafi.volleyapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shafi.volleyapplication.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val TAG: String = "posts"
    private lateinit var postViewModel: PostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        
        postViewModel.getPosts().observe(this, Observer { posts ->
            Log.d(TAG, "onCreate: Post size is ${posts.size}")
           posts.forEachIndexed() { index, post ->
               Log.d(TAG, "onCreate:  Post $index: ${post.title}")
           }
        })
    }
}