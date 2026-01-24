package app.islammedia.halaqatalquran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import app.islammedia.halaqatalquran.adapters.HalaqatAdapter
import app.islammedia.halaqatalquran.database.MainDataBase
import app.islammedia.halaqatalquran.R

/**
 * A simple [Fragment] subclass.
 */
class HalaqatViewFragment : Fragment() {
    var db: MainDataBase? = null
    var fhv_progressbar: ProgressBar? = null
    var dbView: RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_halaqat_view, container, false)
        fhv_progressbar = v.findViewById(R.id.fhv_progressbar)
        fhv_progressbar.setVisibility(View.VISIBLE)
        val thread = Thread(connectDataBase)
        thread.start()
        dbView = v.findViewById(R.id.halaqatViewList)
        dbView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(v.context)
        dbView.setLayoutManager(layoutManager)
        return v
    }

    var connectDataBase: Runnable? = label@ Runnable {
        if (activity == null) return@label
        db = Room.databaseBuilder(activity, MainDataBase::class.java, "MainDataBase").build()
        val halaqat = db.myDAO().halaqat
        val hAdapter = HalaqatAdapter(halaqat, activity)
        activity.runOnUiThread(Runnable {
            dbView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            dbView.setAdapter(hAdapter)
            fhv_progressbar.setVisibility(View.GONE)
        })
    }
}