package java.devcolibri.itvdn.com.day3instagram.screens.search

import android.os.Bundle
import android.util.Log
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseActivity
import java.devcolibri.itvdn.com.day3instagram.screens.common.setupAuthGuard
import java.devcolibri.itvdn.com.day3instagram.screens.common.setupBottomNavigation

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation(1)
        Log.d(TAG, "onCreate: Search")

        setupAuthGuard { }
    }

    companion object {
        const val TAG = "SearchActivity"
    }

}