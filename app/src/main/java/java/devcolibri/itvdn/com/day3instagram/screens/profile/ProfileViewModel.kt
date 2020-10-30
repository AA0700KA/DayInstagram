package java.devcolibri.itvdn.com.day3instagram.screens.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import java.devcolibri.itvdn.com.day3instagram.data.UsersRepository

class ProfileViewModel(private val usersRepo: UsersRepository) : ViewModel() {
    val user = usersRepo.getUser()
    lateinit var images: LiveData<List<String>>

    fun init(uid: String) {
        images = usersRepo.getImages(uid)
    }

}
