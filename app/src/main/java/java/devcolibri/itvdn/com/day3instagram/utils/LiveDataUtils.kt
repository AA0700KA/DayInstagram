package java.devcolibri.itvdn.com.day3instagram.utils

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import java.devcolibri.itvdn.com.day3instagram.activities.add_friends.FirebaseLiveData

fun DatabaseReference.liveData(): LiveData<DataSnapshot> = FirebaseLiveData(this)