/**
 * Component for Twilio Video local views.
 *
 * Authors:
 *   Jonathan Chang <slycoder@gmail.com>
 */

package com.vidyorn.library;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;


public class VidyoPreviewManager extends SimpleViewManager<VidyoPreview> {

    public static final String REACT_CLASS = "RNVidyoPreview";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected VidyoPreview createViewInstance(ThemedReactContext reactContext) {
        return new VidyoPreview(reactContext);
    }
}
