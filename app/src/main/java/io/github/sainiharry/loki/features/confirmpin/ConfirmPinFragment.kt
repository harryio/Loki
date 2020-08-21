package io.github.sainiharry.loki.features.confirmpin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.github.sainiharry.loki.AuthenticationViewModel
import io.github.sainiharry.loki.PrefInteractor
import io.github.sainiharry.loki.R
import io.github.sainiharry.loki.utils.EventObserver
import kotlinx.android.synthetic.main.pin_layout.*

class ConfirmPinFragment : Fragment() {

    private val args: ConfirmPinFragmentArgs by navArgs()

    private val viewModel by viewModels<ConfirmPinViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ConfirmPinViewModel(args.currentPin) as T
            }
        }
    }

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_confirm_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        pin_entry.addTextChangedListener {
            viewModel.handleConfirmPin(it.toString())
        }

        viewModel.pinSetSuccessEvent.observe(viewLifecycleOwner, EventObserver {
            authenticationViewModel.setUserAuthenticated()
            PrefInteractor.storePin(it)
            navController.navigate(ConfirmPinFragmentDirections.popToSettings())
            Toast.makeText(requireContext(), R.string.pin_set_success_msg, Toast.LENGTH_SHORT)
                .show()
        })
        viewModel.pinSetErrorEvent.observe(viewLifecycleOwner, EventObserver {
            pin_entry.setText("")
            Toast.makeText(requireContext(), R.string.pin_set_error_msg, Toast.LENGTH_SHORT).show()
        })
    }
}