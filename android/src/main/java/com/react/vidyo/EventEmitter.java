package com.react.vidyo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by patrykjablonski on 11.10.2017.
 */

class EventEmitter {
    private static final String CONNECTION_STOP = "onDisconnected";
    private static final String CONNECTION_START = "onConnected";
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
            context.getJSModule(RCTEventEmitter.class)
                    .receiveEvent(eventId, eventName, params);
        }
    }
}
