/**
 * Component for Twilio Video participant views.
 *
 * Authors:
 *   Jonathan Chang <slycoder@gmail.com>
 */

package com.vidyorn.library;

import android.content.Context;

public class VidyoRemotePreview extends RNVideoViewGroup {

    private static final String TAG = "VidyoRemotePreview";

    public VidyoRemotePreview(Context context) {
        super(context);
        CustomVidyoView.registerPrimaryVideoView(this.getSurfaceViewRenderer());
    }
}
