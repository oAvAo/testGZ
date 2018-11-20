package com.beibei.drawlayout.chat

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.CheckBox
import com.beibei.drawlayout.R.id.*
import com.dashen.demeter.common.utils.RxBus
import com.dashen.demeter.common.utils.emptyview.EmptyErrorClickListener
import com.dashen.demeter.common.utils.emptyview.EmptyErrorType
import com.dashen.demeter.common.utils.emptyview.EmptyView
import com.dashen.utils.DialogHelper
import com.dashen.utils.ToastUtils
import com.joyou.smartcity.R
import com.joyou.smartcity.base.BaseActivity
import com.joyou.smartcity.base.MessageEvent
import com.joyou.smartcity.common.constant.Constant
import com.joyou.smartcity.common.newNetwork.model.ChatPersonBean
import com.joyou.smartcity.common.newNetwork.model.GroupDetailInfo
import com.joyou.smartcity.common.newNetwork.request.AddFriendRequest
import com.joyou.smartcity.common.newNetwork.request.InitDataNoParamRequest
import com.joyou.smartcity.common.newNetwork.request.QuitGroupRequest
import com.joyou.smartcity.common.utils.DialogUtils
import com.joyou.smartcity.common.utils.rongim.IMHelper
import com.joyou.smartcity.presenter.ChatHelper
import com.joyou.smartcity.presenter.viewinter.ChatView
import com.joyou.smartcity.view.adapter.FriendListAdapter
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.head_view1.*
import me.yokeyword.indexablerv.IndexableAdapter
import me.yokeyword.indexablerv.IndexableHeaderAdapter
import me.yokeyword.indexablerv.IndexableLayout

/**
 *  1.好友列表
 *  2.选择群组成员 -- 发起群聊
 *  3.添加好友
 *  4.移除好友
 */
class FriendsListActivity : BaseActivity(), ChatView, EmptyErrorClickListener {
    private var mHelper: ChatHelper? = null
    private var mEmptyView: EmptyView? = null//空数据界面
    private var mAdapter: FriendListAdapter? = null//适配器
    private var mList: ArrayList<ChatPersonBean> = ArrayList()//集合
    private val mSelectFriend = ArrayList<ChatPersonBean>()
    private var mMenuHeaderAdapter: MenuHeaderAdapter? = null

    private var mType = Constant.MODE_NORMAL//普通展示类型／选择类型
    private var mMode = Constant.FriendList.CREATE_GROUP_CHAT
    private var mGroupDetailInfo: GroupDetailInfo? = null
    private var mGroupId = ""

    override val layoutId: Int
        get() = R.layout.activity_friends_list

    override fun initView() {
        setStatusBarFull(this, 0, null, false)
        iv_head_back.setOnClickListener { finish() }

        mHelper = ChatHelper(this, this)
        mEmptyView = EmptyView()
        mEmptyView!!.addEmptyView(this, rl_list)

        val extras = intent.extras
        if (extras != null) {
            mMode = extras.getSerializable(Constant.KEY_FRIEND_LIST_MODE) as Constant.FriendList
        }
        when (mMode) {
            Constant.FriendList.MY_FRIENDS -> {//我的好友 --普通展示类型
                mType = Constant.MODE_NORMAL
            }
            Constant.FriendList.CREATE_GROUP_CHAT -> {//发起群聊
                mType = Constant.MODE_SELECT
            }
            Constant.FriendList.MEMBER_OWNER_ADD,//成员---添加成员
            Constant.FriendList.GROUP_OWNER_DELETE,//成员---删除成员
            Constant.FriendList.GROUP_OWNER_ADD -> {//群主---添加成员
                mType = Constant.MODE_SELECT
                mGroupDetailInfo = extras.getParcelable(Constant.GROUP_DETAIL_BEAN)
                mGroupId = extras.getString(Constant.KEY_GROUP_ID, "")
            }
            else -> {//添加好友
                mType = Constant.MODE_SELECT
            }
        }
        initIndexableRv()
        initHeadView()
    }

