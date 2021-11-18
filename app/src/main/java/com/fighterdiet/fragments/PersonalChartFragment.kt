package com.fighterdiet.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.activities.FaqActivity
import com.fighterdiet.databinding.FragmentPersonalChartBinding
import com.fighterdiet.utils.Constants.HUNDRED
import com.fighterdiet.utils.Utils
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class PersonalChartFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentPersonalChartBinding
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val sheetView: View =
            layoutInflater.inflate(R.layout.fragment_personal_chart, container, false)
        binding = DataBindingUtil.bind(sheetView)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialise()

        var bundle:Bundle? = arguments
        if (bundle!=null){
            var from:String = bundle.getString("from")!!
            if (from.equals("Activity")){
                binding.ivBack.setOnClickListener(this)
            }
        }

        binding.ivQuestion.setOnClickListener {
            startActivity(FaqActivity.getStartIntent(activity!!))
        }
    }

    private fun initialise() {

        var goalPercentage: Int = Utils.getPercentage(80, 100)

        if (goalPercentage > HUNDRED) {
            goalPercentage = HUNDRED
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.progressBar.setProgress(goalPercentage, true)
        } else {
            binding.progressBar.progress = goalPercentage
        }

        getBarChartEntries()
        getLineChartEntries()
    }

    private fun getLineChartEntries() {

        binding.lineChart.getDescription().setEnabled(false);
        val lineValue = ArrayList<Entry>()
        lineValue.add(Entry(0f, 20f))
        lineValue.add(Entry(1f, 24f))
        lineValue.add(Entry(2f, 29f))
        lineValue.add(Entry(3f, 21f))
        lineValue.add(Entry(4f, 18f))
        lineValue.add(Entry(5f, 11f))
        lineValue.add(Entry(6f, 15f))

        var lineDataSet = LineDataSet(lineValue, "")
        lineDataSet.setDrawCircles(false)
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        var dataSet = ArrayList<ILineDataSet>()
        dataSet.add(lineDataSet)

        lineDataSet.setDrawFilled(true)
            // fill drawable only supported on api level 18 and above
            val drawable = ContextCompat.getDrawable(context!!, R.drawable.fade_green)
        lineDataSet.setFillDrawable(drawable)
        lineDataSet.setColors(ContextCompat.getColor(context!!,R.color.green))

        var data:LineData = LineData(dataSet)
        binding.lineChart.data = data
        binding.lineChart.invalidate()

        var xAxis:XAxis = binding.lineChart.xAxis
        binding.lineChart.xAxis.valueFormatter = MyAxisValiueFormatter()

        binding.lineChart.setDoubleTapToZoomEnabled(false)
        binding.lineChart.setDoubleTapToZoomEnabled(false)
        binding.lineChart.getLegend().setEnabled(false)

        binding.lineChart.getXAxis().setDrawGridLines(false);
//        binding.lineChart.getXAxis().setDrawLabels(false);
        binding.lineChart.getXAxis().setDrawAxisLine(false);

        binding.lineChart.getAxisRight().setDrawGridLines(false)
        binding.lineChart.getAxisRight().setDrawLabels(false)
        binding.lineChart.getAxisRight().setDrawAxisLine(false)
    }

    private fun getBarChartEntries() {

        val barvalues = ArrayList<BarEntry>()
        barvalues.add(BarEntry(1f, 8f))
        barvalues.add(BarEntry(2f, 2f))
        barvalues.add(BarEntry(3f, 3f))
        barvalues.add(BarEntry(4f, 4f))
        barvalues.add(BarEntry(5f, 6f))
        barvalues.add(BarEntry(6f, 7f))
        barvalues.add(BarEntry(7f, 1f))

        var barDataSet: BarDataSet = BarDataSet(barvalues, "")
        barDataSet.setDrawValues(false)
        var barData :BarData = BarData(barDataSet)
        barData.setValueFormatter(StackedValueFormatter(false, "", 1))
        barData.setBarWidth(0.5f);
        binding.barChart.data = barData

        barDataSet.setColors(
            getResources().getColor(R.color.blue_main),
            getResources().getColor(R.color.orange),
            getResources().getColor(R.color.skyblue),
            getResources().getColor(R.color.green),
            getResources().getColor(R.color.red)
        )

        binding.barChart.setFitBars(true)
        binding.barChart.getLegend().setWordWrapEnabled(true)
        binding.barChart.animateX(2500)

        binding.barChart.setExtraOffsets(0f, 0f, 0f, 5f)
        binding.barChart.invalidate()

        binding.barChart.setTouchEnabled(true);
        binding.barChart.setClickable(false);
        binding.barChart.setDoubleTapToZoomEnabled(false);
        binding.barChart.setDoubleTapToZoomEnabled(false);

        binding.barChart.setDrawBorders(false);
        binding.barChart.setDrawGridBackground(false);

        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.getLegend().setEnabled(false);

        binding.barChart.getAxisLeft().setDrawGridLines(false);
        binding.barChart.getAxisLeft().setDrawLabels(false);
        binding.barChart.getAxisLeft().setDrawAxisLine(false);

        binding.barChart.getXAxis().setDrawGridLines(false);
        binding.barChart.getXAxis().setDrawLabels(false);
        binding.barChart.getXAxis().setDrawAxisLine(false);

        binding.barChart.getAxisRight().setDrawGridLines(false);
        binding.barChart.getAxisRight().setDrawLabels(false);
        binding.barChart.getAxisRight().setDrawAxisLine(false);

    }

    private class  MyAxisValiueFormatter(): ValueFormatter() {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return "Days "+value
        }
    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_back -> {
                activity?.onBackPressed()
            }
        }
    }
}