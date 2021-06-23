package com.ssepulveda.rememberall.ui.items

import android.view.View
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.databinding.ItemSimpleTextBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

class SimpleTextItem(val name: String) : BindableItem<ItemSimpleTextBinding>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(viewBinding: ItemSimpleTextBinding, position: Int) {
        viewBinding.textViewText.text = name
    }

    override fun getLayout(): Int = R.layout.item_simple_text

    override fun initializeViewBinding(view: View): ItemSimpleTextBinding {
        return ItemSimpleTextBinding.bind(view)
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }
}
