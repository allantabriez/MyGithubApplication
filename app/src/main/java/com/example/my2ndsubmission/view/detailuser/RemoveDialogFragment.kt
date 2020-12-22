package com.example.my2ndsubmission.view.detailuser

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.my2ndsubmission.R
import kotlinx.android.synthetic.main.fragment_delete_dialog.*

class RemoveDialogFragment : DialogFragment(), View.OnClickListener {

    private var removeDialogListener: OnRemoveDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        yesButton.setOnClickListener(this)
        noButton.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment
        if (fragment is DetailFragment){
            this.removeDialogListener = fragment.removeDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.removeDialogListener = null
    }

    interface OnRemoveDialogListener {
        fun onAgreed(int: Int)
    }

    override fun onClick(v: View?) {
        when(v){
            yesButton -> {
                if (removeDialogListener != null) removeDialogListener?.onAgreed(1)
                dialog?.dismiss()
            }
            noButton -> {
                if (removeDialogListener != null) removeDialogListener?.onAgreed(0)
                dialog?.dismiss()
            }
        }
    }
}