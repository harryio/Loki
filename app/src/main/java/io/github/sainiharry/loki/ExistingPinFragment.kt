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
import androidx.navigation.fragment.navArgs
import io.github.sainiharry.loki.utils.EventObserver

class ExistingPinFragment : Fragment() {

    private val args: ExistingPinFragmentArgs by navArgs()

    private val viewModel by viewModels<ExistingPinViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ExistingPinViewModel(args.existingPin) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_existing_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        viewModel.pinSuccessEvent.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(ExistingPinFragmentDirections.actionSettings())
        })
    }
}