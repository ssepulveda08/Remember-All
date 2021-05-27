package com.ssepulveda.rememberall.ui.items

import android.view.View
import androidx.core.view.isVisible
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.databinding.ItemCheckOptionBinding
import com.xwray.groupie.viewbinding.BindableItem


class CheckOptionItem(
    private val option: String,
    private val isCheck: Boolean = false,
    private val showCheck: Boolean = true,
    private val onCheck: (Boolean) -> Unit = {},
    private val onclick: () -> Unit = {},
) : BindableItem<ItemCheckOptionBinding>() {

    override fun bind(viewBinding: ItemCheckOptionBinding, position: Int) {
        viewBinding.textViewOption.text = option
        viewBinding.checkBox.isChecked = isCheck
        viewBinding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheck(isChecked)
        }
        viewBinding.checkBox.isVisible = showCheck
        viewBinding.root.setOnClickListener {
            onclick()
        }
    }

    override fun getLayout(): Int = R.layout.item_check_option

    override fun initializeViewBinding(view: View): ItemCheckOptionBinding {
        return ItemCheckOptionBinding.bind(view)
    }

}
