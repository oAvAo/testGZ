package com.beibei.drawlayout.fragment

import java.util.*

/**
 * 项目名称：Samsung
 * 包名：com.joyou.smartcity.com.joyou.smartcity.common.factory
 * 创建人：dashen
 * 创建时间：2017/4/26 14:07
 * 类描述：我的上报fragment工厂类
 * 备注：
 */
object ReportFragmentFactory : BaseFragmentFactory() {
    private val fragmentMap = HashMap<Int, BaseFragment>()
    private val fragmentList = ArrayList<BaseFragment>()
    override fun createFragment(arg0: Int, type: Int): BaseFragment? {
        return null
    }

    override fun createFragment(arg0: Int): BaseFragment {
        //
        var fragment: BaseFragment? = fragmentMap[arg0]
        if (fragment != null) {
            return fragment
        } else {
            fragment = MyReportFragment.newInstance(arg0)
            //添加到map集合中去
            fragmentMap.put(arg0, fragment!!)
            return fragment
        }
    }

    /**
     * 获取已经显示的fragment集合

     * @return
     */
    fun getFragmentList(): ArrayList<BaseFragment> {
        fragmentList.clear()
        for (baseFragment in fragmentMap.values) {
            fragmentList.add(baseFragment)
        }
        return fragmentList
    }

    /**
     * 在activity kill 时移除
     */
    fun remove() {
        fragmentMap.clear()
        fragmentList.clear()
    }
}