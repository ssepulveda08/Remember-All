package com.ssepulveda.rememberall.ui.items

import android.view.View
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.databinding.ItemSuggestedBinding
import com.ssepulveda.rememberall.ui.ModelsViews.SuggestedItemModel
import com.xwray.groupie.viewbinding.BindableItem

class SuggestedItem(private val data: SuggestedItemModel, val action: (SuggestedItemModel) -> Unit) : BindableItem<ItemSuggestedBinding>()  {


    override fun bind(viewBinding: ItemSuggestedBinding, position: Int) {
        setupView(viewBinding)
    }

    private fun setupView(binding: ItemSuggestedBinding) {
        binding.checkedSuggested.text = data.text
        binding.checkedSuggested.isChecked = data.check
        binding.checkedSuggested.setOnCheckedChangeListener { _, state ->
            data.check = state
            action(data)
        }
    }

    override fun getLayout(): Int = R.layout.item_suggested

    override fun initializeViewBinding(view: View): ItemSuggestedBinding = ItemSuggestedBinding.bind(view)

}