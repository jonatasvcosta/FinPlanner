package com.itsession.finplanner.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.itsession.finplanner.R
import com.itsession.finplanner.databinding.FragmentSecondBinding
import domain.InputScreenData
import domain.InputScreenType
import org.koin.android.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), KoinComponent {

    private var _binding: FragmentSecondBinding? = null
    private val chartViewModel : ChartViewModel by sharedViewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun setupInputScreen(){
        context?.let {
            binding.customInputScreen.setupInputScreen(
                chartViewModel.getInputData(it)
            ){
                chartViewModel.nextStep {
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
                setupInputScreen()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInputScreen()
        binding.customInputScreen.editText?.performClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}