package java.devcolibri.itvdn.com.day3instagram.screens.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.devcolibri.itvdn.com.day3instagram.R


class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private var images = listOf<String>()

    class ViewHolder(val image: ImageView) : RecyclerView.ViewHolder(image)

    fun updateImages(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

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
