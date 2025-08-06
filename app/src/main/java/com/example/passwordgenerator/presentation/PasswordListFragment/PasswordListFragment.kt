package com.example.passwordgenerator.presentation.PasswordListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.FragmentPasswordListBinding

class PasswordListFragment : Fragment() {

    private var _binding: FragmentPasswordListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bAdd.setOnClickListener {
            findNavController().navigate(R.id.action_passwordListFragment_to_newPasswordFragment)
        }

//        val adapter = PasswordListAdapter()
//        binding.rvPasswordList.adapter = adapter
//
//        lifecycleScope.launch {
//            val listItems = buildPasswordListItems(folderDao, passwordDao)
//            adapter.submitList(listItems)
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    suspend fun buildPasswordListItems(
//        folderDao: PasswordFolderDao,
//        passwordDao: PasswordDao
//    ): List<PasswordListItem> {
//        val result = mutableListOf<PasswordListItem>()
//
//        // Сначала добавим сгенерированные пароли
//        val generatedPasswords = passwordDao.getGeneratedPasswords()
//        if (generatedPasswords.value?.isNotEmpty() == true) {
//            result.add(PasswordListItem.FolderHeaderItem("Без папки"))
//            generatedPasswords.value?.map { PasswordListItem.PasswordItem(it) }
//                ?.let { result.addAll(it) }
//        }
//
//        // Затем — пароли из папок
//        val folders = folderDao.getAllFolders()
//        for (folder in folders.value) {
//            val passwords = passwordDao.getPasswordsByFolder(folder.id)
//            if (passwords.value.isNotEmpty()) {
//                result.add(PasswordListItem.FolderHeaderItem(folder.name))
//                result.addAll(passwords.value.map { PasswordListItem.PasswordItem(it) })
//            }
//        }
//
//        return result
//    }


}