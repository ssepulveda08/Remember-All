package com.ssepulveda.rememberall.ui.items

import android.view.View
import androidx.core.view.isVisible
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.databinding.TopListItemBinding
import com.ssepulveda.rememberall.db.entity.ItemList
import com.xwray.groupie.viewbinding.BindableItem

class TopListItem(private val itemList: ItemList) : BindableItem<TopListItemBinding>() {

    var onLongListener: (ItemList) -> Unit = {}

    var onCloseEditView: () -> Unit = {}

    override fun bind(viewBinding: TopListItemBinding, position: Int) {
        setupView(viewBinding, position)
    }

    private fun setupView(binding: TopListItemBinding, position: Int) {
        val index = position + 1
        binding.count.text = "$index."
        binding.name.text = itemList.text
        binding.root.setOnLongClickListener {
            onLongListener(itemList)
            showDeleteView(binding, true)
            true
        }
        binding.imageViewClose.setOnClickListener {
            onCloseEditView()
            showDeleteView(binding, false)
        }
        showDeleteView(binding, false)
    }

    private fun showDeleteView(binding: TopListItemBinding, showDelete: Boolean) {
        binding.name.visibility = if (!showDelete) View.VISIBLE else View.INVISIBLE
        binding.count.visibility = if (!showDelete) View.VISIBLE else View.INVISIBLE

        binding.imageViewClose.isVisible = showDelete
    }

    override fun getLayout(): Int = R.layout.top_list_item

    override fun initializeViewBinding(view: View): TopListItemBinding =
        TopListItemBinding.bind(view)
}
