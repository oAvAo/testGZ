package com.beibei.drawlayout.fragment

import android.os.Bundle
import android.view.View
import com.beibei.drawlayout.R
import kotlinx.android.synthetic.main.activity_my_report.*
import kotlinx.android.synthetic.main.head_view1.*

/**
 * author: anbeibei
 *
 * date: 2018/4/20
 *
 * eventDesc: 我的上报
 */
class MyReportActivity : BaseActivity() {
//    private var mHelper: MineHelper? = null
    private var mPagerAdapter: MainPagerAdapter? = null//适配器
    private val mTabTitle: ArrayList<String> = arrayListOf("待处理", "已完成")//新闻的分类

    override val layoutId: Int
        get() = R.layout.activity_my_report

    override fun initView() {
//        setStatusBarFull(this, 0, null, false)
        tv_head_title.text = "我的上报"
        iv_head_right.visibility = View.VISIBLE
        iv_head_back.setOnClickListener { finish() }
        iv_head_right.setOnClickListener(this)


//        mHelper = MineHelper(this, this)

        mPagerAdapter = MainPagerAdapter(supportFragmentManager, 2, ReportFragmentFactory, mTabTitle)
        vp_my_report.adapter = mPagerAdapter
        vp_my_report.offscreenPageLimit = 1

//        mHelper?.reflex(tl_my_report)//TabLayout设置下划线(Indicator)宽度
    }

    override fun initData() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_head_right -> {//跳转搜索页面
                val bundle = Bundle()
//                bundle.putInt(Constant.FROM_WHICH, Constant.FromMyReportActivity)
//                startActivity(SearchActivity::class.java, bundle)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ReportFragmentFactory.remove()
    }

//    /**
//     * TabLayout设置下划线(Indicator)宽度
//     *
//     * https://blog.csdn.net/u013134391/article/details/70833903
//     */
//    fun reflex(tabLayout: TabLayout) {
//        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
//        tabLayout.post {
//            try {
//                //拿到tabLayout的mTabStrip属性
//                val mTabStrip = tabLayout.getChildAt(0) as LinearLayout
//
//                val dp10 = StringUtils.dip2px(tabLayout.context, 10f)
//
//                for (i in 0 until mTabStrip.childCount) {
//                    val tabView = mTabStrip.getChildAt(i)
//
//                    //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
//                    val mTextViewField = tabView.javaClass.getDeclaredField("mTextView")
//                    mTextViewField.setAccessible(true)
//
//                    val mTextView = mTextViewField.get(tabView) as TextView
//
//                    tabView.setPadding(0, 0, 0, 0)
//
//                    //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                    var width = 0
//                    width = mTextView.width
//                    if (width == 0) {
//                        mTextView.measure(0, 0)
//                        width = mTextView.measuredWidth
//                    }
//
//                    var widtht = tabView.width
//
//                    //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
//                    val params = tabView.layoutParams as LinearLayout.LayoutParams
//                    params.width = width
//                    params.leftMargin = (widtht - width) / 2
//                    params.rightMargin = (widtht - width) / 2
////                    params.leftMargin = dp10
////                    params.rightMargin = dp10
//                    tabView.layoutParams = params
//
//                    tabView.invalidate()
//                }
//
//            } catch (e: NoSuchFieldException) {
//                e.printStackTrace()
//            } catch (e: IllegalAccessException) {
//                e.printStackTrace()
//            }
//        }
//
//    }
}