package com.srtianxia.bleattendance.ui.teacher.attendance;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.RxBleScanResult;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.config.BleUUID;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;
import com.srtianxia.bleattendance.utils.ToastUtil;
import com.srtianxia.bleattendance.utils.TransformUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT32;
import static com.srtianxia.bleattendance.config.Constant.NOTIFY_OFFSET;
import static com.trello.rxlifecycle.android.FragmentEvent.DESTROY;
import static com.trello.rxlifecycle.android.FragmentEvent.PAUSE;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Singleton
public class TeacherScanPresenter extends BasePresenter<TeacherScanPresenter.ITeacherScanView> {
    private TeacherScanModel mTeacherScanModel;
    private Subscription mScanSubscription;
    private Observable<RxBleConnection> mRxBleConnection;
    private PublishSubject<Void> mDisconnectTriggerSubject = PublishSubject.create();
    private RxBleDevice mRxBleDevice;

    private int position = 0;


    private List<RxBleDevice> deviceList;

    private static final String UUID_SETUP_NOTIFY = BleUUID.ATTENDANCE_NOTIFY_WRITE;
    private static final String UUID_WRITE = BleUUID.ATTENDANCE_NOTIFY_WRITE;

    @Inject
    public TeacherScanPresenter(ITeacherScanView baseView) {
        super(baseView);
        mTeacherScanModel = new TeacherScanModel(getViewType().getActivity());
        EventBus.getDefault().register(this);
    }


    @Override
    public TeacherScanFragment getViewType() {
        return (TeacherScanFragment) getView();
    }


    public void startScan() {
        if (isScanning()) {
            mScanSubscription.unsubscribe();
        } else {
            mScanSubscription = mTeacherScanModel
                    .startScan()
                    .compose(getViewType().bindUntilEvent(DESTROY))
                    .compose(RxSchedulersHelper.io2main())
                    .subscribe(this::scanResult, this::handleScanError);
        }
    }


    private void scanResult(RxBleScanResult rxBleScanResult) {
        getView().addScanResult(rxBleScanResult);
    }


    //一次性连接列表中的设备
    public void queueToConnect(List<RxBleDevice> deviceList) {
//        for (RxBleDevice device : deviceList) {
//            prepareConnection(device.getMacAddress());
//            registerConnectStateCallBack();
//            connect();
//        }
        this.deviceList = deviceList;
        connectAll();
    }


    private void handleScanError(Throwable throwable) {
        getView().handleScanError(throwable);
    }


    public void tryToConnect(String macAddress) {
        prepareConnection(macAddress);
        registerConnectStateCallBack();
        connect();
    }


    private void prepareConnection(String macAddress) {
        mRxBleDevice = mTeacherScanModel.getBleDevice(macAddress);
        mRxBleConnection = mRxBleDevice.establishConnection(getViewType().getActivity(), false)
                .takeUntil(mDisconnectTriggerSubject)
                .compose(getViewType().bindUntilEvent(PAUSE))
                .compose(new ConnectionSharingAdapter());
    }


    private void connect() {
        mRxBleConnection.subscribe(
                rxBleConnection -> {
                    getViewType().getActivity().runOnUiThread(this::onConnect);
                    registerNotification();
                }, this::onConnectFailure);
    }


    private void connectAll() {
        if (position > deviceList.size()) {
            return;
        }
        String address = deviceList.get(position).getMacAddress();
        prepareConnection(address);
        registerConnectStateCallBack();
        connect();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(NotificationEvent event) {
        position++;
        if (position <= deviceList.size()) {
            disconnect();
            connectAll();
        }
    }


    private void registerConnectStateCallBack() {
        mRxBleDevice.observeConnectionStateChanges()
                .compose(getViewType().bindUntilEvent(DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnectionStateCallBack);
    }


    private void registerNotification() {
        if (isConnected()) {
            mRxBleConnection.flatMap(
                    rxBleConnection -> rxBleConnection.setupNotification(
                            UUID.fromString(UUID_SETUP_NOTIFY)))
                    .doOnNext(notificationObservable -> getViewType().getActivity()
                            .runOnUiThread(this::notificationHasBeenSetUp))
                    .flatMap(notificationObservable -> notificationObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onNotificationReceived, this::onNotificationSetupFailure);
        }
    }


    private void onConnectionStateCallBack(RxBleConnection.RxBleConnectionState state) {
        ToastUtil.show(getViewType().getActivity(), state.toString(), true);
    }


    public void write() {
        if (isConnected()) {
            mRxBleConnection
                    .flatMap(rxBleConnection -> rxBleConnection.writeCharacteristic(
                            UUID.fromString(UUID_WRITE),
                            "1".getBytes()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onWrite, this::onWriteError);
        }
    }


    public void disconnect() {
        mDisconnectTriggerSubject.onNext(null);
    }


    private void onWrite(byte[] bytes) {
        ToastUtil.show(getViewType().getActivity(), "write", true);
    }


    private void onWriteError(Throwable throwable) {
        ToastUtil.show(getViewType().getActivity(), "write error" + throwable, true);
    }


    private void onNotificationReceived(byte[] bytes) {
        ToastUtil.show(getViewType().getActivity(), String.valueOf(TransformUtils.bytes2int(bytes, FORMAT_UINT32, NOTIFY_OFFSET)), true);
        EventBus.getDefault().post(new NotificationEvent());
    }


    private void onNotificationSetupFailure(Throwable throwable) {
        Logger.d(throwable);
//        ToastUtil.show(getViewType().getActivity(), throwable.getMessage(), false);
    }


    private void notificationHasBeenSetUp() {
        ToastUtil.show(getViewType().getActivity(), "notificationHasBeenSetUp", true);
    }


    private void onConnect() {
        ToastUtil.show(getViewType().getActivity(), "connectSuccess", true);
    }


    private void onConnectFailure(Throwable throwable) {
        ToastUtil.show(getViewType().getActivity(), throwable.toString(), true);
    }


    private boolean isScanning() {
        return mScanSubscription != null;
    }


    private boolean isConnected() {
        return mRxBleDevice != null
                ? mRxBleDevice.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED
                : false;
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    public interface ITeacherScanView extends BaseView {
        void addScanResult(RxBleScanResult rxBleScanResult);

        void handleScanError(Throwable throwable);
    }
}
