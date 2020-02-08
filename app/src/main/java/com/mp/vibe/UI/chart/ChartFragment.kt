package com.mp.vibe.UI.chart

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.mp.vibe.Data.model.ChartReportItem
import com.mp.vibe.Data.repository.WebRepository
import com.mp.vibe.R
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ChartFragment : Fragment() {

    private lateinit var chartViewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chartViewModel =
            ViewModelProviders.of(this).get(ChartViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chart, container, false)

        // Initialize spotify repo
        var webRepo =
            WebRepository()

        // Response
        val reqResponse = object : SingleObserver<Response<List<ChartReportItem>>> {
            @RequiresApi(Build.VERSION_CODES.O)
            @SuppressLint("CheckResult")
            override fun onSuccess(resp: Response<List<ChartReportItem>>) {
                if (resp.isSuccessful) {
                    Log.i("Report resp succes", resp.message())

                    chartViewModel.barChart = root.findViewById(R.id.barChart)
                    var barEntries: ArrayList<BarEntry> = arrayListOf()

                    for(cri: ChartReportItem in resp.body()!!) {
                        barEntries.add(BarEntry(cri.an_aparitie, cri.nr_piese))
                    }

                    var barDataSet = BarDataSet(barEntries, "Growth")
                    barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

                    var barData = BarData(barDataSet)
                    barData.barWidth = 2f

                    chartViewModel.barChart.animateY(5000)
                    chartViewModel.barChart.data = barData
                    chartViewModel.barChart.setFitBars(true)

                    var description = Description()
                    description.text = "Tracks report"
                    chartViewModel.barChart.description = description
                    chartViewModel.barChart.invalidate()

                } else {
                    Log.e("Report resp error", resp.toString())
                }
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                Log.e("Spotify request error", e.toString())

            }
        }

        webRepo.getChartReport()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(reqResponse)


        return root

    }
}