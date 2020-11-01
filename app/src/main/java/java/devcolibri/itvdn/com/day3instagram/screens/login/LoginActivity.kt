package java.devcolibri.itvdn.com.day3instagram.screens.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.screens.register.RegisterActivity
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseActivity
import java.devcolibri.itvdn.com.day3instagram.screens.common.setupAuthGuard
import java.devcolibri.itvdn.com.day3instagram.screens.common.showToast
import java.devcolibri.itvdn.com.day3instagram.screens.home.HomeActivity

class LoginActivity : BaseActivity(), KeyboardVisibilityEventListener, TextWatcher, View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        KeyboardVisibilityEvent.setEventListener(this, this)
        login_btn.isEnabled = false
        email_input.addTextChangedListener(this)
        password_input.addTextChangedListener(this)
        login_btn.setOnClickListener(this)
        create_account_text.setOnClickListener(this)

        mViewModel = initViewModel()
        mViewModel.goToHomeScreen.observe(this, Observer {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        })
        mViewModel.goToRegisterScreen.observe(this, Observer {
            startActivity(Intent(this, RegisterActivity::class.java))
        })
        mAuth = FirebaseAuth.getInstance()


    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.login_btn ->
                mViewModel.onLoginClick(
                    email = email_input.text.toString(),
                    password = password_input.text.toString()
                )
            R.id.create_account_text -> mViewModel.onRegisterClick()

        }
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if (isKeyboardOpen) {
            scroll_view.scrollTo(0, scroll_view.bottom)
            create_account_text.visibility = View.GONE
        } else {
            scroll_view.scrollTo(0, scroll_view.top)
            create_account_text.visibility = View.VISIBLE
        }
    }

    companion object {
        const val TAG = "LoginActivity"
    }

    override fun afterTextChanged(s: Editable?) {
        login_btn.isEnabled =
                email_input.text.toString().isNotEmpty() &&
                password_input.text.toString().isNotEmpty()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

}