package com.srtianxia.bleattendance.presenter;

import android.bluetooth.le.ScanResult;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.ui.fragment.TeacherFragment;
import com.srtianxia.blelibs.BLECentral;
import com.srtianxia.blelibs.callback.OnScanCallback;
import javax.inject.Singleton;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Singleton
public class TeacherPresenter extends BasePresenter<TeacherPresenter.ITeacherView> {
    private BLECentral mBleCentral;


    public TeacherPresenter(ITeacherView baseView) {
        super(baseView);
        mBleCentral = new BLECentral(((TeacherFragment) getView()).getActivity());
    }


    public void startScan() {
        mBleCentral.startScan();
        mBleCentral.setOnScanCallback(new OnScanCallback() {
            @Override public void onScanResult(int callbackType, ScanResult result) {
                getView().addDeviceInfo(result);
            }


            @Override public void onScanFailed(int errorCode) {

            }
        });
    }


    public void stopScan() {
        mBleCentral.stopScan();
    }

    public void startConnect(String macAddress) {
        mBleCentral.connectRemoteDevice(macAddress);
    }

    public void connectListAllDevice() {

    }

    public interface ITeacherView extends BaseView {
        void addDeviceInfo(ScanResult scanResult);

        void showScanFailure(String errorResult);
    }
}
