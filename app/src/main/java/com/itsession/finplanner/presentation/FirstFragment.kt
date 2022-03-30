package com.itsession.finplanner.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val chartDataMock = listOf(
            ChartData("03/2022", 1200000.0),
            ChartData("04/2022", 1214568.969),
            ChartData("05/2022", 1229254.112),
            ChartData("06/2022",1244056.35694235),
            ChartData("07/2022", 1258976.63703407),
            ChartData("08/2022", 1274015.89353449),
            ChartData("09/2022", 1289175.07517819),
            ChartData("10/2022", 1304455.13826511),
            ChartData("11/2022", 1319857.04672084),
            ChartData("12/2022", 1335381.77215747),
            ChartData("01/2023", 1351030.29393485),
            ChartData("02/2023", 1566803.59922239)
        )


        binding.portfolioGraph.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = ChartAdapter(chartViewModel.getChartData().filter { it.label.contains("12") })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}