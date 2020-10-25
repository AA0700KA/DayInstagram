package java.devcolibri.itvdn.com.day3instagram.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.activities.add_friends.AddFriendsActivity
import java.devcolibri.itvdn.com.day3instagram.activities.edit_profile.EditProfileActivity
import java.devcolibri.itvdn.com.day3instagram.models.User
import java.devcolibri.itvdn.com.day3instagram.utils.FirebaseHelper
import java.devcolibri.itvdn.com.day3instagram.utils.ValueEventListenerAdapter
import java.devcolibri.itvdn.com.day3instagram.views.setupBottomNavigation

class ProfileActivity : BaseActivity() {

    private val TAG = "ProfileActivity"
    private lateinit var mFirebase: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupBottomNavigation(4)
        Log.d(TAG, "onCreate: Profile")

        mFirebase = FirebaseHelper(this)

        edit_profile_btn.setOnClickListener({
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        })

        settings_image.setOnClickListener {
            val intent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(intent)
        }
        add_friends_image.setOnClickListener{
            val intent = Intent(this, AddFriendsActivity::class.java)
            startActivity(intent)
        }

        images_recycler.layoutManager = GridLayoutManager(this, 3)
        mFirebase.database.child("images").child(mFirebase.auth.currentUser!!.uid)
            .addValueEventListener(ValueEventListenerAdapter {
                val images = it.children.map { it.getValue(String::class.java)!! }
                images_recycler.adapter = ImagesAdapter(images)
            })

    }

    override fun onStart() {
        super.onStart()

        mFirebase.currentUserReference().addListenerForSingleValueEvent(ValueEventListenerAdapter{
            val user: User = it.getValue(User::class.java)!!
            profile_image.loadUserPhoto(user.photo)
            username_text.text = user.username
        })
    }

}

class ImagesAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    class ViewHolder(val image: ImageView) : RecyclerView.ViewHolder(image)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val image = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false) as ImageView
        return ViewHolder(image)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.loadImage(images[position])
    }

    private fun ImageView.loadImage(image: String) {
        Glide.with(this).load(image).into(this)
    }

    override fun getItemCount(): Int = images.size
}

class SquareImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}