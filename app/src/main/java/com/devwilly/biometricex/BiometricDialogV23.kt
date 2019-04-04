package com.devwilly.biometricex

import android.content.Context
import android.content.DialogInterface
import android.support.annotation.NonNull
import android.support.annotation.StyleRes
import android.support.design.widget.BottomSheetDialog
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Willy on 2019/4/4.
 */
class BiometricDialogV23(@NonNull context: Context, @NonNull private val callback: BiometricCallback) :
        BottomSheetDialog(context, R.style.BottomSheetDialogTheme) {

    private lateinit var btnCancel: Button
    private lateinit var imgLogo: ImageView
    private lateinit var itemTitle: TextView
    private lateinit var itemDescription: TextView
    private lateinit var itemSubtitle: TextView
    private lateinit var itemStatus: TextView


    init {
        setDialogView()
    }

    private fun setDialogView() {
        val bottomSheetView = layoutInflater.inflate(R.layout.view_bottom_sheet, null)
        setContentView(bottomSheetView)

        btnCancel = bottomSheetView.findViewById(R.id.btn_cancel)
        imgLogo = bottomSheetView.findViewById(R.id.img_logo)
        itemTitle = bottomSheetView.findViewById(R.id.item_title)
        itemDescription = bottomSheetView.findViewById(R.id.item_description)
        itemSubtitle = bottomSheetView.findViewById(R.id.item_subtitle)
        itemStatus = bottomSheetView.findViewById(R.id.item_status)

        val drawable = context.packageManager.getApplicationIcon(context.packageName)
        imgLogo.setImageDrawable(drawable)

        btnCancel.setOnClickListener {
            dismiss()
            callback.onAuthCancelled()
        }
    }

    fun setTitle(title: String) {
        itemTitle.text = title
    }

    fun setSubtitle(subTitle: String) {
        itemStatus.text = subTitle
    }

    fun setDescription(description: String) {
        itemDescription.text = description
    }

    fun setNegativeButtonText(negativeButtonText: String) {
        btnCancel.text = negativeButtonText
    }

    fun updateStatus(status: String) {
        itemStatus.text = status
    }
}