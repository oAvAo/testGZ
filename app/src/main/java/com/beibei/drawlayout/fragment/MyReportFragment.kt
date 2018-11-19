package com.beibei.drawlayout.fragment

import android.os.Bundle
import com.beibei.drawlayout.R
import com.beibei.drawlayout.ReportBean

/**
 * author: anbeibei
 *
 * date: 2018/4/17
 *
 * eventDesc: 我的上报fragment
 */
class MyReportFragment : BaseFragment() {
    private var mWhich: Int = -1
//    private var mHelper: MineHelper? = null
//    private var mEmptyView: EmptyView? = null//空数据界面
    private var pageNum: Int = 0
    private var mAdapter: ReportAdapter? = null//适配器
    private var mList: ArrayList<ReportBean> = ArrayList()//集合
    private var mTaskStatus = "100"//任务状态 100:待处理 200：已完成


    override fun initView() {
        if (arguments != null) {
            mWhich = arguments!!.getInt(ARG_PARAM1, -1)
            mTaskStatus = if (mWhich == 0) {
                "100"
            } else {
                "200"
            }
        }
        initRefreshView()
//        mHelper = MineHelper(activity!!, this)
    }

    override val layoutView: Int?
        get() = R.layout.fragment_new

    override fun initData() {
//        mHelper?.getMyReportData(ReportListRequest(Constant.USER_ID, mTaskStatus, "", pageNum.toString()))
    }

    private fun initRefreshView() {
        //添加空数据界面
//        mEmptyView = EmptyView()
//        mEmptyView!!.addEmptyView(activity!!, rl_list)
//        rv.layoutManager = LinearLayoutManager(activity!!)
//        mAdapter = ReportAdapter(context!!)
//        rv.adapter = mAdapter
//
//        val headerView = HeadRefreshView(activity!!)
//        trl.setHeaderView(headerView)
//        val loadingView = BottomRefreshView(activity!!)
//        trl.setBottomView(loadingView)
//        trl.setOverScrollRefreshShow(false)
//        trl.setEnableOverScroll(false)
//        trl.setOnRefreshListener(object : RefreshListenerAdapter() {
//            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
//                pageNum = 0
//                initData()
//            }
//
//            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
//                pageNum++
//                mHelper?.getMyReportData(ReportListRequest(Constant.USER_ID, mTaskStatus, "", pageNum.toString()))
//            }
//        })
    }
//
//    override fun getReportSuccess(list: ArrayList<ReportBean>) {
//        if (pageNum == 0) {
//            mList.clear()
//            trl.finishRefreshing()
//        } else {
//            trl.finishLoadmore()
//        }
//        if (list.size == 0) {
//            if (pageNum == 0) {
//                mEmptyView!!.setErrorViewDefault(this, "", EmptyErrorType.Type.NO_DATA)
//            } else {
//                pageNum--
//                ToastUtils.showToast(App.context, "已加载全部数据~")
//            }
//        } else {
//            mEmptyView?.resetNormalView()
//            mList.addAll(list)
//            mAdapter?.setData(mList, mTaskStatus)
//        }
//    }
//
//    override fun setOnClickEmptyErrorListener(type: EmptyErrorType.Type) {
//        pageNum = 0
//        initData()
//    }

    companion object {
        private val ARG_PARAM1 = "param1"

        /**
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment HomeFragment.
         */
        fun newInstance(param1: Int): MyReportFragment {
            val fragment = MyReportFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }


}