package java.devcolibri.itvdn.com.day3instagram.screens.common

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.devcolibri.itvdn.com.day3instagram.screens.InstagramApp
import java.devcolibri.itvdn.com.day3instagram.screens.login.LoginActivity


abstract class BaseActivity : AppCompatActivity() {

   lateinit var commonViewModel: CommonViewModel

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

    inline fun <reified T : BaseViewModel> initViewModel(): T =
        ViewModelProviders.of(this, ViewModelFactory(application as InstagramApp, commonViewModel,
            commonViewModel)).get(T::class.java)

    fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}