package com.srtianxia.bleattendance.presenter;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import com.srtianxia.bleattendance.App;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.blelibs.BLECentral;
import com.srtianxia.blelibs.callback.OnScanCallback;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherPresenter extends BasePresenter<TeacherPresenter.ITeacherView> {
    private BLECentral mBleCentral;

    public TeacherPresenter(ITeacherView baseView) {
        super(baseView);
        mBleCentral = new BLECentral(App.getContext());
    }

    public void startScan() {
        mBleCentral.startScan();
        mBleCentral.setOnScanCallback(new OnScanCallback() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override public void onScanResult(int callbackType, ScanResult result) {
                getView().addDeviceInfo(result);
            }


            @Override public void onBatchScanResults(List<ScanResult> results) {

            }


            @Override public void onScanFailed(int errorCode) {

            }
        });
    }

    public void stopScan() {
        mBleCentral.stopScan();
    }

    public interface ITeacherView extends BaseView {
        void addDeviceInfo(ScanResult scanResult);
        void showScanFailure(int errorCode);
    }
}
