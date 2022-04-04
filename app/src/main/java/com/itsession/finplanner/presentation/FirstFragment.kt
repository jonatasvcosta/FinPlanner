package com.itsession.finplanner.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsession.finplanner.R
import com.itsession.finplanner.databinding.FragmentFirstBinding
import com.itsession.finplanner.presentation.domain.ChartData
import com.itsession.finplanner.presentation.domain.ExtensionMethods.roundFigures
import com.itsession.finplanner.presentation.domain.ExtensionMethods.setIsVisible
import com.itsession.finplanner.presentation.domain.ExtensionMethods.toFinancialValue
import com.itsession.finplanner.presentation.uicomponents.ChartAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinComponent

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), KoinComponent {

    private var _binding: FragmentFirstBinding? = null
    private val chartViewModel : ChartViewModel by sharedViewModel()
    private val chartAdapter : ChartAdapter = ChartAdapter()

    companion object{
        const val RATE_PROGRESS_INCREMENT = 1
        const val PERCENTAGE = "%"
        const val MAX_RATE = 30
        const val MAX_MONTHLY_SAVING = 15000
        const val MAX_UNIQUE_SAVING = 500000
        const val CURRENCY = "R$ "
        const val SAVINGS_INCREMENT = 100
        const val EXPECTED_RATE = "Rentabilidade anual esperada:"
        const val EXPECTED_MONTH = "Aportes mensais:"
        const val EXPECTED_UNIQUE = "Aporte anual único:"
    }

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

    private fun loadChartData(){
        val chartData = chartViewModel.getChartData()
        val filteredData = chartData.filter { it.label.contains("12") }
        chartAdapter.setData(filteredData){
            isSelected, position ->
            binding.seekBarMonth.setIsVisible(!isSelected)
            binding.seekBarUnique.setIsVisible(!isSelected)
            binding.seekBarRate.setIsVisible(!isSelected)
            binding.selectedPeriodDetail.apply{
                val selectedChart = filteredData.get(position)
                val sumPassiveIncome = (selectedChart.value - selectedChart.totalSavings - chartViewModel.initialPatrimony)
                portfolioDetail.setIsVisible(isSelected)
                age.text = getString(R.string.selected_detail_age, selectedChart.age.toString())
                patrimony.text = getString(R.string.selected_detail_patrimony, selectedChart.value.toFinancialValue())
                savings.text = getString(R.string.selected_detail_savings, selectedChart.label, selectedChart.totalSavings.toFinancialValue())
                currentPassiveIncome.text = getString(R.string.selected_detail_yearly_passive_income, selectedChart.year.toString(), selectedChart.passiveIncome.toFinancialValue())
                totalPassiveIncome.text = getString(R.string.selected_detail_total_passive_income, sumPassiveIncome.toFinancialValue())
                patrimonyPercentageSavings.text = getString(R.string.selected_detail_patrimony_percentage_savings, (100* selectedChart.totalSavings / (selectedChart.value - chartViewModel.initialPatrimony)).roundFigures(1)+"%")
                patrimonyPercentagePassiveIncome.text = getString(R.string.selected_detail_patrimony_percentage_passive_income, (100 * sumPassiveIncome / (selectedChart.value - chartViewModel.initialPatrimony)).roundFigures(1)+"%")
                monthlyPassiveIncome.text = getString(R.string.selected_detail_monthly_passive_income, (selectedChart.passiveIncome / 12.0).toFinancialValue())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadChartData()

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        val rate = (chartViewModel.rate * 100).toInt()
        binding.seekBarRate.setupComponent(rate, RATE_PROGRESS_INCREMENT, "", PERCENTAGE, EXPECTED_RATE, MAX_RATE){
            chartViewModel.rate = it / 100.0
            loadChartData()
        }

        binding.seekBarMonth.setupComponent(chartViewModel.monthSavings.toInt(), SAVINGS_INCREMENT, CURRENCY, "", EXPECTED_MONTH, MAX_MONTHLY_SAVING){
            chartViewModel.monthSavings = it.toDouble()
            loadChartData()
        }

        binding.seekBarUnique.setupComponent(chartViewModel.uniqueSavings.toInt(), SAVINGS_INCREMENT, CURRENCY, "", EXPECTED_UNIQUE, MAX_UNIQUE_SAVING){
            chartViewModel.uniqueSavings = it.toDouble()
            loadChartData()
        }

        binding.portfolioGraph.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = chartAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}