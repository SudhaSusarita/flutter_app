package com.example.flutter_app

import android.app.DialogFragment
import android.content.Context
import android.nfc.FormatException
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.IOException

class NFCReadFragment : DialogFragment() {
    private var mTvMessage: TextView? = null
    private var mListener: Listener? = null

  /*  @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View {
        val view: View = inflater.inflate(R.layout.fragment_read, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        mTvMessage = view.findViewById<View>(R.id.tv_message) as TextView
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as MainActivity
        mListener!!.onDialogDisplayed()
    }

    override fun onDetach() {
        super.onDetach()
        mListener!!.onDialogDismissed()
    }

    fun onNfcDetected(ndef: Ndef) {
        readFromNFC(ndef)
    }

    private fun readFromNFC(ndef: Ndef) {
        try {
            ndef.connect()
            val ndefMessage = ndef.ndefMessage
            val message = String(ndefMessage.records[0].payload)
            Log.d(TAG, "readFromNFC: $message")
            mTvMessage!!.text = message
            ndef.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: FormatException) {
            e.printStackTrace()
        }
    }

    companion object {
        val TAG = NFCReadFragment::class.java.simpleName
        fun newInstance(): NFCReadFragment {
            return NFCReadFragment()
        }
    }
}
