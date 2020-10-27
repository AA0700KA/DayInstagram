package java.devcolibri.itvdn.com.day3instagram.screens.profilesettings

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile_settings.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseActivity
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.FirebaseHelper
import java.devcolibri.itvdn.com.day3instagram.screens.common.setupAuthGuard

class ProfileSettingsActivity : BaseActivity() {
    private lateinit var mFirebase: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        setupAuthGuard {
            val viewModel = initViewModel<ProfileSettingsViewModel>()
            sign_out_text.setOnClickListener { viewModel.signOut() }
            back_image.setOnClickListener { finish() }
        }
    }
}