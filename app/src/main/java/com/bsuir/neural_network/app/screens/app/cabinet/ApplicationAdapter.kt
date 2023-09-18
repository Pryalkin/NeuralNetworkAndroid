package com.bsuir.neural_network.app.screens.app.cabinet

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.neural_network.R
import com.bsuir.neural_network.app.dto.ApplicationAnswerDTO
import com.bsuir.neural_network.app.dto.utils.Role
import com.bsuir.neural_network.app.dto.utils.Status
import com.bsuir.neural_network.databinding.ItemApplicationBinding

interface AActionListener {
    fun onDetails(applicationAnswerDTO: ApplicationAnswerDTO)
    fun payment(applicationAnswerDTO: ApplicationAnswerDTO)
    fun accept(app: ApplicationAnswerDTO)
    fun ready(app: ApplicationAnswerDTO)
}

class ADiffCallback(
    private val oldList: List<ApplicationAnswerDTO>,
    private val newList: List<ApplicationAnswerDTO>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldA = oldList[oldItemPosition]
        val newA= newList[newItemPosition]
        return oldA.id == newA.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldA = oldList[oldItemPosition]
        val newA = newList[newItemPosition]
        return oldA == newA
    }

}

class ApplicationAdapter(
    private val actionListener: AActionListener,
    private val role: String
) : RecyclerView.Adapter<ApplicationAdapter.AViewHolder>(), View.OnClickListener {

    class AViewHolder(val binding: ItemApplicationBinding): RecyclerView.ViewHolder(binding.root)

    var applicationAnswerDTOs: List<ApplicationAnswerDTO> = emptyList()
        set(newValue) {
            val diffCallback = ADiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemApplicationBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return AViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AViewHolder, position: Int) {
        val app = applicationAnswerDTOs[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = app
            moreImageViewButton.tag = app
            tvForm.setText(app.sampleApplicationDTO.name)
            tvText.setText(app.sampleApplicationDTO.text)
        }
    }

    override fun getItemCount(): Int = applicationAnswerDTOs.size

    override fun onClick(v: View) {
        val app = v.tag as ApplicationAnswerDTO
        if(app.status == Status.PAYMENT.name){
            when (v.id){
                R.id.moreImageViewButton -> {
                    showPopupMenu(v)
                }
                else -> {
                    actionListener.onDetails(app)
                }
            }
        } else {
            showPopupMenu(v)
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val app = v.tag as ApplicationAnswerDTO

        if(role == Role.ROLE_PERSON.name){
            if (app.status == Status.PAYMENT.name)
                popupMenu.menu.add(0, PAYMENT, Menu.NONE, context.getString(R.string.payment))
        } else if(role == Role.ROLE_MANAGER.name){
            if (app.status == Status.INACTIVITY.name)
                popupMenu.menu.add(0, ACCEPT, Menu.NONE, context.getString(R.string.accept))
            if (app.status == Status.ACTIVITY.name)
                popupMenu.menu.add(0, READY, Menu.NONE, context.getString(R.string.ready))
        }

        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                PAYMENT -> {
                    actionListener.payment(app)
                }
                ACCEPT -> {
                    actionListener.accept(app)
                }
                READY -> {
                    actionListener.ready(app)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val PAYMENT = 1
        private const val READY = 2
        private const val ACCEPT = 3
    }

}