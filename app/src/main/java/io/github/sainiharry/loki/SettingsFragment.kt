package io.github.sainiharry.loki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.sainiharry.loki.utils.EventObserver
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val prefInteractor by lazy {
        PrefInteractor(requireContext())
    }

    private val viewModel by viewModels<SettingsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(prefInteractor.getPin()) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        pin_toggle.isChecked = viewModel.isPinSet()
        pin_toggle.setOnCheckedChangeListener { _, isChecked -> viewModel.handlePinToggle(isChecked) }

        viewModel.newPinNavigationEvent.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(SettingsFragmentDirections.actionNewPin())
        })
    }
}