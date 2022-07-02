package lv.stauvere.lita.myjsonarrayparser

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.textView)

        // url to get json array
        val url = "https://api.colnect.net/en/api/" + BuildConfig.COLNECT_KEY + "/countries/cat/stamps/year/2021/"

        // get json array from url using volley network library
            progressBar.visibility = View.VISIBLE

            // request json array response from the provided url
            val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
                    try {
                        textView.text = ""

                        // Loop through the array elements
                        for (i in 0 until response.length()){
                            val itemID = (response[i] as JSONArray)[0] as Int
                            val itemName = (response[i] as JSONArray)[1] as String
                            val count = (response[i] as JSONArray)[2]

                            // Display the formatted json data in text view
                            textView.append("$itemID $itemName\nCount: $count\n\n")
                        }

                    } catch (e: JSONException){
                        textView.text = e.message
                    }

                    progressBar.visibility = View.INVISIBLE
                },
                { error ->
                    textView.text = error.message
                    progressBar.visibility = View.INVISIBLE
                }
            )

            // add network request to volley queue
            VolleySingleton.getInstance(applicationContext).addToRequestQueue(request)

    }
}