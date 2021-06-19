package com.ssepulveda.rememberall.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ssepulveda.rememberall.R
import com.ssepulveda.rememberall.base.BaseActivity
import com.ssepulveda.rememberall.databinding.ActivityMainBinding
import com.ssepulveda.rememberall.db.entity.CounterList
import com.ssepulveda.rememberall.db.entity.ListApp
import com.ssepulveda.rememberall.ui.ModelsViews.ListAppModel
import com.ssepulveda.rememberall.ui.ModelsViews.SuggestedItemModel
import com.ssepulveda.rememberall.ui.activities.models.StartActivityModel
import com.ssepulveda.rememberall.ui.dialogs.AddListDialog
import com.ssepulveda.rememberall.ui.dialogs.SuggestedDialog
import com.ssepulveda.rememberall.ui.items.CheckOptionItem
import com.ssepulveda.rememberall.ui.items.ListAppItem
import com.ssepulveda.rememberall.ui.items.SimpleTextItem
import com.ssepulveda.rememberall.ui.viewModel.HomeViewModel
import com.ssepulveda.rememberall.utils.BiometricAuthentication
import com.ssepulveda.rememberall.utils.BiometricCallback
import com.ssepulveda.rememberall.utils.getColorList
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import javax.crypto.Cipher

@AndroidEntryPoint
class HomeActivity : BaseActivity(), BiometricCallback, SuggestedDialog.SuggestedDialogListener,
    ListAppItem.ListenerItem  {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var dialogSuggested: SuggestedDialog

    private val colorList = getColorList()

    private lateinit var binding: ActivityMainBinding

    private val groupAdapter = GroupieAdapter()

    private val adapterMenu = GroupieAdapter()

    private lateinit var biometricAuthentication: BiometricAuthentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialogSuggested = SuggestedDialog(this)
        biometricAuthentication = BiometricAuthentication(this, lifecycle, this)
        initViewModel()
        setupView()
        initListener()
        initMenu()
    }

    private fun initMenu() {
        binding.containerMenu.recyclerViewOptionsMenu.apply {
            layoutManager =
                GridLayoutManager(baseContext, adapterMenu.spanCount, RecyclerView.VERTICAL, false)
            adapter = adapterMenu
        }
        loadOptionMenu()
    }

    private fun loadOptionMenu() {
        adapterMenu.clear()
        ExpandableGroup(SimpleTextItem(baseContext.getString(R.string.setting)), true).apply {
            add(getItemDoubleColumn())
            add(getItemDarkMode())
            add(getItemBiometric())
            adapterMenu.add(this)
        }
        ExpandableGroup(SimpleTextItem(baseContext.getString(R.string.more_options)), true).apply {
            add(getItemGitHub())
            add(getItemAbout())
            adapterMenu.add(this)
        }
    }

    private fun getItemDoubleColumn(): Group = Section(
        CheckOptionItem(
            baseContext.getString(R.string.double_column),
            isCheck = viewModel.isDoubleColumn(),
            onCheck = ::configDoubleColumn
        )
    )

    private fun getItemDarkMode(): Group = Section(
        CheckOptionItem(
            baseContext.getString(R.string.dark_mode),
            isCheck = viewModel.isDarkMode(),
            onCheck = ::configDarkMode
        )
    )

    private fun getItemBiometric(): Group = Section(
        CheckOptionItem(
            baseContext.getString(R.string.enter_with_footprint),
            viewModel.hasEnabledBiometricsStart(),
            onCheck = ::configBiometric
        )
    )

    private fun getItemAbout(): Group = Section(
        CheckOptionItem(
            baseContext.getString(R.string.about),
            showCheck = false
        ) {
            openUrl(getString(R.string.linkedin_url))
        }
    )

    private fun getItemGitHub(): Group = Section(
        CheckOptionItem(
            baseContext.getString(R.string.github),
            showCheck = false
        ) {
            openUrl(getString(R.string.github_url))
        }
    )

    private fun configDoubleColumn(state: Boolean) {
        viewModel.settingDoubleColumn(state)
        setupRecyclerViewList()
    }

    private fun settingSpanCountRecycler() {
        groupAdapter.spanCount = if (viewModel.isDoubleColumn()) 2 else 1
    }

    private fun configDarkMode(isDarMode: Boolean) {
        viewModel.settingDarkMode(isDarMode)
        if (isDarMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun configBiometric(onBiometric: Boolean) {
        if (onBiometric) {
            viewModel.showBiometricPromptForEncryption()
        } else {
            viewModel.clearBiometric()
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun initListener() {
        binding.containerHomeList.containerHeader.imageViewSettings.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }
        binding.containerMenu.headerMenu.imageViewClose.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }
        binding.containerMenu.headerMenu.imageViewEditProfile.setOnClickListener {
            binding.containerMenu.headerMenu.textInputEditTextName.isEnabled = true
            handleVisibilityProfileOptions(false)
        }
        binding.containerMenu.headerMenu.imageViewSave.setOnClickListener {
            binding.containerMenu.headerMenu.textInputEditTextName.isEnabled = false
            handleVisibilityProfileOptions(true)
            val name = binding.containerMenu.headerMenu.textInputEditTextName.text.toString()
            viewModel.updateProfile(name)
        }
    }

    private fun handleVisibilityProfileOptions(showEdit: Boolean) {
        binding.containerMenu.headerMenu.imageViewEditProfile.isVisible = showEdit
        binding.containerMenu.headerMenu.imageViewSave.isVisible = !showEdit
    }

    private fun initViewModel() {
        viewModel.apply {
            onCounterList().observe(this@HomeActivity, ::loadList)
            onNameProfile().observe(this@HomeActivity, ::loadNameProfile)
            showDialogSuggested().observe(this@HomeActivity, ::showSuggestedDialog)
            showBiometric().observe(this@HomeActivity, ::showBiometricPrompt)
        }
    }

    private fun loadNameProfile(name: String?) {
        if (!name.isNullOrBlank()) {
            binding.containerHomeList.containerHeader.textViewName.text =
                baseContext.getString(R.string.copy_hello, name)
            binding.containerMenu.headerMenu.textInputEditTextName.setText(name)
        }
    }

    private fun setupView() {
        binding.containerHomeList.floatingActionButton.setOnClickListener {
            val dialog = AddListDialog { addListApp(it) }
            showDialog(dialog, "TAG_DIALOG_ADD_LIST")
        }
        setupRecyclerViewList()
    }

    private fun setupRecyclerViewList() {
        settingSpanCountRecycler()
        binding.containerHomeList.listRecyclerView.apply {
            layoutManager =
                GridLayoutManager(baseContext, groupAdapter.spanCount, RecyclerView.VERTICAL, false)
            adapter = groupAdapter
        }
        groupAdapter.notifyDataSetChanged()
    }

    private fun addListApp(listApp: ListApp) {
        viewModel.setList(listApp)
    }

    private fun loadList(counterListApp: List<CounterList>) {
        if (counterListApp.isNotEmpty()) viewModel.initShowSuggestedDialog()
        val section = Section()
        counterListApp.forEach { item ->
            section.add(ListAppItem(baseContext, ListAppModel(item), this))
        }
        groupAdapter.clear()
        groupAdapter.add(section)
    }

    private fun showSuggestedDialog(show: Boolean) {
        if (show) {
            showDialog(dialogSuggested, "SHOW_DIALOG_SUGGESTED")
        }
    }

    private fun saveViewList() {
        viewModel.setShowDialog()
        setupRecyclerViewList()
    }

    override fun onSaveData(list: List<SuggestedItemModel>) {
        list.forEach {
            viewModel.setList(ListApp(0, it.text, colorList.random()))
        }
        saveViewList()
        dialogSuggested.dismiss()
    }

    override fun onOpenDetailItem(item: CounterList) {
        val bundle = Bundle().apply {
            putLong(KEY_ID_LIST, item.id)
        }
        startActivity(StartActivityModel(ItemsActivity::class.java, bundle))
    }

    override fun onDeleteItem(item: CounterList) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.title_delete_list)
            .setMessage(R.string.message_delete_list)
            .setPositiveButton(R.string.done) { dialog, _ ->
                viewModel.deleteList(item)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showBiometricPrompt(cipher: Cipher) {
        biometricAuthentication.createPrompt(cipher)
    }

    @SuppressLint("RtlHardcoded")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
        }
    }

    override fun biometricError(throwable: Throwable) {

    }

    override fun biometricSuccess(result: BiometricPrompt.AuthenticationResult) {
        viewModel.encryptAndStoreServerToken(result)
    }
}
