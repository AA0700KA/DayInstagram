package java.devcolibri.itvdn.com.day3instagram.activities

import android.os.Bundle
import android.util.Log
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.views.setupBottomNavigation

class SearchActivity : BaseActivity() {

    private val TAG = "SearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigation(1)
        Log.d(TAG, "onCreate: Search")
    }

}