package com.ssepulveda.rememberall.base

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.ssepulveda.rememberall.ui.activities.models.StartActivityModel

open class BaseActivity : AppCompatActivity() {
    fun showDialog(dialog: DialogFragment, tag: String?): Boolean {
        var wasShown = false
        val fragment = supportFragmentManager.findFragmentByTag(dialog.javaClass.name)
        if (!isFinishing && (fragment == null || !fragment.isAdded)) {
            dialog.show(
                supportFragmentManager,
                tag ?: dialog.javaClass.name
            )
            wasShown = true
        }
        return wasShown
    }

    fun startActivity(startActivityModel: StartActivityModel) {
        val intent = Intent(baseContext, startActivityModel.activity)
        startActivityModel.bundle?.let {
            intent.putExtras(it)
        }
        intent.flags = startActivityModel.flags
        startActivityForResult(intent, startActivityModel.code)
    }

    fun openUrl(webUrl: String) {
        val uri: Uri = Uri.parse(webUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}