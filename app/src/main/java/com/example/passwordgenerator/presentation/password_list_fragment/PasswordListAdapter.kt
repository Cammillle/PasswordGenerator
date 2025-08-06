package com.example.passwordgenerator.presentation.password_list_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.R
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.model.PasswordFolder
import com.example.passwordgenerator.domain.model.PasswordListItem

class PasswordListAdapter(
    private val onFolderClick: (PasswordFolder) -> Unit,
    private val onDeleteClick: (Password) -> Unit,
    private val onCopyClick: (Password) -> Unit
) :
    ListAdapter<PasswordListItem, RecyclerView.ViewHolder>(PasswordListDiffCallback()) {

    inner class PasswordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(password: Password) {
            itemView.findViewById<TextView>(R.id.password_value).text = password.value
            itemView.findViewById<ImageView>(R.id.copy_icon).setOnClickListener {
                onCopyClick(password)
            }
            itemView.findViewById<ImageView>(R.id.delete_icon).setOnClickListener {
                onDeleteClick(password)
            }


            password.entropy.let {
                itemView.findViewById<TextView>(R.id.password_entropy).text =
                    "Entropy: %.2f".format(it)
            }

        }
    }

    inner class FolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(folder: PasswordFolder) {
            itemView.findViewById<TextView>(R.id.folder_name).text = folder.name
            itemView.setOnClickListener { onFolderClick(folder) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PasswordListItem.FolderItem -> TYPE_FOLDER
            is PasswordListItem.PasswordItem -> TYPE_PASSWORD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_FOLDER -> FolderViewHolder(
                inflater.inflate(R.layout.item_folder, parent, false)
            )

            else -> PasswordViewHolder(
                inflater.inflate(R.layout.item_password, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PasswordListItem.FolderItem -> (holder as FolderViewHolder).bind(item.folder)
            is PasswordListItem.PasswordItem -> (holder as PasswordViewHolder).bind(item.password)
        }
    }

    companion object {
        private const val TYPE_FOLDER = 0
        private const val TYPE_PASSWORD = 1
    }
}