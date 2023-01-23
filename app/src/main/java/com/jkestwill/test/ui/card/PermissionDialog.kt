package com.jkestwill.test.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jkestwill.test.databinding.DialogFragmentPermissionBinding

class PermissionDialog:BottomSheetDialogFragment() {

    private var binding:DialogFragmentPermissionBinding?=null

    private var onPositiveListener:(()->Unit)? = null

    private var headerText=""
    private var description=""
    private var positiveButtonText=""

   companion object{
         const val TAG = "PermissionDialog"
    }

    fun setOnPositiveListener(onPositiveListener:()->Unit){
        this.onPositiveListener=onPositiveListener
    }

    fun setHeader(text:String){
        headerText=text
    }

    fun setDescription(description:String){
        this.description=description
    }

    fun setPositiveButtonText(text:String){
        positiveButtonText=text
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DialogFragmentPermissionBinding.inflate(inflater,container,false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.bottomSheetText?.text=headerText
        binding?.bottomSheetDescription?.text=description
        binding?.bottomSheetOk?.text=positiveButtonText

        binding?.bottomSheetOk?.setOnClickListener {
            onPositiveListener?.let { it1 -> it1() }
        }
        binding?.bottomSheetCancel?.setOnClickListener {
            dismiss()
        }
    }
}