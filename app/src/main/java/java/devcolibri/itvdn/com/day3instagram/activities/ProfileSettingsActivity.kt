package java.devcolibri.itvdn.com.day3instagram.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile_settings.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.utils.FirebaseHelper

class ProfileSettingsActivity : BaseActivity() {
    private lateinit var mFirebase: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        mFirebase = FirebaseHelper(this)
        sign_out_text.setOnClickListener { mFirebase.auth.signOut() }
        back_image.setOnClickListener { finish() }
    }
}