package java.devcolibri.itvdn.com.day3instagram.activities

import android.os.Bundle
import android.util.Log
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.views.setupBottomNavigation

class LikesActivity : BaseActivity() {

    private val TAG = "LikesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigation(3)
        Log.d(TAG, "onCreate: Likes")
    }

}