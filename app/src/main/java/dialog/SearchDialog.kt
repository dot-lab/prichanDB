package dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.custom.view.*
import org.jetbrains.anko.toast
import xyz.dot_lab.prichandb.MainActivity
import xyz.dot_lab.prichandb.R

class SearchDialog: DialogFragment() {
    val title = "コーデ検索"
    private val okText = "検索"
    private val cancelText = "キャンセル"
    private var customView: View? = null
    var onClickListener: DialogInterface.OnClickListener? = DialogInterface.OnClickListener { dialogInterface, i ->
    }
    var onCancelClickListener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        customView = inflater.inflate(R.layout.custom, null)
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
                .setMessage("")
                .setView(customView)
                .setPositiveButton(okText, onClickListener)
                .setNegativeButton(cancelText, onCancelClickListener)
        return builder.create()
    }
}