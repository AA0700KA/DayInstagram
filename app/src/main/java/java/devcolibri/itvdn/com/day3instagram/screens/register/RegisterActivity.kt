package java.devcolibri.itvdn.com.day3instagram.screens.register

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register_email.*
import kotlinx.android.synthetic.main.fragment_register_namepass.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.User
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseActivity
import java.devcolibri.itvdn.com.day3instagram.screens.common.showToast
import java.devcolibri.itvdn.com.day3instagram.screens.home.HomeActivity

class RegisterActivity : BaseActivity(),
    EmailFragment.Listener,
    NamePassFragment.Listener {


    private lateinit var mViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mViewModel = initViewModel()
        mViewModel.goToNamePassScreen.observe(this, Observer {
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, NamePassFragment())
                .addToBackStack(null)
                .commit()
        })
        mViewModel.goToHomeScreen.observe(this, Observer {
            startHomeActivity()
        })
        mViewModel.goBackToEmailScreen.observe(this, Observer {
            supportFragmentManager.popBackStack()
        })

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.frame_layout, EmailFragment())
                .commit()
        }
    }

    override fun onNext(email: String) {
        mViewModel.onEmailEntered(email)
    }

    override fun onRegister(fullName: String, password: String) {
        mViewModel.onRegister(fullName, password)
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    companion object {
        const val TAG = "RegisterActivity"
    }

}



