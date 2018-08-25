package ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ConfirmDialog: DialogFragment() {
    val title = "確認"
    val msg = "情報を保存せずにメインメニューへ戻りますか？"
    val okText = "保存せずに戻る"
    val cancelText = "キャンセル"
    var onClickListener: DialogInterface.OnClickListener? = DialogInterface.OnClickListener{
        _, _ ->
    }
    var onCancelClickListener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(okText,onClickListener)
                .setNegativeButton(cancelText,onCancelClickListener)
        return builder.create()
    }
}