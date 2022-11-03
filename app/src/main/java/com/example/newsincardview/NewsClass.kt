package com.example.newsincardview

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class NewsClass: AppCompatActivity(), NewsItemClicked {
    private lateinit var recyclerView: RecyclerView
    private  lateinit var madapter:NewsListAdapter
    var str =""
    private lateinit var Bussiness: TextView
    private lateinit var Entertainment: TextView
    private lateinit var Sport: TextView
    private lateinit var Health: TextView
    private lateinit var Secience: TextView
    private lateinit var tecnology: TextView
    private lateinit var none: TextView
    private lateinit var Category: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newsclass)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
       fetchData()
        madapter =NewsListAdapter(this)
        recyclerView.adapter =madapter


    }
    private fun fetchData() {

        val url =
            "https://newsapi.org/v2/top-headlines?country=in&apiKey=208fb60b989b4cef8fa7ff86b5c0e38e" +
                    ""
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                Log.e("TAG", "fetchData: $it")
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("publishedAt"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                madapter.updateNews(newsArray)
            },

            {
                Log.d("Error occur", "Try again..." + it.networkResponse.statusCode)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun fetchData(category:String) {

        val url =
            "https://newsapi.org/v2/top-headlines?country=in&category=${category}&apiKey=208fb60b989b4cef8fa7ff86b5c0e38e" +
                    ""
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                Log.e("TAG", "fetchData: $it")
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("publishedAt"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                madapter.updateNews(newsArray)
            },

            {
                Log.d("Error occur", "Try again..." + it.networkResponse.statusCode)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}