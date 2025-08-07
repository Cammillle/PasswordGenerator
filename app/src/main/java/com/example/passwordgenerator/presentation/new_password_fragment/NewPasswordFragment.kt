package com.example.passwordgenerator.presentation.new_password_fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.passwordgenerator.AppApplication
import com.example.passwordgenerator.databinding.FragmentNewPasswordBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewPasswordFragment : Fragment() {

    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: NewPasswordViewModelFactory

    private lateinit var viewModel: NewPasswordViewModel

    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                viewModel.importPasswordsFromFile(requireContext(), it)
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AppApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewPasswordViewModel::class.java]
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        with(binding) {
            generatePasswordButton.setOnClickListener {
                val length = passwordLength.text.toString().toIntOrNull() ?: 0
                val useUppercase = useUppercaseCheckbox.isChecked
                val useNumbers = useNumbersCheckbox.isChecked
                val useSpecial = useSpecialCharsCheckbox.isChecked

                viewModel.generatePassword(length, useUppercase, useNumbers, useSpecial)
            }

            savePasswordButton.setOnClickListener {
                viewModel.saveGeneratedPassword()
            }

            loadPasswordsFromFileButton.setOnClickListener {
                filePickerLauncher.launch(arrayOf("text/plain"))
            }
        }

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {

            with(binding) {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.generatedPassword.collect { password ->
                            generatedPasswordField.setText(password)
                        }
                    }
                    launch {
                        viewModel.entropy.collect { entropy ->
                            passwordEntropyField.text = "Entropy: $entropy"
                        }
                    }
                    launch {
                        viewModel.eventFlow.collectLatest { event ->
                            when (event) {
                                is NewPasswordViewModel.UiEvent.Success -> {
                                    Toast.makeText(
                                        requireContext(), event.message, Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is NewPasswordViewModel.UiEvent.Error -> {
                                    Toast.makeText(
                                        requireContext(), event.message, Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                        }

                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}