package java.devcolibri.itvdn.com.day3instagram.screens.profilesettings

import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import java.devcolibri.itvdn.com.day3instagram.common.AuthManager
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseViewModel

class ProfileSettingsViewModel(private val authManager: AuthManager, onFailureListener: OnFailureListener) : BaseViewModel(onFailureListener),
    AuthManager by authManager