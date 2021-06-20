package com.ssepulveda.rememberall.ui.items

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.databinding.ItemListAppBinding
import com.ssepulveda.rememberall.db.entity.CounterList
import com.ssepulveda.rememberall.ui.ModelsViews.ListAppModel
import com.ssepulveda.rememberall.utils.changeColorDrawable
import com.ssepulveda.rememberall.utils.ucFirst
import com.xwray.groupie.viewbinding.BindableItem

class ListAppItem(
    private val context: Context,
    private val list: ListAppModel,
    private val listener: ListenerItem
) : BindableItem<ItemListAppBinding>() {

    override fun bind(viewBinding: ItemListAppBinding, position: Int) {
        setupView(viewBinding)
    }

    override fun getLayout(): Int = R.layout.item_list_app

    override fun initializeViewBinding(view: View): ItemListAppBinding {
        return ItemListAppBinding.bind(view)
    }

    private fun setupView(binding: ItemListAppBinding) {
        val count = list.model.count
        val drawable = ContextCompat.getDrawable(context, R.drawable.background_item_list)
        binding.name.text = ucFirst(list.model.name)
        binding.count.text = "$count ${context.getString(R.string.copy_items)}"
        changeColorDrawable(drawable, list.model.color)?.let { binding.container.background = it }
        addListener(binding)
        showDeleteItem(binding, list.showDelete)
    }

    /**
     * Listener Action
     * */

    private fun addListener(binding: ItemListAppBinding) {
        binding.close.setOnClickListener {
            list.showDelete = false
            showDeleteItem(binding, false)
        }
        binding.root.setOnLongClickListener {
            list.showDelete = true
            showDeleteItem(binding, true)
            true
        }
        binding.container.setOnClickListener { listener.onOpenDetailItem(list.model) }
        binding.delete.setOnClickListener { listener.onDeleteItem(list.model) }
    }

    private fun showDeleteItem(binding: ItemListAppBinding, showDelete: Boolean) {
        binding.name.isVisible = !showDelete
        binding.count.isVisible = !showDelete

        binding.close.isVisible = showDelete
        binding.delete.isVisible = showDelete
    }

    interface ListenerItem {
        fun onOpenDetailItem(item: CounterList)
        fun onDeleteItem(item: CounterList)
    }
}
