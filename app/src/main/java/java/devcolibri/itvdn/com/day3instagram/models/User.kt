package java.devcolibri.itvdn.com.day3instagram.models

import com.google.firebase.database.Exclude

data class User(val name: String = "", val username: String = "", val email: String = "",
                val follows: Map<String, Boolean> = emptyMap(),
                val followers: Map<String, Boolean> = emptyMap(),
                val website: String = "", val bio: String = "", val phone: String = "",
                val photo: String = "", @Exclude val uid: String = "")