package com.react.vidyo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class EventEmitter {
    private static final String CONNECTION_STOP = "onDisconnect";
    private static final String CONNECTION_START = "onConnect";
    private static final String CONNECTION_FAILURE = "onFailure";

    private static final String FAILURE_DATA = "errorText";

    static void emmitVidyoConnectionEnd(ThemedReactContext context, int viewId){
        emit(context, viewId, CONNECTION_STOP, Arguments.createMap());
    }

    static void emmitVidyoConnected(ThemedReactContext context, int viewId){
        emit(context, viewId, CONNECTION_START, Arguments.createMap());
    }

    static void emmitVidyoConnectionFailure(ThemedReactContext context, int viewId, String errorText){
        WritableMap map = Arguments.createMap();
        map.putString(FAILURE_DATA, errorText);
        emit(context, viewId, CONNECTION_FAILURE, map);
    }

    private static void emit(ThemedReactContext context, int eventId, String eventName, WritableMap params){
        if(context != null) {
            context
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
    }
}
