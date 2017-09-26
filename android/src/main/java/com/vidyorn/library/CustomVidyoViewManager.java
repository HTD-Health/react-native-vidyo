/**
 * Component to orchestrate the Twilio Video connection and the various video
 * views.
 *
 * Authors:
 */
package com.vidyorn.library;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import static com.vidyorn.library.CustomVidyoView.Events.ON_AUDIO_CHANGED;
import static com.vidyorn.library.CustomVidyoView.Events.ON_CAMERA_SWITCHED;
import static com.vidyorn.library.CustomVidyoView.Events.ON_CONNECTED;
import static com.vidyorn.library.CustomVidyoView.Events.ON_CONNECT_FAILURE;
import static com.vidyorn.library.CustomVidyoView.Events.ON_DISCONNECTED;
import static com.vidyorn.library.CustomVidyoView.Events.ON_PARTICIPANT_CONNECTED;
import static com.vidyorn.library.CustomVidyoView.Events.ON_PARTICIPANT_DISCONNECTED;
import static com.vidyorn.library.CustomVidyoView.Events.ON_VIDEO_CHANGED;
import static com.vidyorn.library.CustomVidyoView.Events.ON_PARTICIPANT_ADDED_VIDEO_TRACK;
import static com.vidyorn.library.CustomVidyoView.Events.ON_PARTICIPANT_REMOVED_VIDEO_TRACK;

public class CustomVidyoViewManager extends SimpleViewManager<CustomVidyoView> {
    public static final String REACT_CLASS = "RNCustomVidyoView";

    private static final int CONNECT_TO_ROOM = 1;
    private static final int DISCONNECT = 2;
    private static final int SWITCH_CAMERA = 3;
    private static final int TOGGLE_VIDEO = 4;
    private static final int TOGGLE_SOUND = 5;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected CustomVidyoView createViewInstance(ThemedReactContext reactContext) {
        return new CustomVidyoView(reactContext);
    }

    @Override
    public void receiveCommand(CustomVidyoView view, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case CONNECT_TO_ROOM:
                String roomName = args.getString(0);
                String accessToken = args.getString(1);
                view.connectToRoomWrapper(roomName, accessToken);
                break;
            case DISCONNECT:
                view.disconnect();
                break;
            case SWITCH_CAMERA:
                view.switchCamera();
                break;
            case TOGGLE_VIDEO:
                Boolean videoEnabled = args.getBoolean(0);
                view.toggleVideo(videoEnabled);
                break;
            case TOGGLE_SOUND:
                Boolean audioEnabled = args.getBoolean(0);
                view.toggleAudio(audioEnabled);
                break;
        }
    }

    @Override
    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        Map<String, Map<String, String>> map = MapBuilder.of(
                ON_CAMERA_SWITCHED, MapBuilder.of("registrationName", ON_CAMERA_SWITCHED),
                ON_VIDEO_CHANGED, MapBuilder.of("registrationName", ON_VIDEO_CHANGED),
                ON_AUDIO_CHANGED, MapBuilder.of("registrationName", ON_AUDIO_CHANGED),
                ON_CONNECTED, MapBuilder.of("registrationName", ON_CONNECTED),
                ON_CONNECT_FAILURE, MapBuilder.of("registrationName", ON_CONNECT_FAILURE),
                ON_DISCONNECTED, MapBuilder.of("registrationName", ON_DISCONNECTED),
                ON_PARTICIPANT_CONNECTED, MapBuilder.of("registrationName", ON_PARTICIPANT_CONNECTED)
        );

        map.putAll(MapBuilder.of(
                ON_PARTICIPANT_DISCONNECTED, MapBuilder.of("registrationName", ON_PARTICIPANT_DISCONNECTED),
                ON_PARTICIPANT_ADDED_VIDEO_TRACK, MapBuilder.of("registrationName", ON_PARTICIPANT_ADDED_VIDEO_TRACK),
                ON_PARTICIPANT_REMOVED_VIDEO_TRACK, MapBuilder.of("registrationName", ON_PARTICIPANT_REMOVED_VIDEO_TRACK)
        ));

        return map;
    }

    @Override
    @Nullable
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
            "connectToRoom", CONNECT_TO_ROOM,
            "disconnect", DISCONNECT,
            "switchCamera", SWITCH_CAMERA,
            "toggleVideo", TOGGLE_VIDEO,
            "toggleSound", TOGGLE_SOUND
        );
    }
}
