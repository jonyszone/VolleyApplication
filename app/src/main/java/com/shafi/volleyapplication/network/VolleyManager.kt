package com.shafi.volleyapplication.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class VolleyManager private constructor(context: Context) {
    val requestQueue: RequestQueue
    private val sslSocketFactory: SSLSocketFactory

    companion object {
        private var instance: VolleyManager? = null

        fun getInstance(context: Context): VolleyManager {
            return instance ?: synchronized(this) {
                instance ?: VolleyManager(context).also {
                    instance = it
                }
            }
        }
    }

    init {
        requestQueue = Volley.newRequestQueue(context.applicationContext, HurlStack(null, getSSLSocketFactory()))
        sslSocketFactory = getSSLSocketFactory()
    }

    private fun getSSLSocketFactory(): SSLSocketFactory {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            return sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }
}
