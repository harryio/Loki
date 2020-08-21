package io.github.sainiharry.loki

import android.os.Bundle
import android.text.TextWatcher
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
import io.github.sainiharry.loki.utils.EventObserver
import kotlinx.android.synthetic.main.pin_layout.*

class EnterPinFragment : Fragment() {

    private val args: EnterPinFragmentArgs by navArgs()

    private var textWatcher: TextWatcher? = null

    private val viewModel by viewModels<EnterPinViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return EnterPinViewModel(args.existingPin) as T
            }
        }
    }

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_enter_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        viewModel.pinEnterSuccessEvent.observe(viewLifecycleOwner, EventObserver {
            authenticationViewModel.setUserAuthenticated()
        })

        viewModel.settingsNavigationEvent.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), R.string.welcome_back, Toast.LENGTH_SHORT).show()
            navController.navigate(EnterPinFragmentDirections.actionSettings())
        })
    }

    override fun onStart() {
        super.onStart()
        textWatcher = pin_entry.addTextChangedListener {
            viewModel.handlePinInput(it.toString())
        }
    }

    override fun onStop() {
        super.onStop()
        pin_entry.setText("")
        pin_entry.removeTextChangedListener(textWatcher)
    }
}