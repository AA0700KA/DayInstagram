package java.devcolibri.itvdn.com.day3instagram.data.firebase.common

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import java.devcolibri.itvdn.com.day3instagram.common.ValueEventListenerAdapter

class FirebaseLiveData(private val reference: Query) : LiveData<DataSnapshot>() {

    private val listener = ValueEventListenerAdapter {
        value = it
    }

    override fun onActive() {
        super.onActive()
        reference.addValueEventListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        reference.removeEventListener(listener)
    }

}