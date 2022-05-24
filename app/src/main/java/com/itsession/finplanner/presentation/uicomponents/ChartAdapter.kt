package com.itsession.finplanner.presentation.uicomponents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itsession.finplanner.databinding.GraphCellBinding
import domain.ChartData

class ChartAdapter() : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {

    inner class ChartViewHolder(
        private val binding: GraphCellBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bindView(data : ChartData, maxValue : Double, position: Int){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val view = GraphCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
        return ChartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }
}