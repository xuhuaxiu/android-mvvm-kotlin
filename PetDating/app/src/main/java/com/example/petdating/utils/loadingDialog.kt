package com.example.petdating.utils

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.petdating.R

/**
 *created by xiuer on
 *remark: 为什么用 DialogFragment？
 * 优点	说明
 * 生命周期安全	不持有 Activity 的强引用，系统自动管理
 * 避免内存泄漏	不会因为外部持有 Context 导致泄漏
 * 支持配置更复杂的 UI	使用自定义布局更灵活
 * 易复用	可以通过 tag 管理弹窗唯一性，防止重复显示
 **/
class LoadingDialog  : DialogFragment() {

    companion object {
        private const val TAG = "LoadingDialog"

        fun show(manager: FragmentManager, message: String = "加载中...") {
            if (manager.isDestroyed) return
            if (manager.findFragmentByTag(TAG) == null) {
                newInstance(message).show(manager, TAG)
            }
        }

        fun dismiss(manager: FragmentManager) {
            (manager.findFragmentByTag(TAG) as? DialogFragment)?.dismiss()
        }

        private fun newInstance(message: String): LoadingDialog {
            return LoadingDialog().apply {
                arguments = Bundle().apply { putString("message", message) }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val loadingDialog = MaterialDialog(requireContext())
            .cancelable(true)
            .cancelOnTouchOutside(false)
            .cornerRadius(12f)
            .customView(R.layout.layout_custom_progress_dialog_view)
            .lifecycleOwner(this)
        val message = arguments?.getString("message") ?: "加载中..."
        loadingDialog.getCustomView().run {
            this.findViewById<TextView>(R.id.loading_tips).text = message
            this.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        }
        return loadingDialog
    }

    override fun onDestroyView() {
        (dialog as? MaterialDialog)?.dismiss()
        super.onDestroyView()
    }
}
