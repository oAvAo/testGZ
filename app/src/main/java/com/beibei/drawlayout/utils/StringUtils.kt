package com.beibei.drawlayout.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.text.TextUtils
import java.lang.reflect.Field
import java.util.*

/**
 * Created by anbeibei on 2018/4/13.
 */

/**
 * 字符串工具类
 */
object StringUtils {
    private var sPackageInfo: PackageInfo? = null
    var random = Random(100)

    /**
     * 字符串长度大于int a时保留前a个+...

     * @param string
     * *
     * @return
     */
    fun cutStringWithEnd(string: String, limitNum: Int): String {
        var string = string
        if (string.length > limitNum) {
            string = string.substring(0, limitNum) + "..."
        }
        return string
    }

    /**
     * 字符串长度大于int a时保留前a个

     * @param string
     * *
     * @return
     */
    fun cutString(string: String, limitNum: Int): String {
        var string = string
        if (string.length > limitNum) {
            string = string.substring(0, limitNum)
        }
        return string
    }

    /**
     * 车牌号格式：汉字 + A-Z + 5位A-Z或0-9（只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）正则表达式有局限性，比如第一位只限定是汉字，
     * 没限定只有34个省汉字缩写；车牌号不存在字母I和O，防止和1、0混淆；部分车牌无法分辨等等

     * @param carnumber
     * *
     * @return
     */
    fun isCarnumberNO(carnumber: String): Boolean {
        return !TextUtils.isEmpty(carnumber) && carnumber.matches("[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}".toRegex())
    }

    /**
     * 身份证号正则判断

     * @param idNum
     * *
     * @return
     */
    fun isIdNum(idNum: String): Boolean {
        return !TextUtils.isEmpty(idNum) && idNum.matches("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])".toRegex())
    }

    /**
     * 判断是否为+86手机号码

     * @param number
     * *
     * @return
     */
    fun is86Num(number: String): Boolean {
        //        return !TextUtils.isEmpty(number) && number.matches("^((\\+{0,1}86){0,1})1[0-9]{10}");
        return !TextUtils.isEmpty(number) && number.matches("^[+][8][6][1][3-8]\\d{9}$".toRegex())
    }

    /**
     * 判断是否为邮箱
     */
    fun isEmail(Email: String): Boolean {
        return !TextUtils.isEmpty(Email) && Email.matches("^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$".toRegex())
    }

    /**
     * 判断是否为手机号码

     * @param number
     * *
     * @return
     */
    fun isMobilePhoneNumber(number: String): Boolean {
        //        return !TextUtils.isEmpty(number) && number.matches("^[1][3-8]\\d{9}$");
        return !TextUtils.isEmpty(number) && number.matches("^1[3|4|5|7|8][0-9]\\d{8}$".toRegex())
    }

    fun getRandom(): String {
        return random.nextInt().toString() + ""
    }

    fun getApkVersionName(context: Context): String {
        return getPackageInfo(context)!!.versionName
    }

    @Synchronized
    fun getPackageInfo(context: Context): PackageInfo? {
        if (sPackageInfo == null) {
            try {
                sPackageInfo = context.packageManager.getPackageInfo(
                        context.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                assert(false)
                return null
            }

        }
        return sPackageInfo
    }

    /**
     * 得到设备屏幕的宽度
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 得到设备屏幕的高度
     */
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 得到设备的密度
     */
    fun getScreenDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }


    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 网络是否可用

     * @param context
     * *
     * @return
     */
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    @Throws(Exception::class)
    fun PO2Map(o: Any): Map<String, Any> {
        val map = HashMap<String, Any>()
        var fields: Array<Field>? = null
        val clzName = o.javaClass.simpleName
        fields = o.javaClass.declaredFields
        for (field in fields!!) {
            field.isAccessible = true
            val proName = field.name
            val proValue = field.get(o)
            map.put(proName, proValue)
        }
        return map
    }

    /**
     * 判断某个服务是否正在运行的方法

     * @param mContext
     * *
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * *
     * @return true代表正在运行，false代表服务没有正在运行
     */
    fun isServiceWork(mContext: Context, serviceName: String): Boolean {
        var isRunning = false
        val activityManager = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val serviceList = activityManager.getRunningServices(Integer.MAX_VALUE)
        if (serviceList.size <= 0) {
            return false
        }
        for (i in serviceList.indices) {
            if (serviceList[i].service.className == serviceName) {
                isRunning = true
                break
            }
        }
        return isRunning
    }

    /**
     * 是否是空的字符串
     *
     * @param str
     * @return
     */
    fun isEmpty(str: String?): Boolean {
        if (str == null || "" == str || "null" == str) {
            return true
        }
        return false
    }

    fun isNUll2Num(str:String?):String{
        if (!isEmpty(str)){
            return str!!
        }else{
            return "0"
        }
    }

    fun convertNull2Str(str:String?):String{
        return if (str!=null){
            str
        }else{
            ""
        }
    }

    fun isNullEmptyBlank(str: String?): Boolean {
        StringUtils
        return str == null || "" == str || "" == str.trim { it <= ' ' }
    }


    /**
     * 将 07月07日改为  7月7日

     * @param date
     * *
     * @return
     */
    fun formatDate(date: String): String {
        var date = date
        if ("0" == date[3].toString()) {
            date = date.substring(0, 3) + date.substring(4, date.length)
        }
        if (date.startsWith("0")) {
            date = date.substring(1, date.length)
        }
        return date
    }

    /**
     * 判断字符串是否为空/""

     * @param string
     * *
     * @return
     */
    fun isNullOrNoLength(string: String?): Boolean {
        if (string == null) {
            return true
        } else if (string.trim { it <= ' ' }.length == 0) {
            return true
        } else {
            return false
        }
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(mContext: Context): Int {
        var statusBarHeight1 = -1
        //获取status_bar_height资源的ID
        val resourceId = mContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = mContext.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight1
    }

    fun replaceTimeSymbol(time:String?):String{
        var tempt = ""
        if (time!=null){
            tempt = time.replace(" ","").replace("-","").replace(":","")
        }
        return tempt
    }
}