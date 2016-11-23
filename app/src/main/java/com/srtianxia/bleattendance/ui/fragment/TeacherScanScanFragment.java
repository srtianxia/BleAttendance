package com.srtianxia.bleattendance.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.polidea.rxandroidble.RxBleScanResult;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.di.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.di.module.TeacherModule;
import com.srtianxia.bleattendance.presenter.TeacherScanPresenter;
import com.srtianxia.bleattendance.ui.adapter.TeacherAdapter;
import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherScanScanFragment extends BaseFragment implements TeacherScanPresenter.ITeacherScanView {
    @Inject
    TeacherScanPresenter mPresenter;
    @BindView(R.id.rv_teacher) RecyclerView rvTeacher;
    @BindView(R.id.fab) FloatingActionButton fab;
    private TeacherAdapter mAdapter;

    //private Subscription mScanSubscription;
    //private RxBleClient mRxBleClient;


    public static TeacherScanScanFragment newInstance() {
        Bundle args = new Bundle();
        TeacherScanScanFragment fragment = new TeacherScanScanFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override protected void initView() {
        DaggerTeacherComponent.builder()
            .teacherModule(new TeacherModule(this))
            .build()
            .inject(this);
        initRecyclerView();
    }


    private void initRecyclerView() {
        rvTeacher.setAdapter(mAdapter = new TeacherAdapter());
        rvTeacher.setLayoutManager(
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mAdapter.setOnItemClickListener(
        //    position -> connect(mAdapter.getDataController().getData(position).getMacAddress()));
    }


    //private void connect(String macAddress) {
    //    RxBleDevice rxBleDevice = mRxBleClient.getBleDevice(
    //        macAddress);
    //    Observable<RxBleConnection> connection = rxBleDevice
    //        .establishConnection(getActivity(), false);
    //
    //    connection
    //        .observeOn(AndroidSchedulers.mainThread())
    //        .compose(bindUntilEvent(FragmentEvent.DESTROY))
    //        .flatMap(
    //            rxBleConnection -> rxBleConnection.setupNotification(Constant.BATTERY_LEVEL_UUID))
    //        //.subscribe(new Action1<byte[]>() {
    //        //    @Override public void call(byte[] bytes) {
    //        //        Log.d("call -->", bytes.toString());
    //        //    }
    //        //}, new Action1<Throwable>() {
    //        //    @Override public void call(Throwable throwable) {
    //        //        Log.d("error -->", "error");
    //        //    }
    //        //});
    //        //.subscribe(this::onConnectionReceived, this::onConnectionFailure);
    //        //.subscribe(this::onConnectionByte, this::onConnectionFailure);
    //        .doOnNext(notificationObservable -> getActivity().runOnUiThread(
    //            this::notificationHasBeenSetUp))
    //        .flatMap(notificationObservable -> notificationObservable)
    //        .observeOn(AndroidSchedulers.mainThread())
    //        .subscribe(this::onNotificationReceived, this::onNotificationSetupFailure);
    //
    //}

    //
    //private void notificationHasBeenSetUp() {
    //    ToastUtil.show(getActivity(), "notificationHasBeenSetUp", true);
    //}
    //
    //
    //private void onNotificationReceived(byte[] bytes) {
    //    ToastUtil.show(getActivity(), "onNotificationReceived", true);
    //}
    //
    //
    //private void onNotificationSetupFailure(Throwable throwable) {
    //    ToastUtil.show(getActivity(), "onNotificationSetupFailure", true);
    //}
    //
    //
    //private void onConnectionReceived(RxBleConnection connection) {
    //    ToastUtil.show(getActivity(), "connect success", true);
    //}
    //
    //
    //private void onConnectionByte(byte[] bytes) {
    //    ToastUtil.show(getActivity(), "byte" + bytes.toString(), false);
    //}
    //
    //
    //private void onConnectionFailure(Throwable throwable) {
    //    ToastUtil.show(getActivity(), "connect failure", true);
    //}


    @Override protected int getLayoutRes() {
        return R.layout.fragment_teacher;
    }


    //private boolean isScanning() {
    //    return mScanSubscription != null;
    //}


    @Override public void addScanResult(RxBleScanResult rxBleScanResult) {
        mAdapter.addData(rxBleScanResult.getBleDevice());
    }


    @Override public void handleScanError(Throwable throwable) {

    }


    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    @OnClick(R.id.fab)
    void startScan() {
        mPresenter.startScan();
        //if (isScanning()) {
        //    mScanSubscription.unsubscribe();
        //} else {
        //    mScanSubscription = mRxBleClient
        //        .scanBleDevices()
        //        .map(rxBleScanResult -> rxBleScanResult.getBleDevice())
        //        .subscribeOn(Schedulers.io())
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .compose(bindUntilEvent(FragmentEvent.DESTROY))
        //        .subscribe(mAdapter::addData, this::onScanFailure);
        //}
    }


    //private void onScanFailure(Throwable throwable) {
    //
    //}

}
