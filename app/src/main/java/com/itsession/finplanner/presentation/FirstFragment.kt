package com.itsession.finplanner.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsession.finplanner.R
import com.itsession.finplanner.databinding.FragmentFirstBinding
import domain.ExtensionMethods.roundFigures
import domain.ExtensionMethods.setIsVisible
import domain.ExtensionMethods.toFinancialValue
import com.itsession.finplanner.presentation.uicomponents.ChartAdapter
import domain.ChartData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinComponent
import java.lang.NumberFormatException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), KoinComponent {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.portfolioGraph.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.portfolioGraph.adapter = ChartAdapter().apply {
            setData(
                listOf(ChartData("Jan / 2022", 1000.0), ChartData("Jan / 2023", 1100.0),
                    ChartData("Jan / 2024", 1400.0), ChartData("Jan / 2025", 1700.0),
                    ChartData("Jan / 2026", 2100.0), ChartData("Jan / 2027", 3000.0)), 0,
                onPeriodSelectedListener = { selected, position ->
                    Toast.makeText(context, "Item "+position+" is " +if(selected)"selected" else "unselected", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun setupSeekBars(){
      //  binding.seekBarRate.setProgressWithoutListener((chartViewModel.rates[currentlySelected] * 100).toInt())
      //  binding.seekBarUnique.setProgressWithoutListener(chartViewModel.uniqueSavings[currentlySelected].toInt())
      //  binding.seekBarMonth.setProgressWithoutListener(chartViewModel.monthSavings[currentlySelected].toInt())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}