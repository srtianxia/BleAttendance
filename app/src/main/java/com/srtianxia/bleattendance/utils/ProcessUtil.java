package com.srtianxia.bleattendance.utils;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 梅梅 on 2016/9/5.
 */
public class ProcessUtil {
    private static final String PACKAGE_NAME_SYSTEM_UI = "systemui";
    private static final String PACKAGE_NAME_LAUNCHER = "launcher";
    private static final String PACKAGE_NAME_BLE = "bleattendance";
    private static final String PACKAGE_NAME_HOME = "home";


    public static boolean isNeededInBackground(Context context) {
        class RecentUseComparator implements Comparator<UsageStats> {
            @Override
            public int compare(UsageStats lhs, UsageStats rhs) {
                return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed())
                       ? -1
                       : (lhs.getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;
            }
        }

        RecentUseComparator mRecentUseComparator = new RecentUseComparator();
        long currentTime = System.currentTimeMillis();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(
            Context.USAGE_STATS_SERVICE);
        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_BEST, currentTime - 1000 * 10, currentTime);
        if (isPermission(context) == false) {
            return false;
        }
        Collections.sort(usageStats, mRecentUseComparator);
        String currentTopPackage = PACKAGE_NAME_LAUNCHER;
        if (usageStats.size() != 0) {
            currentTopPackage = usageStats.get(0).getPackageName();
        }
        //包名的最后一段
        String[] str = currentTopPackage.split("\\.");
        currentTopPackage = str[str.length - 1].toString();
        return !isCurrentDetection(currentTopPackage);
    }


    /**
     * 判断是否开启了权限
     */
    public static boolean isPermission(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(
                context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(
                Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }


    public static boolean isCurrentDetection(String currentPackageName) {
        //通过包名的最后一部分判断是否和任务栏、主界面launcher、本app的包名相等
        if (PACKAGE_NAME_SYSTEM_UI.equals(currentPackageName)) return true;
        if (PACKAGE_NAME_LAUNCHER.equals(currentPackageName)) return true;
        if (PACKAGE_NAME_BLE.equals(currentPackageName)) return true;
        if (PACKAGE_NAME_HOME.equals(currentPackageName)) return true;
        return false;
    }
}
