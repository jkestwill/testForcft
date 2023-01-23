package com.jkestwill.test.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class BaseFragment<T: ViewBinding>(private val inflater: Inflater<T>): Fragment() {

    private var _binding:T?=null
    val binding get() =  _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= inflater(inflater,container,false)
        return _binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}