package com.bsuir.neural_network.app.screens.app.home

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.neural_network.R
import com.bsuir.neural_network.app.dto.PersonAnswerDTO
import com.bsuir.neural_network.app.dto.utils.Role
import com.bsuir.neural_network.databinding.ItemPersonBinding
import com.bumptech.glide.Glide

interface PersonActionListener {
    fun add(personAnswerDTO: PersonAnswerDTO)
    fun delete(personAnswerDTO: PersonAnswerDTO)
}

class PersonDiffCallback(
    private val oldList: List<PersonAnswerDTO>,
    private val newList: List<PersonAnswerDTO>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSA = oldList[oldItemPosition]
        val newSA= newList[newItemPosition]
        return oldSA.id == newSA.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson == newPerson
    }

}

class PersonAdapter(
    private val actionListener: PersonActionListener
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(), View.OnClickListener {

    class PersonViewHolder(val binding: ItemPersonBinding): RecyclerView.ViewHolder(binding.root)

    var people: List<PersonAnswerDTO> = emptyList()
        set(newValue) {
            val diffCallback = PersonDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = person
            moreImageViewButton.tag = person
            userNameTextView.setText("${person.surname} ${person.name} ${person.patronymic}")
            userEnrollTextView.setText("${person.passportSeries}${person.passportNumber} - ${person.role}")
            Glide.with(photoImageView.context)
                .load(R.drawable.ic_person)
                .into(photoImageView)
        }
    }

    override fun getItemCount(): Int = people.size

    override fun onClick(v: View) {
//        val person = v.tag as PersonDTO
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
        val person = v.tag as PersonAnswerDTO
        if (person.role == Role.ROLE_PERSON.name)
            popupMenu.menu.add(0, ADD, Menu.NONE, context.getString(R.string.add))
        if (person.role == Role.ROLE_MANAGER.name)
            popupMenu.menu.add(1, DELETE, Menu.NONE, context.getString(R.string.delete))
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                ADD -> {
                    actionListener.add(person)
                }
                DELETE -> {
                    actionListener.delete(person)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val ADD = 1
        private const val DELETE = 2
    }

}