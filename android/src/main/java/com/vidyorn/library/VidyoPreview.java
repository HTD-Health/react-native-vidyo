/**
 * Component for Vidyo local views.
 *
 */

package com.vidyorn.library;

import android.content.Context;


public class VidyoPreview extends RNVideoViewGroup {

    private static final String TAG = "VidyoPreview";

    public VidyoPreview(Context context) {
        super(context);
        CustomVidyoView.registerThumbnailVideoView(this.getSurfaceViewRenderer());
        this.getSurfaceViewRenderer().setMirror(true);
        this.getSurfaceViewRenderer().applyZOrder(true);
    }
}
