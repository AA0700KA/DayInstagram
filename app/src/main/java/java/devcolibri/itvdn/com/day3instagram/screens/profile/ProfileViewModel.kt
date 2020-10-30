package java.devcolibri.itvdn.com.day3instagram.screens.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import java.devcolibri.itvdn.com.day3instagram.data.UsersRepository
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseViewModel

class ProfileViewModel(private val usersRepo: UsersRepository, onFailureListener: OnFailureListener) : BaseViewModel(onFailureListener) {
    val user = usersRepo.getUser()
    lateinit var images: LiveData<List<String>>

    fun init(uid: String) {
        images = usersRepo.getImages(uid)
    }

}
