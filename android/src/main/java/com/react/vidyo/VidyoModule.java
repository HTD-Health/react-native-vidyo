package com.react.vidyo;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

/**
 * Created by patrykjablonski on 14.11.2017.
 */

public class VidyoModule extends ReactContextBaseJavaModule {
    private static final String VIDYO_MANAGER = "RNTVidyoManager";

    public VidyoModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return VIDYO_MANAGER;
    }
}