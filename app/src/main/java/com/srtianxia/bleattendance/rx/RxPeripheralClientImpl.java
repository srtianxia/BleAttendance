package com.srtianxia.bleattendance.rx;

import android.bluetooth.BluetoothAdapter;
import com.orhanobut.logger.Logger;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by srtianxia on 2016/11/26.
 */

class RxPeripheralClientImpl extends RxPeripheralClient {
    private RxBleAdapterWrapper mAdapterWrapper;


    RxPeripheralClientImpl(RxBleAdapterWrapper adapterWrapper) {
        this.mAdapterWrapper = adapterWrapper;
    }


    public static RxPeripheralClientImpl getInstance() {
        final RxBleAdapterWrapper adapterWrapper = new RxBleAdapterWrapper(
            BluetoothAdapter.getDefaultAdapter());
        return new RxPeripheralClientImpl(adapterWrapper);
    }


    @Override public Observable<RxPeripheralAdvResult> advertise(String uuid) {
        return initAdvertiser(uuid);
    }


    private Observable<RxPeripheralAdvResult> initAdvertiser(String uuid) {
        RxAdvertiserOperation rxAdvertiserOperation = new RxAdvertiserOperation(mAdapterWrapper);
        Observable.just(rxAdvertiserOperation).subscribe(new Action1<RxAdvertiserOperation>() {
                                                             @Override public void call(RxAdvertiserOperation rxAdvertiserOperation) {
                                                                 rxAdvertiserOperation.run();
                                                             }
                                                         },
            new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.d(throwable);
                }
            });

        return rxAdvertiserOperation.asObservable();
    }
}
