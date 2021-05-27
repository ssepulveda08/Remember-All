package com.ssepulveda.rememberall.ui.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.base.BaseDialog
import com.ssepulveda.rememberall.databinding.DialogEditItemBinding
import com.ssepulveda.rememberall.db.entity.ItemList
import com.ssepulveda.rememberall.ui.adapters.ColorAdapter

class EditItemDialog(private val itemList: ItemList, val edit: (list: ItemList) -> Unit) :
    BaseDialog<DialogEditItemBinding>() {

    private lateinit var colorAdapter: ColorAdapter

    override fun initDialog(dialog: Dialog, binding: DialogEditItemBinding) {
        super.initDialog(dialog, binding)
        addListener(binding)
        setupView(binding)
    }

    private fun setupView(binding: DialogEditItemBinding) {
        binding.textName.setText(itemList.text)
    }

    private fun addListener(binding: DialogEditItemBinding) {
        binding.done.setOnClickListener {
            if (binding.textName.text.isNotBlank()) {
                itemList.text = binding.textName.text.toString()
                edit(itemList)
                dismiss()
            }
        }

        binding.Cancel.setOnClickListener {
            dismiss()
        }
    }

    override fun getLayout(): Int = R.layout.dialog_edit_item

    override val bindingInflater: (LayoutInflater) -> DialogEditItemBinding
        get() = DialogEditItemBinding::inflate
}