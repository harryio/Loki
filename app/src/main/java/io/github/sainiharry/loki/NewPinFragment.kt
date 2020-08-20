package io.github.sainiharry.loki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.github.sainiharry.loki.utils.EventObserver
import kotlinx.android.synthetic.main.pin_layout.*

class NewPinFragment : Fragment() {

    private val viewModel by viewModels<NewPinViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_new_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pin_entry.addTextChangedListener {
            viewModel.handleNewPin(it.toString())
        }

        viewModel.confirmPinNavigationEvent.observe(viewLifecycleOwner, EventObserver {
            // TODO: 20/08/20 Navigate to confirm pin here
        })
    }
}