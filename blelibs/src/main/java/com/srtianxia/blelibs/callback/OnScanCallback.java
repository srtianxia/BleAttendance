package com.srtianxia.blelibs.callback;

import android.bluetooth.le.ScanResult;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/15.
 * ScanCallback的回调
 */
public interface OnScanCallback {
    void onScanResult(int callbackType, ScanResult result);

    void onBatchScanResults(List<ScanResult> results);

    void onScanFailed(int errorCode);
}
