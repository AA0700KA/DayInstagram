package java.devcolibri.itvdn.com.day3instagram.screens.add_friends

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_add_friends.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.User
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseActivity
import java.devcolibri.itvdn.com.day3instagram.screens.common.showToast

class AddFriendsActivity : BaseActivity(),
    FriendsAdapter.Listener {

    private lateinit var mViewModel: AddFriendsViewModel
    private lateinit var mUser: User
    private lateinit var mUsers: List<User>
    private lateinit var mAdapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friends)

        mAdapter = FriendsAdapter(this)

        mViewModel = initViewModel()

        back_image.setOnClickListener { finish() }


        add_friends_recycler.adapter = mAdapter
        add_friends_recycler.layoutManager = LinearLayoutManager(this)

        mViewModel.userAndFriends.observe(this, Observer {
            it?.let { (user, otherUsers) ->
                mUser = user
                mUsers = otherUsers

                mAdapter.update(mUsers, mUser.follows)
            }
        })

    }

    override fun follow(uid: String) {
        setFollow(uid, true) {
            mAdapter.followed(uid)
        }
    }

    override fun unfollow(uid: String) {
        setFollow(uid, false) {
            mAdapter.unfollowed(uid)
        }
    }

    private fun setFollow(uid: String, follow: Boolean, onSuccess: () -> Unit) {
        mViewModel.setFollow(mUser.uid!!, uid, follow)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message!!) }
    }

}



