package com.beibei.drawlayout

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig


/**
 * Created by anbeibei on 2018/4/8.
 */

class App : Application() {

    /**
     * 静态代码
     */
    companion object {
        val context: Context
            get() = application!!.applicationContext


        private var application: App? = null
        var sp: SharedPreferences? = null//sp存储
        var isLogin: Boolean = false//是否登录
//        var refWatcher: RefWatcher? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
//        sp = getSharedPreferences(Constant.FILE_NAME, Context.MODE_PRIVATE)
//        resetLogin()
//        refWatcher = initLeakCanary()
//        initAllDebug()
        initFresco(this)// 初始化fresco参数
//        initJPush()  //极光初始化
//        initGreenDao()
//        //融云初始化
//        IMHelper.initRongIm(this)
    }
//
//    private fun initLeakCanary(): RefWatcher {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED
//        }
//        return LeakCanary.install(this)
//    }
//
//
//    private lateinit var mDaoSession: DaoSession
//    private fun initGreenDao() {
//        mDaoSession = DaoMaster(
//                DaoMaster.DevOpenHelper(this, "greendao_demo.db").writableDb).newSession()
//    }
//
//    fun getDaoSession(): DaoSession {
//        return mDaoSession
//    }
//
//
//    /**
//     * 获取sp存储
//     * @return
//     */
//    fun getSharedPreferences(): SharedPreferences = sp!!
//
//    fun getLoginStatus(): Boolean {
//        return isLogin
//    }

//    /**
//     * 初始化leakcannary
//     */
//    private fun initAllDebug() {
//        //todo debug模式
//        if (BuildConfig.DEBUG) {
//            //控制日志输出
//            LogUtils.mDebuggable = LogUtils.LEVEL_ERROR
//            //输出极光日志
//            JPushInterface.setDebugMode(true)
//            //友盟集成测试
////            MobclickAgent.setDebugMode(true)
//        } else {
//            //控制日志输出
//            LogUtils.mDebuggable = LogUtils.LEVEL_NONE
//            //不输出
//            JPushInterface.setDebugMode(false)
//            //友盟集成测试
////            MobclickAgent.setDebugMode(false)
//        }
////        if (LeakCanary.isInAnalyzerProcess(this)) {
////            return
////        }
////        //todo debug模式
////        if (BuildConfig.DEBUG) {
////            refWatcher = LeakCanary.install(this)
//////            refWatcher = RefWatcher.DISABLED
////        } else {
////            refWatcher = RefWatcher.DISABLED
////        }
//    }

    /**
     * 配置fresco相关
     *
     * @param applicationContext
     */
    private fun initFresco(applicationContext: Context) {
        val configBuilder = ImagePipelineConfig.newBuilder(applicationContext)
        //初始化MemoryCacheParams对象。从字面意思也能看出，这是内存缓存的Params
        val bitmapCacheParams = MemoryCacheParams(
                // 内存缓存最大值，这里用最大运行内存的三分之一
                Runtime.getRuntime().maxMemory().toInt() / 3,
                // 内存缓存单个文件最大值，这里就用Integer的最大值了。
                Integer.MAX_VALUE,
                // 可以释放的内存缓存的最大值，当内存缓存到了上面设置的最大值，最多可以释放多少内存
                Runtime.getRuntime().maxMemory().toInt() / 3,
                //可以被释放缓存的文件数
                Integer.MAX_VALUE,
                //Max cache entry size.
                Integer.MAX_VALUE)
        //初始化DiskCacheConfig对象
        val diskCacheConfig = DiskCacheConfig.newBuilder(applicationContext)
                //设置磁盘缓存路径
                .setBaseDirectoryPath(applicationContext.externalCacheDir)
                //设置磁盘缓存文件夹名
                .setBaseDirectoryName("imagecache")
                //设置磁盘缓存最大值
                .setMaxCacheSize((300 * ByteConstants.MB).toLong())
                .build()
        //getImagePipelineConfig设置属性
        configBuilder
                //设置内存缓存的Params
                .setBitmapMemoryCacheParamsSupplier { bitmapCacheParams }
                //设置磁盘缓存的配置
                .setMainDiskCacheConfig(diskCacheConfig)
                //设置图片压缩质量，不设置默认为ARGB_8888
                .setBitmapsConfig(Bitmap.Config.RGB_565).isDownsampleEnabled = true

        Fresco.initialize(this, configBuilder.build())
    }
//
//    /**
//     * 初始化极光参数
//     */
//    private fun initJPush() {
//        JPushInterface.init(this)
//        val builder = BasicPushNotificationBuilder(applicationContext)
//        builder.statusBarDrawable = R.mipmap.logo
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_SHOW_LIGHTS  //设置为自动消失和呼吸灯闪烁
//        builder.notificationDefaults = Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS  // 设置为铃声、震动、呼吸灯闪烁都要
//        JPushInterface.setPushNotificationBuilder(2, builder)
//    }
//
//
//    /**
//     * 重置登录状态
//     */
//    fun resetLogin() {
//        val userId = SharedPreferencesUtils[this, Constant.KEY_USER_ID, ""].toString()
//        if (userId.isEmpty()) {
//            isLogin = false
//        } else {
//            Constant.USER_ID = userId
//            Constant.TOKEN = SharedPreferencesUtils[this, Constant.KEY_TOKEN, ""].toString()
//            Constant.USER_NICKNAME = SharedPreferencesUtils[this, Constant.KEY_REAL_NAME, ""].toString()
//            Constant.USER_PIC = SharedPreferencesUtils[this, Constant.KEY_USER_URL, ""].toString()
//            isLogin = true
//        }
//    }
//
//    /**
//     * 清除登录信息
//     */
//    fun clearLogin() {
//        Constant.USER_ID = ""
//        Constant.TOKEN = ""
//        Constant.USER_NICKNAME = ""
//        Constant.USER_PIC = ""
//        //极光删除别名
//        SharedPreferencesUtils.clear(this)
//        JPushSetAliasHelper.onTagAliasAction("", TagAliasOperatorHelper.ACTION_DELETE)
////        LogUtils.e("setAlias--------》alias：null")
////        //友盟退出统计
////        MobclickAgent.onProfileSignOff()
//        isLogin = false
//    }
}
