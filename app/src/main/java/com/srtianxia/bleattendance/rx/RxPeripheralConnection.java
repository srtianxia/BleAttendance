package com.srtianxia.bleattendance.rx;

import rx.Observable;

/**
 * Created by srtianxia on 2017/1/16.
 */

public interface RxPeripheralConnection {


    Observable<ConnectionState> observeConnectionStateChanges();


    class ConnectionState {

        public static final ConnectionState DISCONNECTED = new ConnectionState("DISCONNECTED");
        public static final ConnectionState CONNECTED = new ConnectionState("CONNECTED");
        private final String description;

        ConnectionState(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "RxBleConnectionState{" + description + '}';
        }
    }
}
