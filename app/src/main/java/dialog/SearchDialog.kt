package dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

class SearchDialog: DialogFragment() {
    val title = "検索"
    private val okText = "OK"
    private val cancelText = "キャンセル"
    private val customView = null
    var onClickListener: DialogInterface.OnClickListener? = DialogInterface.OnClickListener {
        dialogInterface, i ->
    }
    var onCancelClickListener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
                .setMessage("")
//                .setView(customView)
                .setPositiveButton(okText,onClickListener)
                .setNegativeButton(cancelText,onCancelClickListener)
        return builder.create()
    }
}