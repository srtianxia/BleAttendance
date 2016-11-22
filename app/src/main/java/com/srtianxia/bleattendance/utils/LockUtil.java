package com.srtianxia.bleattendance.utils;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.srtianxia.bleattendance.ui.activity.Lockactivity.LockActivity;
import com.srtianxia.bleattendance.ui.activity.Lockactivity.LockActivity1;
import com.srtianxia.bleattendance.ui.activity.Lockactivity.LockActivity2;
import com.srtianxia.bleattendance.ui.activity.Lockactivity.LockActivity3;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by 梅梅 on 2016/9/5.
 */
public class LockUtil {
    public static boolean queryUsageStats(Context context){
        final String TAG = "queryUsageStats";

        class RecentUseComparator implements Comparator<UsageStats> {

            @Override
            public int compare(UsageStats lhs, UsageStats rhs) {
                return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs.getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;
            }
        }

        RecentUseComparator mRecentUseComparator = new RecentUseComparator();
        long ts = System.currentTimeMillis();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        List<UsageStats> usageStatses = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,ts -1000 * 10,ts);
        if (isPermissionForTest(context) == false){
            return false;
        }
        Collections.sort(usageStatses,mRecentUseComparator);
        String currentTopPackage = "launcher";
        if (usageStatses.size() != 0)
            currentTopPackage = usageStatses.get(0).getPackageName();

        //将包名的最后一段保存用于判断比较方便
        String[] str = currentTopPackage.split("\\.");
        currentTopPackage = str[str.length-1].toString();


        Log.i(TAG,currentTopPackage);
        if (isCurrentDetection(currentTopPackage)){
            Log.i(TAG,"在前台");
        }else
        {
            Log.i(TAG,"在后台");

            int ran_num = new Random().nextInt(4);   //生成一个0-3的随机数,随机选择lockactivity
            Intent intent = null;
            /*switch (ran_num){
                case 0:
                    intent = new Intent(context,LockActivity.class);
                    break;
                case 1:
                    intent = new Intent(context,LockActivity1.class);
                    break;
                case 2:
                    intent = new Intent(context,LockActivity2.class);
                    break;
                case 3:
                    intent = new Intent(context,LockActivity3.class);
                    break;
            }*/

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return true;
    }

    /**
     * 判断是否开启了权限
     * @param context
     * @return
     */
    public static boolean isPermissionForTest(Context context){
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isCurrentDetection(String Currentpackage ){

        //通过包名的最后一部分判断 是否和 任务栏 或 主界面launcher 或 本app 的包名相等
        if (Currentpackage.equals("systemui") ){
            return true;
        }
        if ( Currentpackage.equals("launcher")){
            return true;
        }
        if ( Currentpackage.equals("bleattendance")){
            return true;
        }
        return false;
    }
}
