package com.bsuir.neural_network.app.screens.app.cabinet

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.neural_network.R
import com.bsuir.neural_network.app.dto.HistoryAnswerDTO
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.databinding.ItemHistoryBinding
import com.bsuir.neural_network.databinding.ItemImageBinding
import com.bumptech.glide.Glide

interface HistoryForUserActionListener {
    fun copyLink(history: HistoryAnswerDTO)
    fun onHistoryDetails(history: HistoryAnswerDTO)
}

class HistoryDiffCallback(
    private val oldList: List<HistoryAnswerDTO>,
    private val newList: List<HistoryAnswerDTO>
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

class HistoryForUserAdapter(
    private val actionListener: HistoryForUserActionListener
) : RecyclerView.Adapter<HistoryForUserAdapter.HistoryViewHolder>(), View.OnClickListener {

    class HistoryViewHolder(val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root)

    var historyAnswerDTOs: List<HistoryAnswerDTO> = emptyList()
        set(newValue) {
            val diffCallback = HistoryDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyAnswerDTOs[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = history
            moreImageViewButton.tag = history
            val result = "Ключевые слова: ${history.words.joinToString(separator = ", ")}"
            tvKeywords.text = result
            if(history.url.isNotBlank()){
                Glide.with(imageView.context)
                    .load(history.url)
//                    .circleCrop()
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_image)
            }
        }
    }

    override fun getItemCount(): Int = historyAnswerDTOs.size

    override fun onClick(v: View) {
        val history = v.tag as HistoryAnswerDTO
        when (v.id){
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }else -> {
            actionListener.onHistoryDetails(history)
        }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val history = v.tag as HistoryAnswerDTO
        popupMenu.menu.add(1, COPY, Menu.NONE, context.getString(R.string.copy))
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                COPY -> {
                    actionListener.copyLink(history)
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