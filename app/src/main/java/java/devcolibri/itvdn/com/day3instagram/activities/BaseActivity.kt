package java.devcolibri.itvdn.com.day3instagram.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_profile.*
import java.devcolibri.itvdn.com.day3instagram.R


abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var commonViewModel: CommonViewModel

    companion object {
        const val TAG = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        commonViewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
        commonViewModel.errorMessage.observe(this, Observer {
            it?.let {
                showToast(it)
            }
        })
    }

    protected inline fun <reified T : ViewModel> initViewModel(): T =
        ViewModelProviders.of(this, ViewModelFactory(commonViewModel)).get(T::class.java)

}