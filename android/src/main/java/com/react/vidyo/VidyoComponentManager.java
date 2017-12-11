package com.react.vidyo;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by patrykjablonski on 11.10.2017.
 */

public class VidyoComponentManager extends ViewGroupManager<VidyoView> {
    private static final String COMPONENT_NAME = "RNTVideo";

    public static final int COMMAND_CONNECT_TO_ROOM = 1;
    public static final int COMMAND_DISCONNECT = 2;
    public static final int COMMAND_TOGGLE_CAMERA = 3;
    public static final int COMMAND_DEINITIALIZE = 4;
    public static final int COMMAND_TOGGLE_MIC = 5;

    public VidyoComponentManager() {
        super();
    }


    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    protected VidyoView createViewInstance(ThemedReactContext reactContext) {
        return new VidyoView(reactContext);
    }

    @ReactProp(name = "height", defaultInt = 400)
    public void setHeight(VidyoView view, int height) {
        view.setHeight(height);
    }

    @ReactProp(name = "width", defaultInt = 400)
    public void setWidth(VidyoView view, int width) {
        view.setWidth(width);
    }

    @ReactProp(name = "token")
    public void setToken(VidyoView view, String token) {
        view.setToken(token);
    }

    @ReactProp(name = "host")
    public void setHost(VidyoView view, String host) {
        view.setHost(host);
    }

    @ReactProp(name = "displayName")
    public void setUserName(VidyoView view, String userName) {
        view.setUserName(userName);
    }

    @ReactProp(name = "roomId")
    public void setRoomId(VidyoView view, String roomName) {
        view.setResourceId(roomName);
    }

    @ReactProp(name = "hudHidden", defaultBoolean = false)
    public void setHudHidden(VidyoView view, boolean hidden) {
        if (hidden) {
            view.hideButtons();
        } else {
            view.showButtons();
        }
    }

    public void toggleCamera(VidyoView view, boolean enabled){
        if(!enabled) {
            view.disableCamera();
        } else {
            view.enableCamera();
        }
    }

    public void toggleMic(VidyoView view, boolean enabled){
        if(enabled) {
            view.enableMic();
        } else {
            view.disableMic();
        }
    }


    public void connectToRoom(VidyoView view) {
        view.connect();
    }


    public void disconnect(VidyoView view) {
        view.disconnect();
    }


    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.<String, Integer>builder()
                .put("connectToRoom", COMMAND_CONNECT_TO_ROOM)
                .put("disconnect", COMMAND_DISCONNECT)
                .put("toggleCamera", COMMAND_TOGGLE_CAMERA)
                .put( "toggleMic", COMMAND_TOGGLE_MIC)
                .put("deinitialize", COMMAND_DEINITIALIZE)
                .build();
    }

    @Override
    public void receiveCommand(VidyoView root, int commandId, @Nullable ReadableArray args) {
        super.receiveCommand(root, commandId, args);
        switch (commandId) {
            case COMMAND_CONNECT_TO_ROOM:
                connectToRoom(root);
                break;
            case COMMAND_DISCONNECT:
                disconnect(root);
                break;
            case COMMAND_TOGGLE_CAMERA:
                if(args != null && args.size() > 0) {
                    toggleCamera(root, args.getBoolean(0));
                }
                break;
            case COMMAND_TOGGLE_MIC:
                if(args != null && args.size() > 0) {
                    toggleMic(root, args.getBoolean(0));
                }
                break;
            case COMMAND_DEINITIALIZE:
                root.deinitialize();
                break;
        }
    }
}
