package com.ssepulveda.rememberall.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.ssepulveda.rememberall.R

abstract class BaseDialog<T : ViewBinding> : DialogFragment() {

    private lateinit var binding: T

    protected abstract val bindingInflater: (LayoutInflater) -> T

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = bindingInflater.invoke(LayoutInflater.from(context))
        val dialog: Dialog? = getDialogAlreadySetup()
        dialog?.let {
            initDialog(it, binding)
            setupWindow(it)
            return it
        } ?: return super.onCreateDialog(savedInstanceState)
    }

    protected open fun setupWindow(dialog: Dialog) {
        val window = dialog.window
        if (window != null) {
            context?.let {
                window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    }

    internal abstract fun getLayout(): Int

    protected open fun getDialogAlreadySetup(): Dialog? {
        val dialog = Dialog(requireContext(), R.style.CustomMaterialDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    protected open fun initDialog(dialog: Dialog, binding: T) {
        if (this::binding.isInitialized) {
            val view = this.binding.root
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.setContentView(view)
        }
    }
}