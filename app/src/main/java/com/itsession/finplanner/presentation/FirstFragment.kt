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

    @SuppressLint("NotifyDataSetChanged")
    fun loadChartData(){
        chartAdapter.setData(chartViewModel.getChartData().filter { it.label.contains("12") })
        chartAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadChartData()

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.seekbarRate.apply {
            progress = (chartViewModel.rate * 100).toInt()
            incrementProgressBy(1)
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    chartViewModel.rate = p1.toDouble() / 100.0
                    loadChartData()
                    val offsetX = (p1 * (binding.seekbarRate.width - (binding.seekbarRate.thumbOffset.toFloat() * 2.0)) / binding.seekbarRate.max).toFloat()
                    binding.seekbarRateText.text = "$progress%"
                    binding.seekbarRateText.x = (binding.seekbarRate.x +offsetX + binding.seekbarRate.thumbOffset.toFloat() / 2.0f)
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}

            })
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