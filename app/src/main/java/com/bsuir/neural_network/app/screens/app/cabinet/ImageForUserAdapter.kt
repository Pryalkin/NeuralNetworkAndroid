package com.bsuir.neural_network.app.screens.app.cabinet

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.neural_network.R
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.databinding.ItemImageBinding
import com.bumptech.glide.Glide

interface ImageForUserActionListener {
    fun copyLink(image: ImageAnswerDTO)
}

class ImageDiffCallback(
    private val oldList: List<ImageAnswerDTO>,
    private val newList: List<ImageAnswerDTO>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldImage = oldList[oldItemPosition]
        val newImage= newList[newItemPosition]
        return oldImage.id == newImage.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldList[oldItemPosition]
        val newMovie = newList[newItemPosition]
        return oldMovie == newMovie
    }
}

class ImageForUserAdapter(
    private val actionListener: ImageForUserActionListener
) : RecyclerView.Adapter<ImageForUserAdapter.ImageViewHolder>(), View.OnClickListener {

    class ImageViewHolder(val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root)

    var imageAnswerDTOs: List<ImageAnswerDTO> = emptyList()
        set(newValue) {
            val diffCallback = ImageDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageAnswerDTOs[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = image
            moreImageViewButton.tag = image
            val result = "Ключевые слова: ${image.keywords.joinToString(separator = ", ")}"
            tvKeywords.text = result
            if(image.url.isNotBlank()){
                Glide.with(imageView.context)
                    .load(image.url)
//                    .circleCrop()
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_image)
            }
        }
    }

    override fun getItemCount(): Int = imageAnswerDTOs.size

    override fun onClick(v: View) {
        val movie = v.tag as ImageAnswerDTO
        when (v.id){
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val image = v.tag as ImageAnswerDTO
        popupMenu.menu.add(1, COPY, Menu.NONE, context.getString(R.string.copy))
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                COPY -> {
                    actionListener.copyLink(image)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val COPY = 1
    }

}