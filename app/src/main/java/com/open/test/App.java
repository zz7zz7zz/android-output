package com.open.test;

import android.app.Application;
import android.content.Intent;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.open.test.apmthridparty.DynamicConfigImplDemo;
import com.open.test.apmthridparty.TestPluginListener;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.batterycanary.BatteryMonitorPlugin;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.util.MatrixLog;
import com.tencent.sqlitelint.SQLiteLint;
import com.tencent.sqlitelint.SQLiteLintPlugin;
import com.tencent.sqlitelint.config.SQLiteLintConfig;

public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        apmInitMatrix();

//        BlockCanary.install(this, new BlockCanaryContext(){
//            @Override
//            public int provideBlockThreshold() {
//                return 50;
//            }
//        }).start();
    }

    private void apmInitMatrix(){
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        boolean matrixEnable = dynamicConfig.isMatrixEnable();
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();

        MatrixLog.i(TAG, "MatrixApplication.onCreate");

        Matrix.Builder builder = new Matrix.Builder(this);
        builder.patchListener(new TestPluginListener(this));

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                .splashActivities("sample.tencent.matrix.SplashActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);

//        if (matrixEnable) {
//
//            //resource
//            Intent intent = new Intent();
//            ResourceConfig.DumpMode mode = ResourceConfig.DumpMode.MANUAL_DUMP;
//            MatrixLog.i(TAG, "Dump Activity Leak Mode=%s", mode);
//            intent.setClassName(this.getPackageName(), "com.tencent.mm.ui.matrix.ManualDumpActivity");
//            ResourceConfig resourceConfig = new ResourceConfig.Builder()
//                    .dynamicConfig(dynamicConfig)
//                    .setAutoDumpHprofMode(mode)
////                .setDetectDebuger(true) //matrix test code
////                    .set(intent)
//                    .setManualDumpTargetActivity(ManualDumpActivity.class.getName())
//                    .build();
//            builder.plugin(new ResourcePlugin(resourceConfig));
//            ResourcePlugin.activityLeakFixer(this);
//
//            //io
//            IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
//                    .dynamicConfig(dynamicConfig)
//                    .build());
//            builder.plugin(ioCanaryPlugin);
//
//
//            // prevent api 19 UnsatisfiedLinkError
//            //sqlite
//            SQLiteLintConfig sqlLiteConfig;
//            try {
//                sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
//            } catch (Throwable t) {
//                sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
//            }
//            builder.plugin(new SQLiteLintPlugin(sqlLiteConfig));
//
//
//            ThreadMonitor threadMonitor = new ThreadMonitor(new ThreadMonitorConfig.Builder().build());
//            builder.plugin(threadMonitor);
//
//
//            BatteryMonitorPlugin batteryMonitorPlugin = BatteryCanaryInitHelper.createMonitor();
//            builder.plugin(batteryMonitorPlugin);
//        }

        Matrix.init(builder.build());

        //start only startup tracer, close other tracer.
        tracePlugin.start();
//        Matrix.with().getPluginByClass(ThreadMonitor.class).start();
//        Matrix.with().getPluginByClass(BatteryMonitor.class).start();
        MatrixLog.i("Matrix.HackCallback", "end:%s", System.currentTimeMillis());
    }
}
