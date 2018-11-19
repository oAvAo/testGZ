package com.beibei.drawlayout

import android.os.Parcel
import android.os.Parcelable

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


data class ReportBean(
        val eventScope: String,
        val eventDesc: String,
        val eventLevel: String,
        val eventDealWay: String,
        val eventCategoryName: String,
        val eventId: String,
        val fileUrl: String,
        val eventLocation: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eventScope)
        parcel.writeString(eventDesc)
        parcel.writeString(eventLevel)
        parcel.writeString(eventDealWay)
        parcel.writeString(eventCategoryName)
        parcel.writeString(eventId)
        parcel.writeString(fileUrl)
        parcel.writeString(eventLocation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReportBean> {
        override fun createFromParcel(parcel: Parcel): ReportBean {
            return ReportBean(parcel)
        }

        override fun newArray(size: Int): Array<ReportBean?> {
            return arrayOfNulls(size)
        }
    }
}

