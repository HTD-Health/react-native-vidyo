package com.react.vidyo;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by patrykjablonski on 11.10.2017.
 */

public class VidyoComponent extends com.facebook.react.uimanager.SimpleViewManager<VidyoView> {
    private static final String COMPONENT_NAME = "RNTVideo";
    private VidyoView currentView;

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    protected VidyoView createViewInstance(ThemedReactContext reactContext) {
        currentView = new VidyoView(reactContext);
        return currentView;
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

    @ReactProp(name = "userName")
    public void setUserName(VidyoView view, String userName) {
        view.setUserName(userName);
    }

    @ReactProp(name = "roomId")
    public void setRoomId(VidyoView view, String roomName) {
        view.setResourceId(roomName);
    }

    @ReactProp(name = "hudHidden", defaultBoolean = false)
    public void setHudHidden(VidyoView view, boolean hidden){
        if(hidden){
            view.hideButtons();
        }else{
            view.showButtons();
        }
    }

    @ReactMethod
    public void connectToRoom(){
         currentView.connect();
    }

    @ReactMethod
    public void disconnect(){
        currentView.disconnect();
    }

    @ReactMethod
    public void disableCamera(){
        currentView.disableCamera();
    }
}
