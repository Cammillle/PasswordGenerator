package com.example.passwordgenerator.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.R
import com.example.passwordgenerator.data.entity.PasswordEntity
import com.example.passwordgenerator.domain.model.PasswordListItem

class PasswordListAdapter :
    ListAdapter<PasswordListItem, RecyclerView.ViewHolder>(PasswordListDiffCallback()) {

    class PasswordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val passwordValue = view.findViewById<TextView>(R.id.passwordValue)
        private val passwordEntropy = view.findViewById<TextView>(R.id.passwordEntropy)

        fun bind(password: PasswordEntity) {
            passwordValue.text = password.value
            passwordEntropy.text = "Энтропия: ${password.entropy}"
        }
    }

    class FolderHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val folderTitle = view.findViewById<TextView>(R.id.folderTitle)

        fun bind(item: PasswordListItem.FolderHeaderItem) {
            folderTitle.text = item.folderName
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PasswordListItem.FolderHeaderItem -> VIEW_TYPE_HEADER
            is PasswordListItem.PasswordItem -> VIEW_TYPE_PASSWORD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            VIEW_TYPE_HEADER -> FolderHeaderViewHolder(
                inflater.inflate(R.layout.item_folder_header,parent,false)
            )
            else -> PasswordViewHolder(
                inflater.inflate(R.layout.item_password,parent,false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PasswordListItem.FolderHeaderItem -> (holder as FolderHeaderViewHolder).bind(item)
            is PasswordListItem.PasswordItem -> (holder as PasswordViewHolder).bind(item.password)
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_PASSWORD = 1
    }
}