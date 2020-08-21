package io.github.sainiharry.loki

import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.github.sainiharry.loki.utils.EventObserver
import kotlinx.android.synthetic.main.fragment_enter_pin.*
import kotlinx.android.synthetic.main.pin_layout.*

class EnterPinFragment : Fragment() {

    private val args: EnterPinFragmentArgs by navArgs()

    private var textWatcher: TextWatcher? = null

    private var countDownTimer: CountDownTimer? = null

    private val viewModel by viewModels<EnterPinViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return EnterPinViewModel(args.existingPin, PrefInteractor.getUnlockSeconds()) as T
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

        viewModel.startCountDownEvent.observe(viewLifecycleOwner, EventObserver {
            countDownTimer?.cancel()
            PrefInteractor.setUnlockSeconds(it)
            countDownTimer = object : CountDownTimer((it + 1).toLong() * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val unlockSeconds = (millisUntilFinished / 1000).toInt()
                    PrefInteractor.setUnlockSeconds(unlockSeconds)
                    viewModel.handleUnlockSeconds(unlockSeconds)
                }

                override fun onFinish() {
                    PrefInteractor.setUnlockSeconds(0)
                    viewModel.handleUnlockSeconds(0)
                }
            }
            countDownTimer!!.start()
        })

        viewModel.pinInputEnabled.observe(viewLifecycleOwner, Observer {
            pin_entry.isEnabled = it ?: true
        })

        viewModel.incorrectMsgVisible.observe(viewLifecycleOwner, Observer { visible ->
            incorrect_msg.visibility = if (visible) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        viewModel.lockedMsgVisible.observe(viewLifecycleOwner, Observer { visible ->
            locked_msg.visibility = if (visible) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        viewModel.incorrectPinEnteredEvent.observe(viewLifecycleOwner, EventObserver {
            pin_entry.setText("")
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

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}