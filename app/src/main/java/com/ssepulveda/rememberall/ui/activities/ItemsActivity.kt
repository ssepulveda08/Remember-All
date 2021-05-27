package com.ssepulveda.rememberall.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.base.BaseActivity
import com.ssepulveda.rememberall.databinding.ActivityItemsBinding
import com.ssepulveda.rememberall.db.entity.ItemList
import com.ssepulveda.rememberall.db.entity.ListApp
import com.ssepulveda.rememberall.ui.dialogs.AddItemListDialog
import com.ssepulveda.rememberall.ui.items.TopListItem
import com.ssepulveda.rememberall.ui.viewModel.ItemsViewModel
import com.ssepulveda.rememberall.utils.getHexToColor
import com.ssepulveda.rememberall.utils.ucFirst
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint

const val KEY_ID_LIST = "key_id_lis"

@AndroidEntryPoint
class ItemsActivity : BaseActivity() {

    private val viewModel: ItemsViewModel by viewModels()

    private val groupAdapter = GroupieAdapter()

    private lateinit var binding: ActivityItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        intent.extras?.let {
            initViewModel(it.getLong(KEY_ID_LIST, 0))
        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initListener()
        binding.RecyclerViewItems.adapter = groupAdapter
    }

    private fun initViewModel(id: Long) {
        viewModel.initData(id)
        viewModel.getItemsByList().observe(this, { it?.let { items -> loadList(items) } })
        viewModel.getDataList().observe(this, ::initToolbar)
    }

    private fun showOptionItem(show: Boolean) {
        binding.constraintLayoutOption.isVisible = show
    }

    private fun initListener() {
        binding.fab.setOnClickListener {
            showDialogAdd()
        }
        binding.imageViewDelete.setOnClickListener {
            showDialogConfirmationDelete()
        }
        binding.imageViewEdit.setOnClickListener {
            showDialogAdd(true)
            showOptionItem(false)
        }
    }

    private fun showDialogConfirmationDelete() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.title_delete_list)
            .setMessage(R.string.message_delete_list)
            .setPositiveButton(R.string.done) { dialog, _ ->
                viewModel.deleteItem()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun initToolbar(listApp: ListApp) {
        if (!listApp.color.isNullOrBlank()) {
            val color = getHexToColor(listApp.color)
            binding.toolbarLayout.setContentScrimColor(color)
            binding.toolbarLayout.setBackgroundColor(color)
        }
        title = ucFirst(listApp.name)
    }

    private fun showDialogAdd(edit: Boolean = false) {
        val dialog = AddItemListDialog() { text ->
            if(edit){
                viewModel.updateItem(text)
            }else {
                viewModel.setItemList(text)
            }
        }.apply {
            if (edit) {
                viewModel.getCurrentItemList().value?.let {
                    item = it
                }
            }
        }
        showDialog(dialog, "SHOW_DIALOG_ITEM")
    }

    private fun loadList(items: List<ItemList>) {
        val section = Section()
        items.forEach { item ->
            section.add(
                TopListItem(item).apply {
                    onLongListener = {
                        viewModel.configIdItemEdit(it)
                        showOptionItem(true)
                    }
                    onCloseEditView = {
                        viewModel.configIdItemEdit()
                        showOptionItem(false)
                    }
                }
            )
        }
        groupAdapter.clear()
        groupAdapter.add(section)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /* private fun startEditItem(item: ItemList) {
         viewModel.updateItem(item)
     }*/
}
