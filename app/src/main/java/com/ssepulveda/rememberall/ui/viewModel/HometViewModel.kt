package com.ssepulveda.rememberall.ui.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssepulveda.rememberall.db.AppRepository
import com.ssepulveda.rememberall.db.ProfileRepository
import com.ssepulveda.rememberall.db.SharedPreferenceRepository
import com.ssepulveda.rememberall.db.entity.ListApp
import com.ssepulveda.rememberall.db.entity.ListAppCount
import com.ssepulveda.rememberall.utils.BEEN_SHOWN_DIALOG
import com.ssepulveda.rememberall.utils.DARK_MODE
import com.ssepulveda.rememberall.utils.DOUBLE_COLUMN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository,
    private val profileRepository: ProfileRepository,
    private val preference: SharedPreferenceRepository,
) : ViewModel() {

    private val _showDialogSuggested = MutableLiveData(false)

    init {
        initShowSuggestedDialog()
    }

    fun getNameProfile() = profileRepository.getNameProfile()

    fun getListAppAndCount() = repository.getListAppAndCount()

    fun deleteList(list: ListAppCount) {
        viewModelScope.launch {
            repository.deleteList(list.id)
        }
    }

    fun setList(listApp: ListApp) {
        viewModelScope.launch {
            repository.setListApp(listApp)
        }
    }

    fun isDoubleColumn(): Boolean = preference.getBoolean(DOUBLE_COLUMN, true)

    fun isDarkMode(): Boolean = preference.getBoolean(DARK_MODE)

    fun initShowSuggestedDialog() {
        viewModelScope.launch {
            val state = !preference.getBoolean(BEEN_SHOWN_DIALOG) && repository.getCountList() == 0
            _showDialogSuggested.postValue(state)
        }
    }

    fun settingDarkMode(isDarMode: Boolean) {
        preference.setValue(DARK_MODE, isDarMode)
    }

    fun settingDoubleColumn(isDarMode: Boolean) {
        preference.setValue(DOUBLE_COLUMN, isDarMode)
    }

    fun setShowDialog() {
        preference.setValue(BEEN_SHOWN_DIALOG, true)
    }

    fun updateProfile(name: String) {
        viewModelScope.launch {
            profileRepository.updateNameProfile(name)
        }
    }

    fun showDialogSuggested(): LiveData<Boolean> = _showDialogSuggested
}