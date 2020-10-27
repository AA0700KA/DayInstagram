package java.devcolibri.itvdn.com.day3instagram.screens.profilesettings

import android.arch.lifecycle.ViewModel
import java.devcolibri.itvdn.com.day3instagram.common.AuthManager

class ProfileSettingsViewModel(private val authManager: AuthManager) : ViewModel(),
    AuthManager by authManager