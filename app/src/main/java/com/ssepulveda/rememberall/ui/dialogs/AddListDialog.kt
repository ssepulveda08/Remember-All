package com.ssepulveda.rememberall.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.base.BaseDialog
import com.ssepulveda.rememberall.databinding.DialogAddListBinding
import com.ssepulveda.rememberall.db.entity.ListApp
import com.ssepulveda.rememberall.ui.adapters.ColorAdapter
import com.ssepulveda.rememberall.utils.getColorList

class AddListDialog(val action: (list: ListApp) -> Unit) : BaseDialog<DialogAddListBinding>() {

    private lateinit var colorAdapter: ColorAdapter

    private val listColor = getColorList()

    override fun initDialog(dialog: Dialog, binding: DialogAddListBinding) {
        super.initDialog(dialog, binding)
        addListener(binding)
        loadColors(listColor, binding)
    }

    private fun loadColors(colors: List<String>?, binding: DialogAddListBinding) {
        context?.let {
            colorAdapter = ColorAdapter(it, colors)
            setupRecyclerView(it, binding)
        }
    }

    private fun setupRecyclerView(context: Context, binding: DialogAddListBinding) {
        binding.RecyclerViewColors.apply {
            layoutManager = GridLayoutManager(
                context,
                colorAdapter.getSpanCount(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = colorAdapter
        }
    }

    private fun addListener(binding: DialogAddListBinding) {
        binding.btnDone.setOnClickListener {
            if (validateFields(binding)) {
                val name = binding.textInputEditTextName.text.toString()
                val listApp = ListApp(0, name, colorAdapter.getSelectColor())
                action(listApp)
                dismiss()
            }
        }

        binding.imageViewClose.setOnClickListener {
            dismiss()
        }
    }

    private fun validateFields(binding: DialogAddListBinding): Boolean {
        var state = false
        when {
            binding.textInputEditTextName.text.isNullOrBlank() -> Toast.makeText(
                context,
                "Nombre sin llenar",
                Toast.LENGTH_LONG
            ).show()
            colorAdapter.getSelectColor().isBlank() -> Toast.makeText(
                context,
                "Selecciona un Color",
                Toast.LENGTH_LONG
            ).show()
            else -> state = true
        }
        return state
    }

    override fun getLayout(): Int = R.layout.dialog_add_list

    override val bindingInflater: (LayoutInflater) -> DialogAddListBinding
        get() = DialogAddListBinding::inflate
}
