package com.shafi.volleyapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shafi.volleyapplication.data.Post
import com.shafi.volleyapplication.repository.PostRepository

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepository(application)
    private var posts: MutableLiveData<List<Post>>? = null

    fun getPosts(): LiveData<List<Post>> {
        if (posts == null) {
            posts = MutableLiveData()
            loadPosts()
        }
        return posts!!
    }

    private fun loadPosts() {
        repository.getPosts(object : PostRepository.DataCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                posts?.value = data
            }

            override fun onError(errorMessage: String?) {
                // Handle errors here
            }
        })
    }
}
