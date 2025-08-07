package com.example.passwordgenerator.presentation.password_list_fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passwordgenerator.AppApplication
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.FragmentPasswordListBinding
import com.example.passwordgenerator.domain.model.PasswordListItem
import com.example.passwordgenerator.domain.model.PasswordListUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordListFragment : Fragment() {

    private var _binding: FragmentPasswordListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PasswordListViewModelFactory

    private lateinit var viewModel: PasswordListViewModel

    private lateinit var listAdapter: PasswordListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AppApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[PasswordListViewModel::class.java]
        setupRecyclerView()
        observeUiState()

        binding.bAdd.setOnClickListener {
            findNavController().navigate(R.id.action_passwordListFragment_to_newPasswordFragment)
        }
        binding.buttonBack.setOnClickListener {
            viewModel.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRoot()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is PasswordListUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.rvPasswordList.visibility = View.GONE
                        binding.buttonBack.visibility = View.GONE
                    }

                    is PasswordListUiState.Root -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvPasswordList.visibility = View.VISIBLE
                        binding.buttonBack.visibility = View.GONE
                        val items = state.folders.map { PasswordListItem.FolderItem(it) } +
                                state.passwords.map { PasswordListItem.PasswordItem(it) }
                        listAdapter.submitList(items)
                    }

                    is PasswordListUiState.FolderContent -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvPasswordList.visibility = View.VISIBLE
                        binding.buttonBack.visibility = View.VISIBLE
                        val items = state.passwords.map { PasswordListItem.PasswordItem(it) }
                        listAdapter.submitList(items)
                    }
                }

            }
        }
    }

    private fun setupRecyclerView() {
        listAdapter = PasswordListAdapter(
            onFolderClick = { folder -> viewModel.openFolder(folder) },
            onCopyClick = { password -> copyPasswordToClipboard(password.value) },
            onDeleteClick = { password -> viewModel.deletePassword(password) }
        )

        binding.rvPasswordList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPasswordList.adapter = listAdapter

        binding.exportButton.setOnClickListener {
            viewModel.exportPasswords { lines ->
                exportToFile(lines)
            }
        }
    }

    private fun copyPasswordToClipboard(password: String) {
        val clipboardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(getString(R.string.password), password)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(requireContext(), getString(R.string.password_copy), Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("StringFormatMatches")
    private fun exportToFile(lines: List<String>) {
        Toast.makeText(requireContext(),
            getString(R.string.passwords_exported, lines.size), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}