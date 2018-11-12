package com.beibei.drawlayout

/**
 * author: anbeibei
 *
 * date: 2018/11/12
 *
 * desc:
 */
data class ThirdBean(
        val title: String = "小标题",
        var url: String = "",
        val icon: String = ""
)

data class SecondBean(
        val title: String = "中标题",
        val icon: String = "",
        val items: List<ThirdBean> = listOf()
)

data class FirstBean(
        val title: String = "大标题",
        val items: List<SecondBean> = listOf()
)

data class DrawerBean(
        val hots: SecondBean,
        val forums: FirstBean,
        val san: FirstBean
)

