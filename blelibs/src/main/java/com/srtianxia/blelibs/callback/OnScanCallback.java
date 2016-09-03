package com.srtianxia.blelibs.callback;

import android.bluetooth.le.ScanResult;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/15.
 * ScanCallback的回调
 */
public abstract class OnScanCallback {
    public abstract void onScanResult(int callbackType, ScanResult result);

    public void onBatchScanResults(List<ScanResult> results) {}

    public abstract void onScanFailed(int errorCode);
}
