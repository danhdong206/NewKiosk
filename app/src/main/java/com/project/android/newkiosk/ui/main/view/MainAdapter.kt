package com.project.android.newkiosk.ui.main.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.android.newkiosk.R
import com.project.android.newkiosk.data.model.App
import kotlinx.android.synthetic.main.apps_item.view.*

class MainAdapter(mContext: Context, private var mListApp: ArrayList<App>, private var listener: OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.apps_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mListApp.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val icon: Drawable? = mListApp[position].getAppIcon()
        val label: String? = mListApp[position].getAppName()

        viewHolder.mTextViewApps.text = label
        viewHolder.mImageViewApps.setImageDrawable(icon)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val mImageViewApps: ImageView = itemView.img_apps
        val mTextViewApps: TextView = itemView.txt_apps_name

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}