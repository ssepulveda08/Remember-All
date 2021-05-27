package com.ssepulveda.rememberall.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssepulveda.rememberall.R

class ColorAdapter(private val context: Context, private val colors: List<String>?) :
    RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private var selectColor = ""

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_cirle_color, viewGroup, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return colors?.size!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, index: Int) {
        colors?.get(index)?.let {
            setView(viewHolder, it)
            addListener(viewHolder, index)
        }
    }

    private fun addListener(viewHolder: ColorAdapter.ViewHolder, index: Int) {
        viewHolder.relative.tag = index
        viewHolder.relative.setOnClickListener { view ->
            val indexTag: Int = view.tag as Int
            selectColor = colors?.get(indexTag) ?: ""
            notifyDataSetChanged()
        }
    }

    private fun setView(viewHolder: ViewHolder, hex: String) {
        if (hex == selectColor) {
            viewHolder.image.visibility = View.VISIBLE
        } else {
            viewHolder.image.visibility = View.GONE
        }
        val color = Color.parseColor("#$hex");
        val mDrawable = ContextCompat.getDrawable(context, R.drawable.circle_color)
        mDrawable!!.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            viewHolder.relative.setBackgroundDrawable(mDrawable)
        } else {
            viewHolder.relative.background = mDrawable
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relative: LinearLayout = itemView.findViewById(R.id.container) as LinearLayout
        var image: ImageView = itemView.findViewById(R.id.image_check) as ImageView
    }


    fun getSelectColor(): String = selectColor

    fun getSpanCount(): Int = 4
}