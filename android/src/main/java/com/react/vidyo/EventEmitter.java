package com.react.vidyo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;

class EventEmitter {
    private static final String CONNECTION_STOP = "RNTVidyoOnDisconnect";
    private static final String CONNECTION_START = "RNTVidyoOnConnect";
    private static final String CONNECTION_FAILURE = "RNTVidyoOnFailure";
    private static final String COMPONENT_INIT_FAILED = "RNTVidyoInitFailed";
    private static final String COMPONENT_READY = "RNTVidyoOnReady";

    private static final String FAILURE_DATA = "errorText";

    static void emmitVidyoConnectionEnd(ThemedReactContext context){
        emit(context, CONNECTION_STOP, Arguments.createMap());
    }

    static void emmitVidyoConnected(ThemedReactContext context){
        emit(context, CONNECTION_START, Arguments.createMap());
    }

    static void emmitVidyoConnectionFailure(ThemedReactContext context, String errorText){
        WritableMap map = Arguments.createMap();
        map.putString(FAILURE_DATA, errorText);
        emit(context, CONNECTION_FAILURE, map);
    }

    static void emitVidyoComponentReady(ThemedReactContext context){
        emit(context, COMPONENT_READY, Arguments.createMap());
    }

    static void emitVidyoComponentInitFailed(ThemedReactContext context){
        emit(context, COMPONENT_INIT_FAILED, Arguments.createMap());
    }

    private static void emit(ThemedReactContext context, String eventName, WritableMap params){
        if(context != null) {
            context
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
    }
}
