package com.ssepulveda.rememberall.ui.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import android.widget.Toast
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.base.BaseDialog
import com.ssepulveda.rememberall.databinding.DialogAddItemListBinding
import com.ssepulveda.rememberall.db.entity.ItemList

class AddItemListDialog(val action: (String) -> Unit) :
    BaseDialog<DialogAddItemListBinding>() {

    var item: ItemList? = null

    override fun initDialog(dialog: Dialog, binding: DialogAddItemListBinding) {
        super.initDialog(dialog, binding)
        addListener(binding)
    }

    private fun addListener(binding: DialogAddItemListBinding) {
        binding.btnDone.setOnClickListener {
            if (validateFields(binding)) {
                action(binding.textInputEditTextText.text.toString())
                dismiss()
            }
        }
        binding.imageViewClose.setOnClickListener {
            dismiss()
        }

        item?.let {
            binding.btnDone.text = context?.getText(R.string.copy_save)
            binding.textInputEditTextText.setText(it.text)
        }
    }

    private fun validateFields(binding: DialogAddItemListBinding): Boolean {
        var state = false
        when {
            binding.textInputEditTextText.text.isNullOrBlank() -> Toast.makeText(
                context,
                "texto sin llenar",
                Toast.LENGTH_LONG
            ).show()
            else -> state = true
        }
        return state
    }

    override fun getLayout(): Int = R.layout.dialog_add_item_list

    override val bindingInflater: (LayoutInflater) -> DialogAddItemListBinding
        get() = DialogAddItemListBinding::inflate
}
