package com.beibei.drawlayout.fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beibei.drawlayout.R
import com.beibei.drawlayout.ReportBean
import java.lang.ref.WeakReference

/**
 * author: anbeibei
 *
 * date: 2018/4/18
 *
 * eventDesc:
 */
class ReportAdapter(val context: Context) : RecyclerView.Adapter<ReportAdapter.Viewholder>(), BaseAdapter {
    private val mContext: WeakReference<Context> = WeakReference(context)
    private var mTaskStatus: String = ""
    var mList: List<ReportBean> = ArrayList()

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val eventBean = mList[position]
//        holder.itemView.sdv_item_task.setImageURI(eventBean.fileUrl)
//        var isNormal = false
//
//        when (mTaskStatus) {//任务状态 100:待处理 200：已完成
//            "100" -> {
//                holder.itemView.tv_event_status.visibility = View.VISIBLE
//                holder.itemView.tv_event_status.text = "待处理"
//                holder.itemView.tv_event_status.setTextColor(App.context.resources.getColor(R.color.color_red))
//            }
//            "200" -> {
//                holder.itemView.tv_event_status.visibility = View.VISIBLE
//                holder.itemView.tv_event_status.text = "完成"
//                holder.itemView.tv_event_status.setTextColor(App.context.resources.getColor(R.color.color_text_unimportant))
//            }
//            else -> {
//                holder.itemView.tv_event_status.visibility = View.GONE
//            }
//        }
//
//        when (eventBean.eventDealWay) {
//            "200" -> {//100:一般事件 200：自行处理事件
//                holder.itemView.tv_event_kind.text = "事件类型：自行处理事件"
//                isNormal = false
//            }
//            else -> {//不是自行处理任务
//                holder.itemView.tv_event_kind.text = "事件类型：一般事件"
//                isNormal = true
//            }
//        }
//        holder.itemView.tv_event_type.text = eventBean.eventCategoryName
//        holder.itemView.tv_event_locate.text = "位置：" + eventBean.eventLocation
//        holder.itemView.tv_event_desc.text = "描述：" + if (eventBean.eventDesc.isEmpty()) "无" else eventBean.eventDesc
//
//        holder.itemView.ll_item_report.setOnClickListener {
//            skipToActivity(eventBean.eventId, isNormal)
//        }
    }

    private fun skipToActivity(eventId: String, isNormal: Boolean) {
//        val intent = Intent(mContext.get()!!, EventDetailActivity::class.java)
//        val bundle = Bundle()
//        bundle.putString("eventId", eventId)
//        bundle.putBoolean("isNormal", isNormal)
//        intent.putExtras(bundle)
//        (mContext.get()!! as BaseActivity).startActivity(intent)
//        (mContext.get()!! as BaseActivity).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))
    }

//
//    /**
//     * 添加数据，更新界面
//     */
//    fun setData(mList: List<ReportBean>) {
//        this.mList = mList
//        notifyDataSetChanged()
//    }


    /**
     * 添加数据，更新界面
     */
    fun setData(mList: List<ReportBean>, taskStatus: String) {
        this.mList = mList
        this.mTaskStatus = taskStatus
        notifyDataSetChanged()
    }

    class Viewholder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}