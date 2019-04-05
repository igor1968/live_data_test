package com.igordanilchik.livedatatest.app

import android.app.Application
import android.content.Context
import com.igordanilchik.livedatatest.BuildConfig
import com.igordanilchik.livedatatest.common.di.common.app.ApplicationComponent
import com.igordanilchik.livedatatest.common.di.common.app.ApplicationModule
import com.igordanilchik.livedatatest.common.di.common.app.DaggerApplicationComponent
import com.igordanilchik.livedatatest.common.log.TimberLogger
import com.igordanilchik.livedatatest.data.common.logger.CapLogger
import com.squareup.leakcanary.LeakCanary

class DaggerApplication : Application() {

    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        initInjector()
        initLeakDetection()
        initLogger()
    }

    private fun initLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)

//            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
//                    .detectAll()
//                    .penaltyLog()
//                    .build())
//            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
//                    .detectAll()
//                    .penaltyLog()
//                    //.penaltyDeath()
//                    .build())
        }
    }

    private fun initInjector() {
        appComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    /**
     * Plant proper Timber for Logging for Debug
     * or Sending Errors as Crashes for Release
     * Pretends as Fail!
     */
    private fun initLogger() {
        CapLogger.setDelegate(TimberLogger())
        TimberLogger.initTimber(BuildConfig.DEBUG)
    }

    companion object {

        operator fun get(context: Context?): DaggerApplication {
            return context?.applicationContext as DaggerApplication
        }
    }
}
