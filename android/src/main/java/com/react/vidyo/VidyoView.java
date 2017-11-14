package com.react.vidyo;

import android.content.Context;
import android.graphics.Canvas;
import android.provider.CalendarContract;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;
import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.VidyoConnector;
import com.vidyo.VidyoClient.Endpoint.VidyoLogRecord;
import com.vidyo.VidyoClient.VidyoNetworkInterface;

public class VidyoView extends ConstraintLayout implements
        VidyoConnector.IConnect,
        VidyoConnector.IRegisterLogEventListener,
        VidyoConnector.IRegisterNetworkInterfaceEventListener {

    private static final String TAG = "VidyoView";

    private enum VIDYO_CONNECTOR_STATE {
        VC_CONNECTED,
        VC_DISCONNECTED,
        VC_DISCONNECTED_UNEXPECTED,
        VC_CONNECTION_FAILURE
    }

    private FrameLayout imageContainer;
    private ImageView endCallButton;
    private ImageView disableCamera;
    private ProgressBar progress;
    private TextView errorText;

    private boolean vidyoClientInitialized = false;
    private VIDYO_CONNECTOR_STATE vidyoConnectorState = VIDYO_CONNECTOR_STATE.VC_DISCONNECTED;
    private VidyoConnector vidyoConnector = null;
    private boolean vidyoConnectorConstructed = false;

    private String host;
    private String token;
    private String userName;
    private String resourceId;
    /** View methods **/

    private final Runnable mLayoutRunnable = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    public VidyoView(@NonNull ThemedReactContext context) {
        super(context);

        setUp();
    }

    public VidyoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public VidyoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        Log.d(TAG, "SET UP");
        inflate(getContext(), R.layout.component_vidyo_view, this);

        setWillNotDraw(false);


        final ThemedReactContext context = (ThemedReactContext) getContext();

        context.addLifecycleEventListener(new LifecycleEventListener() {
            @Override
            public void onHostResume() {
                Log.d(TAG, "RESUME");
                start();
            }

            @Override
            public void onHostPause() {
                Log.d(TAG, "PAUSE");
                stop();
            }

            @Override
            public void onHostDestroy() {
                Log.d(TAG, "DESTROY");
                disconnect();
            }
        });

        setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        imageContainer = (FrameLayout) findViewById(R.id.video_container);
        endCallButton = (ImageView) findViewById(R.id.endCallButton);
        progress = (ProgressBar) findViewById(R.id.connectProgress);
        errorText = (TextView) findViewById(R.id.errorText);
        disableCamera = (ImageView) findViewById(R.id.changeCameraStateButton);

        Connector.SetApplicationUIContext(context.getCurrentActivity());

        vidyoClientInitialized = Connector.Initialize();

        endCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
            }
        });

        disableCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCamera();
            }
        });
    }

    public void setHost(String host) {
        Log.d(TAG, "SET HOST");
        this.host = host;
    }

    public void setToken(String token) {
        Log.d(TAG, "SET TOKEN");
        this.token = token;
    }

    public void setResourceId(String resourceId) {
        Log.d(TAG, "SET RESOURCE ID");
        this.resourceId = resourceId;
    }

    public void setUserName(String userName) {
        Log.d(TAG, "SET USER NAME");
        this.userName = userName;
    }

    public void showButtons(){
        this.disableCamera.setVisibility(VISIBLE);
        this.endCallButton.setVisibility(VISIBLE);
    }

    public void hideButtons(){
        this.endCallButton.setVisibility(GONE);
        this.disableCamera.setVisibility(GONE);
    }


    @Override
    public void requestLayout() {
        super.requestLayout();
        post(mLayoutRunnable);
    }

    public void start() {
        Log.d(TAG, "Start");
        ViewTreeObserver viewTreeObserver = imageContainer.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    imageContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    checkVidyoConnection();

                    EventEmitter.emitVidyoComponentReady((ThemedReactContext) getContext());
                }
            });
            viewTreeObserver.dispatchOnGlobalLayout();
        }

    }

    public void stop(){
        Log.d(TAG, "Stop");
        if (vidyoConnectorConstructed) {
            vidyoConnector.SetMode(VidyoConnector.VidyoConnectorMode.VIDYO_CONNECTORMODE_Background);
        }
    }

    public void disconnect() {
        Log.d(TAG, "Detach");
        if(vidyoConnector != null) {
            vidyoConnector.Disconnect();

        }

        Connector.Uninitialize();
        EventEmitter.emmitVidyoConnectionEnd((ThemedReactContext) getContext());
    }

    public void disableCamera(){
        Log.d(TAG, "Disable camera");
        if(vidyoConnector != null) {
            vidyoConnector.SetCameraPrivacy(false);
        }
    }


    public void enableCamera(){
        Log.d(TAG, "Disable camera");
        if(vidyoConnector != null) {
            vidyoConnector.SetCameraPrivacy(true);
        }
    }

    public void connect() {
        Log.d(TAG, "Connect");
        if (vidyoConnectorState != VIDYO_CONNECTOR_STATE.VC_CONNECTED && vidyoConnector != null) {
            progress.setVisibility(VISIBLE);
            Log.d(TAG, "host: " + host);
            Log.d(TAG, "token: " + token);
            Log.d(TAG, "userName: " + userName);
            Log.d(TAG, "resoureId: " + resourceId);
            if (!vidyoConnector.Connect(
                    host,
                    token,
                    userName,
                    resourceId,
                    this)) {
                onConnectorStateUpdeted(VIDYO_CONNECTOR_STATE.VC_CONNECTION_FAILURE, "Failed to connect");
            }
        }
    }

    private void checkVidyoConnection() {
        if (!vidyoConnectorConstructed) {
            if (vidyoClientInitialized) {
                createVidyoConnector();
                refreshView();
            } else {
                Log.d(TAG, "ERROR: VidyoClientInitialize failed - not constructing VidyoConnector ...");
            }

            Log.d(TAG, "onResume: vidyoConnectorConstructed => " + (vidyoConnectorConstructed ? "success" : "failed"));
        }
    }

    public void createVidyoConnector() {
        vidyoConnector = new VidyoConnector(imageContainer,
                VidyoConnector.VidyoConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default,
                15,
                "info@VidyoClient info@VidyoConnector warning",
                "",
                0);

        vidyoConnectorConstructed = true;

        if (!vidyoConnector.RegisterNetworkInterfaceEventListener(VidyoView.this)) {
            Log.d(TAG, "VidyoConnector RegisterNetworkInterfaceEventListener failed");
        }

        if (!vidyoConnector.RegisterLogEventListener(VidyoView.this, "info@VidyoClient info@VidyoConnector warning")) {
            Log.d(TAG, "VidyoConnector RegisterLogEventListener failed");
        }
    }

    private void refreshView() {
        Log.d(TAG, "refreshView");
        progress.setVisibility(GONE);
        errorText.setVisibility(GONE);

        vidyoConnector.ShowViewAt(imageContainer, 0, 0, imageContainer.getWidth(), imageContainer.getHeight());
    }


    private void hideProgress() {
        progress.setVisibility(GONE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "OnDraw");
        super.onDraw(canvas);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");


    }

    public void restartConnection() {
        Log.d(TAG, "Restart");
        if (vidyoConnectorConstructed) {
            vidyoConnector.SetMode(VidyoConnector.VidyoConnectorMode.VIDYO_CONNECTORMODE_Foreground);
        }
    }

    public void setHeight(int height) {
        getLayoutParams().height = height;
    }

    public void setWidth(int width) {
        getLayoutParams().width = width;
    }


    @Override
    public void OnSuccess() {
        Log.d(TAG, "Success, connected");
        onConnectorStateUpdeted(VIDYO_CONNECTOR_STATE.VC_CONNECTED, "Connected");
    }


    @Override
    public void OnFailure(VidyoConnector.VidyoConnectorFailReason vidyoConnectorFailReason) {
        onConnectorStateUpdeted(VIDYO_CONNECTOR_STATE.VC_CONNECTION_FAILURE, "Failed to connect");
        Log.d(TAG, vidyoConnectorFailReason.toString());
        Log.d(TAG, "On failure = " + vidyoConnectorFailReason.toString());
    }

    @Override
    public void OnDisconnected(VidyoConnector.VidyoConnectorDisconnectReason vidyoConnectorDisconnectReason) {
        VIDYO_CONNECTOR_STATE state;
        String statusText;
        if (vidyoConnectorDisconnectReason == VidyoConnector.VidyoConnectorDisconnectReason.VIDYO_CONNECTORDISCONNECTREASON_Disconnected) {
            Log.d(TAG, "OnDisconnected: successfully disconnected, reason = " + vidyoConnectorDisconnectReason.toString());
            state = VIDYO_CONNECTOR_STATE.VC_DISCONNECTED;
            statusText = "Disconnected";
        } else {
            Log.d(TAG, "OnDisconnected: successfully disconnected, reason = " + vidyoConnectorDisconnectReason.toString());
            state = VIDYO_CONNECTOR_STATE.VC_DISCONNECTED_UNEXPECTED;
            statusText = "Unexpected disconnection";
        }
        onConnectorStateUpdeted(state, statusText);
    }

    @Override
    public void OnLog(VidyoLogRecord vidyoLogRecord) {

    }

    @Override
    public void OnNetworkInterfaceAdded(VidyoNetworkInterface vidyoNetworkInterface) {

    }

    @Override
    public void OnNetworkInterfaceRemoved(VidyoNetworkInterface vidyoNetworkInterface) {

    }

    @Override
    public void OnNetworkInterfaceSelected(VidyoNetworkInterface vidyoNetworkInterface,
                                           VidyoNetworkInterface.VidyoNetworkInterfaceTransportType vidyoNetworkInterfaceTransportType) {

    }

    @Override
    public void OnNetworkInterfaceStateUpdated(VidyoNetworkInterface vidyoNetworkInterface,
                                               VidyoNetworkInterface.VidyoNetworkInterfaceState vidyoNetworkInterfaceState) {

    }

    private void onConnectorStateUpdeted(VIDYO_CONNECTOR_STATE state, final String statusText) {
        Log.d(TAG, "onConnectorStateUpdeted, state = " + state.toString());
        Log.d(TAG, "onConnectorStateUpdeted, state text = " + statusText);

        vidyoConnectorState = state;

        ThemedReactContext context = (ThemedReactContext) getContext();
        if (context != null && context.getCurrentActivity() != null) {
            context.getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    checkConnectionState(vidyoConnectorState);
                }
            });

        }
    }

    private void checkConnectionState(VIDYO_CONNECTOR_STATE state){
        if (state == VIDYO_CONNECTOR_STATE.VC_CONNECTED) {
            onStateConnected();
        } else if (state == VIDYO_CONNECTOR_STATE.VC_CONNECTION_FAILURE) {
            onStateConnectionFailure();
        }
    }

    private void onStateConnected(){
        Log.d(TAG, "State connected");
        hideProgress();
        refreshView();
        EventEmitter.emmitVidyoConnected((ThemedReactContext) getContext());
    }

    private void onStateConnectionFailure(){
        hideProgress();
        errorText.setVisibility(VISIBLE);
        errorText.setText(getContext().getString(R.string.cannot_connect));
        EventEmitter.emmitVidyoConnectionFailure((ThemedReactContext) getContext(), "cannot connect");
    }
}

