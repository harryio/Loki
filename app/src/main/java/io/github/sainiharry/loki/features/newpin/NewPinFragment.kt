package io.github.sainiharry.loki.features.newpin

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.github.sainiharry.loki.R
import io.github.sainiharry.loki.utils.EventObserver
import kotlinx.android.synthetic.main.pin_layout.*

class NewPinFragment : Fragment() {

    private val viewModel by viewModels<NewPinViewModel>()

    private var textWatcher: TextWatcher? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_new_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        viewModel.confirmPinNavigationEvent.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(NewPinFragmentDirections.actionConfirmPin(it))
        })
    }

    override fun onStart() {
        super.onStart()
        textWatcher = pin_entry.addTextChangedListener {
            viewModel.handleNewPin(it.toString())
        }
    }

    override fun onStop() {
        super.onStop()
        pin_entry.setText("")
        pin_entry.removeTextChangedListener(textWatcher)
    }
}