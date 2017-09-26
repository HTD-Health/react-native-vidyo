/**
 * Component for Twilio Video participant views.
 *
 * Authors:
 *   Jonathan Chang <slycoder@gmail.com>
 */

package com.vidyorn.library;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;


public class VidyoRemotePreviewManager extends SimpleViewManager<VidyoRemotePreview> {

    public static final String REACT_CLASS = "RNVidyoRemotePreview";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected VidyoRemotePreview createViewInstance(ThemedReactContext reactContext) {
        return new VidyoRemotePreview(reactContext);
    }
}
