package com.beibei.drawlayout.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * 项目名称：Jupiter-Android
 * 包名：com.joyou.smartcity.view.adapter
 * 创建人：dashen
 * 创建时间：2017/7/18 10:24
 * 类描述：主界面tab适配器
 * 备注：
 */
class MainPagerAdapter(fm: FragmentManager, val itemCount: Int, val factory: BaseFragmentFactory) : FragmentPagerAdapter(fm) {
    var mTitleList = ArrayList<String>()//标题集合
    var idList = ArrayList<Int>()//id集合

    /**
     * 次构造
     */
    constructor(fm: FragmentManager, itemCount: Int, factory: BaseFragmentFactory, titleList: ArrayList<String>) : this(fm, itemCount, factory) {
        this.mTitleList = titleList
    }

    constructor(fm: FragmentManager, itemCount: Int, factory: BaseFragmentFactory, titleList: ArrayList<String>, idList: ArrayList<Int>) : this(fm, itemCount, factory) {
        this.mTitleList = titleList
        this.idList = idList
    }

    override fun getItem(position: Int): Fragment {
        if (idList.size == 0) {
            return factory.createFragment(position)!!
        } else {
            return factory.createFragment(position, idList[position])!!
        }
    }

    override fun getCount(): Int {
        return itemCount
    }

    override fun getPageTitle(position: Int): CharSequence {
        if (mTitleList.size == 0) {
            return ""
        } else {
            return mTitleList[position]
        }
    }
}