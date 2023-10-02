package com.shafi.volleyapplication.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.shafi.volleyapplication.data.Post
import com.shafi.volleyapplication.network.VolleyManager
import org.json.JSONArray
import org.json.JSONException

class PostRepository(context: Context) {
    private val BASE_URL = "https://jsonplaceholder.typicode.com"
    private val requestQueue: RequestQueue = VolleyManager.getInstance(context).requestQueue

    fun getPosts(callback: DataCallback<List<Post>>) {
        val url = "$BASE_URL/posts"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val posts = mutableListOf<Post>()
                try {
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val post = Post(
                            jsonObject.getInt("userId"),
                            jsonObject.getInt("id"),
                            jsonObject.getString("title"),
                            jsonObject.getString("body")
                        )
                        posts.add(post)
                    }
                    callback.onSuccess(posts)
                } catch (e: JSONException) {
                    callback.onError(e.message)
                }
            },
            { error ->
                callback.onError(error.message)
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    interface DataCallback<T> {
        fun onSuccess(data: T)
        fun onError(errorMessage: String?)
    }
}
