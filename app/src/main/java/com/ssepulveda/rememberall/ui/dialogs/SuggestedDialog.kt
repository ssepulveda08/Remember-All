package com.ssepulveda.rememberall.ui.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.base.BaseDialog
import com.ssepulveda.rememberall.databinding.DialogSuggestedBinding
import com.ssepulveda.rememberall.ui.ModelsViews.SuggestedItemModel
import com.ssepulveda.rememberall.ui.items.SuggestedItem
import com.ssepulveda.rememberall.ui.viewModel.HomeViewModel
import com.ssepulveda.rememberall.utils.getSuggestedList
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

class SuggestedDialog(private val listener: SuggestedDialogListener) :
    BaseDialog<DialogSuggestedBinding>() {

    private var suggested: List<String> = emptyList()

    private val adapter = GroupieAdapter()

    private var listSuggestedItemModel: List<SuggestedItemModel> = emptyList()

    private lateinit var viewModel: HomeViewModel

    override fun initDialog(dialog: Dialog, binding: DialogSuggestedBinding) {
        super.initDialog(dialog, binding)
        suggested = getSuggestedList(context)
        setupView(binding)
    }

    private fun setupView(binding: DialogSuggestedBinding) {
        binding.recyclerViewSuggested.adapter = adapter
        val section = Section()
        listSuggestedItemModel = suggested.map {
            SuggestedItemModel(
                it
            )
        }
        listSuggestedItemModel.forEach { item ->
            section.add(SuggestedItem(item) { onCheck(it) })
        }
        adapter.clear()
        adapter.add(section)
        addListener(binding)
    }

    private fun onCheck(item: SuggestedItemModel) {
        listSuggestedItemModel.forEach {
            if (it.text.equals(item.text, true))
                it.check = item.check
        }
    }

    private fun addListener(binding: DialogSuggestedBinding) {
        binding.btnDone.setOnClickListener {
            val list = listSuggestedItemModel.filter { it.check }
            listener.onSaveData(list)
        }
        binding.imageViewClose.setOnClickListener {
            dismiss()
        }
    }

    override fun getLayout(): Int = R.layout.dialog_suggested

    interface SuggestedDialogListener {
        fun onSaveData(list: List<SuggestedItemModel>)
    }

    override val bindingInflater: (LayoutInflater) -> DialogSuggestedBinding
        get() = DialogSuggestedBinding::inflate
}