    private fun initIndexableRv() {
        //设置粘性（ABC等字母在屏幕顶部悬停）
        indexableLayout.setStickyEnable(false)
        //设置IOS风格的 OverlayView
//        indexableLayout.setOverlayStyle_Center()
        indexableLayout.setOverlayStyle_MaterialDesign(resources.getColor(R.color.base_theme))
        indexableLayout.setLayoutManager(LinearLayoutManager(this))
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST)
        mAdapter = FriendListAdapter(this, mType)
        indexableLayout.setAdapter(mAdapter)
        mAdapter?.setOnItemContentClickListener(object : IndexableAdapter.OnItemContentClickListener<ChatPersonBean> {
            override fun onItemClick(v: View, originalPosition: Int, currentPosition: Int, entity: ChatPersonBean) {
                when (mType) {
                    Constant.MODE_NORMAL -> {//普通展示模式 跳聊天页面
                        IMHelper.startPrivateChat(this@FriendsListActivity, entity?.userId.toString(), entity?.nickName + "=" + entity?.mobileAccount)
                    }
                    Constant.MODE_SELECT -> {//选择模式
                        if (originalPosition >= 0) {//不是额外加的条目（新的好友，群组等）
                            val checkBox = v.findViewById<CheckBox>(R.id.cb)
                            checkBox.isChecked = !checkBox.isChecked
                            if (checkBox.isChecked) {
                                mSelectFriend.add(entity)
                            } else {
                                mSelectFriend.remove(entity)
                            }
                        }
                    }
                }
            }
        })
        when (mMode) {
            Constant.FriendList.MY_FRIENDS -> {//我的好友 --普通展示类型
                // 构造函数里3个参数,分别对应 (IndexBar的字母索引, IndexTitle, 数据源), 不想显示哪个就传null, 数据源传null时,代表add一个普通的View
                mMenuHeaderAdapter = MenuHeaderAdapter("↑", null, initMenuData())
                // 添加菜单
                indexableLayout.addHeaderAdapter<MenuEntity>(mMenuHeaderAdapter)
                mMenuHeaderAdapter?.setOnItemHeaderClickListener(object : IndexableHeaderAdapter.OnItemHeaderClickListener<MenuEntity> {
                    override fun onItemClick(v: View?, currentPosition: Int, entity: MenuEntity?) {
                        startActivity(MyGroupChatActivity::class.java)
                    }
                })
            }
        }
    }

    private fun initMenuData(): List<MenuEntity> {
        val list = arrayListOf<MenuEntity>()
        list.add(MenuEntity("群聊"))
        return list
    }

    private fun initHeadView() {
        when (mMode) {
            Constant.FriendList.MY_FRIENDS -> {//我的好友列表
                tv_head_title.text = "通讯录"
            }
            Constant.FriendList.CREATE_GROUP_CHAT -> {//我的好友---发起群聊
                tv_head_title.text = "选择群组成员"
                tv_head_right.visibility = View.VISIBLE
                tv_head_right.text = "确定"
                tv_head_right.setOnClickListener(this)
            }
            Constant.FriendList.MEMBER_OWNER_ADD,//成员---添加成员
            Constant.FriendList.GROUP_OWNER_ADD//群主---添加成员
            -> {
                tv_head_title.text = "添加群组成员"
                tv_head_right.visibility = View.VISIBLE
                tv_head_right.text = "确定"
                tv_head_right.setOnClickListener(this)
            }
            Constant.FriendList.GROUP_OWNER_DELETE//群主---删除成员
            -> {
                tv_head_title.text = "移除群组成员"
                tv_head_right.visibility = View.VISIBLE
                tv_head_right.text = "确定"
                tv_head_right.setOnClickListener(this)
            }
        }
    }

    override fun initData() {
        when (mMode) {
            Constant.FriendList.MY_FRIENDS,//我的好友列表
            Constant.FriendList.CREATE_GROUP_CHAT,//发起群聊
            Constant.FriendList.MEMBER_OWNER_ADD,//成员---添加成员
            Constant.FriendList.GROUP_OWNER_ADD -> {//群主---添加成员
                mList.clear()
                mHelper?.getMyFriends(InitDataNoParamRequest(Constant.USER_ID))
            }
            Constant.FriendList.GROUP_OWNER_DELETE -> {//群主---删除成员
                mEmptyView?.resetNormalView()
                val filter = mGroupDetailInfo?.users?.filter { it.userId != Constant.USER_ID }
                mAdapter?.setDatas(filter)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_head_right -> {
                val mSelectFriendId = getSelectFriendId()
                when (mMode) {
                    Constant.FriendList.MEMBER_OWNER_ADD,//成员---添加成员
                    Constant.FriendList.GROUP_OWNER_ADD -> {//群主---添加成员
                        if (mSelectFriendId.size == 0) {
                            ToastUtils.showToast(this, "至少选择一位成员")
                        } else {
                            DialogUtils.startLoading(this)
                            mHelper?.addFriend(AddFriendRequest(Constant.USER_ID, mGroupDetailInfo?.groupName
                                    ?: "群聊", mGroupId, mSelectFriendId))
                        }
                    }
                    Constant.FriendList.GROUP_OWNER_DELETE -> {//群主---删除成员
                        if (mSelectFriendId.size == 0) {
                            ToastUtils.showToast(this, "至少选择一位成员")
                        } else {
                            mHelper?.kickGroupDialog()
                        }
                    }
                    Constant.FriendList.CREATE_GROUP_CHAT -> {//发起群聊
                        mSelectFriendId.add(Constant.USER_ID)
                        if (mSelectFriendId.size < 3) {
                            ToastUtils.showToast(this, "至少选择两位成员")
                        } else {
                            val bundle = Bundle()
                            bundle.putInt(Constant.GROUP_CHAT_INFO_TYPE, Constant.GROUP_CHAT_CREATE)
                            bundle.putStringArrayList(Constant.GROUP_CHAT_SELECT_PERSON, mSelectFriendId)
                            startActivity(EditGroupInfoActivity::class.java, bundle)
                            finish()
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取选择好友的id列表
     */
    private fun getSelectFriendId(): ArrayList<String> {
        val mSelectFriendId = arrayListOf<String>()
        for (i in 0 until mSelectFriend.size) {
            mSelectFriendId.add(mSelectFriend[i].userId)
        }
        return mSelectFriendId
    }

    override fun setOnClickEmptyErrorListener(type: EmptyErrorType.Type) {
        initData()
    }

    override fun getMyFriendsSuccess(it: ArrayList<ChatPersonBean>) {
        if (it.isEmpty()) {//第一页无数据时
            mEmptyView!!.setErrorViewDefault(this, "", EmptyErrorType.Type.NO_DATA)
        } else {
            mEmptyView?.resetNormalView()
            when (mMode) {
                Constant.FriendList.MEMBER_OWNER_ADD,//成员---添加成员
                Constant.FriendList.GROUP_OWNER_ADD -> {//群主---添加成员
                    it.removeAll(mGroupDetailInfo!!.users)
                }
            }
            mList.addAll(it)
            mAdapter?.setDatas(mList)
        }
    }

    override fun addFriendSuccess() {
        RxBus.instance.post(MessageEvent(Constant.CHAT_GROUP_MEMBER_CHANGE_ADD, mSelectFriend))
        finish()
    }

    override fun addFriendMessage() {
        DialogUtils.stopLoading()
    }

    override fun kickGroupConfirm() {
        DialogUtils.startLoading(this)
        mHelper?.kickGroup(QuitGroupRequest(Constant.USER_ID, mGroupId, getSelectFriendId()))
    }

    override fun kickGroupMessage() {
        DialogUtils.stopLoading()
    }

    override fun kickGroupSuccess() {
        finish()
    }
}