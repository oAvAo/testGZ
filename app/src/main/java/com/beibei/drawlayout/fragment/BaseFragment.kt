package com.beibei.drawlayout.fragment

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 项目名称：demeter
 * 包名：com.joyou.smartcity.base
 * 创建人：whj
 * 创建时间：2017/9/29 10:16
 * 类描述：fragment基类
 * 备注：
 */
abstract class BaseFragment : Fragment(), View.OnClickListener {
    /**
     * 获取fragment中view

     * @return
     */
    private var rootView: View? = null
    var sp: SharedPreferences? = null
    protected var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = View.inflate(getActivity(), layoutView!!, null /* click listener */)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    /**
     * 初始化布局
     */
    protected abstract fun initView()

    /**
     * 获取布局id
     */
    abstract val layoutView: Int?


    /**
     * 初始化数据
     */
    protected abstract fun initData()


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        this.activity = activity
    }

    override fun onClick(v: View) {

    }

}
