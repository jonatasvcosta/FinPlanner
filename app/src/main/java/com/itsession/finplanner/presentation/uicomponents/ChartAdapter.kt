package com.itsession.finplanner.presentation.uicomponents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itsession.finplanner.databinding.GraphCellBinding
import domain.ChartData

class ChartAdapter() : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {
    var chartData : List<ChartData> = listOf()
    var currentlySelected = 0
    var isSelected = false
    var onPeriodSelectedListener : ((Boolean, Int)->Unit)? = null
    private val EACH_BAR_DELAY  = 200L

    fun setData(data : List<ChartData>, selected : Int, onPeriodSelectedListener : (Boolean, Int)->Unit){
        chartData = data
        currentlySelected = selected
        chartData[currentlySelected].selected = true
        this.onPeriodSelectedListener = onPeriodSelectedListener
        notifyDataSetChanged()
    }

    inner class ChartViewHolder(
        private val binding: GraphCellBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bindView(data : ChartData, maxValue : Double, position: Int){
            val previousValue = if(position > 1) chartData[position - 1].value else 0.0
            binding.graphBarCell.setGraphBarValue(data.value, maxValue, previousValue, data.label, EACH_BAR_DELAY * position)
            binding.graphBarCell.alpha = if(data.selected) 1.0f else 0.5f
            if(data.hasBreakEven) binding.graphBarCell.setGraphBarHighlightColor()
            binding.graphBarCell.setOnClickListener {
                chartData[currentlySelected].selected = false
                notifyItemChanged(currentlySelected)
                isSelected = if(position == currentlySelected) !isSelected else true
                chartData[position].selected = isSelected
                onPeriodSelectedListener?.invoke(isSelected, position)
                currentlySelected = position
                notifyItemChanged(currentlySelected)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val view = GraphCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
        return ChartViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ChartViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        chartData.getOrNull(position)?.let {
            holder.bindView(it, chartData.maxOf { it.value }, position)
        }
    }

    override fun getItemCount(): Int {
        return chartData.size
    }
}