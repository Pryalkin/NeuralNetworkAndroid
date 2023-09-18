package com.bsuir.neural_network.app.screens.app.home

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.neural_network.R
import com.bsuir.neural_network.app.dto.utils.Role
import com.bsuir.neural_network.app.dto.utils.SampleApplicationDTO
import com.bsuir.neural_network.databinding.ItemAppBinding

interface SAActionListener {
    fun onDetails(sampleApplication: SampleApplicationDTO)
    fun apply(sampleApplication: SampleApplicationDTO)
    fun copyLink(sampleApplication: SampleApplicationDTO)
}

class SADiffCallback(
    private val oldList: List<SampleApplicationDTO>,
    private val newList: List<SampleApplicationDTO>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSA = oldList[oldItemPosition]
        val newSA= newList[newItemPosition]
        return oldSA.id == newSA.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSA = oldList[oldItemPosition]
        val newSA = newList[newItemPosition]
        return oldSA == newSA
    }

}

class AppAdapter(
    private val actionListener: SAActionListener,
    private val role: String
) : RecyclerView.Adapter<AppAdapter.SAViewHolder>(), View.OnClickListener {

    class SAViewHolder(val binding: ItemAppBinding): RecyclerView.ViewHolder(binding.root)

    var sampleApplications: List<SampleApplicationDTO> = emptyList()
        set(newValue) {
            val diffCallback = SADiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SAViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAppBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return SAViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SAViewHolder, position: Int) {
        val app = sampleApplications[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = app
            moreImageViewButton.tag = app
            tvForm.setText(app.name)
            tvText.setText(app.text)
        }
    }

    override fun getItemCount(): Int = sampleApplications.size

    override fun onClick(v: View) {
        val app = v.tag as SampleApplicationDTO
        when (v.id){
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
//            else -> {
//                actionListener.onDetails(app)
//            }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val app = v.tag as SampleApplicationDTO
        if (role == Role.ROLE_PERSON.name)
            popupMenu.menu.add(0, APPLY, Menu.NONE, context.getString(R.string.add))
        popupMenu.menu.add(1, COPY, Menu.NONE, context.getString(R.string.copy))
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                APPLY -> {
                    actionListener.apply(app)
                }
                COPY -> {
                    actionListener.copyLink(app)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val APPLY = 1
        private const val COPY = 2
    }

}