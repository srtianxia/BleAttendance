package com.srtianxia.bleattendance.rx;

import android.content.Context;
import android.support.annotation.NonNull;
import java.util.UUID;
import rx.Observable;

/**
 * Created by srtianxia on 2016/11/26.
 */

public abstract class RxPeripheralClient {
    public static RxPeripheralClient create(@NonNull Context context) {
        return RxPeripheralClientImpl.getInstance();
    }

    public abstract Observable<RxPeripheralAdvResult> advertise(String uuid);
}
