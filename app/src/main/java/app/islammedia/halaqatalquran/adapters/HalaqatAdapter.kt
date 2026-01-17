package app.islammedia.halaqatalquran.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import app.islammedia.halaqatalquran.database.entities.Halaqa
import app.islammedia.halaqatalquran.database.MainDataBase
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.halaqa.HalaqaActivity

class HalaqatAdapter(private val halaqat: MutableList<Halaqa>, private val a: Activity) : RecyclerView.Adapter<HalaqatAdapter.HViewHolder>() {
    var db: MainDataBase = Room.databaseBuilder(a, MainDataBase::class.java, "MainDataBase").build()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_halaqat, parent, false)
        return HViewHolder(v)
    }

    override fun onBindViewHolder(holder: HViewHolder, position: Int) {
        val halaqaName = holder.hText
        halaqaName.text = halaqat[position].name
        val deleteHalaqa = holder.deleteHalaqa
        val editHalaqa = holder.editHalaqa
        halaqaName.setOnClickListener {
            val i = Intent(a.applicationContext, HalaqaActivity::class.java)
            i.putExtra("halaqaID", halaqat[position].halaqaID)
            a.startActivity(i)
        }
        editHalaqa.setOnClickListener {
            val i = Intent(a.applicationContext, HalaqaActivity::class.java)
            i.putExtra("halaqaID", halaqat[position].halaqaID)
            i.putExtra("MODE", 2)
            a.startActivity(i)
        }
        deleteHalaqa.setOnClickListener { view: View ->
            AlertDialog.Builder(a)
                    .setTitle(R.string.confirmDeleteHalaqa)
                    .setPositiveButton(R.string.yes) { _: DialogInterface?, _: Int ->
                        view.visibility = View.GONE
                        val r = Runnable {
                            db.myDAO().deleteHalaqa(halaqat[position].halaqaID)
                            halaqat.removeAt(holder.adapterPosition)
                            notifyItemRemoved(holder.adapterPosition)
                            notifyDataSetChanged()
                            a.runOnUiThread { view.visibility = View.VISIBLE }
                        }
                        Thread(r).start()
                    }.setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }.show()
        }
    }

    override fun getItemCount(): Int {
        return halaqat.size
    }

    class HViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var hText: TextView = v.findViewById(R.id.halaqaNameTV)
        var editHalaqa: ImageButton = v.findViewById(R.id.editThisHalaqa)
        var deleteHalaqa: ImageButton = v.findViewById(R.id.deleteThisHalaqa)

    }

}