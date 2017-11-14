//
//  RNTVideoViewManager.m
//  nutrimedy_app
//
//  Created by Aleksander Maj on 29/09/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNTVideoManager.h"
#import "RNTVideoView.h"
#import "Lmi/VidyoClient/VidyoConnector_Objc.h"

@interface RNTVideoManager () <IConnect, RNTVideoViewDelegate>

@property (nonatomic, assign, getter=isConnected) BOOL connected;
@property (nonatomic, assign, getter=isCameraOn) BOOL cameraOn;
@property (nonatomic, strong) Connector *connector;
@property (nonatomic, weak) RNTVideoView *videoView;

@end

@implementation RNTVideoManager

RCT_EXPORT_MODULE()

RCT_EXPORT_VIEW_PROPERTY(hudHidden, BOOL)
RCT_EXPORT_VIEW_PROPERTY(host, NSString)
RCT_EXPORT_VIEW_PROPERTY(token, NSString)
RCT_EXPORT_VIEW_PROPERTY(displayName, NSString)
RCT_EXPORT_VIEW_PROPERTY(roomId, NSString)
RCT_REMAP_VIEW_PROPERTY(RNTVidyoOnReady, onReady, RCTBubblingEventBlock)
RCT_REMAP_VIEW_PROPERTY(RNTVidyoOnConnect, onConnect, RCTBubblingEventBlock)
RCT_REMAP_VIEW_PROPERTY(RNTVidyoOnDisconnect, onDisconnect, RCTBubblingEventBlock)
RCT_REMAP_VIEW_PROPERTY(RNTVidyoOnFailure, onFailure, RCTBubblingEventBlock)

- (UIView *)view {
    RNTVideoView *view = [RNTVideoView new];
    view.delegate = self;
    self.videoView = view;
    return view;
}

#pragma mark EXPORT methods

RCT_EXPORT_METHOD(connectToRoom) {
    const char *host = [self.videoView.host UTF8String];
    const char *token = [self.videoView.token UTF8String];
    const char *displayName = [self.videoView.displayName UTF8String];
    const char *roomId = [self.videoView.roomId UTF8String];
    
    [self.connector Connect:host Token:token DisplayName:displayName ResourceId:roomId Connect:self];
    [self.videoView setConnecting:YES];
}

RCT_EXPORT_METHOD(disconnect) {
    [self.connector Disconnect];
    [self.videoView setConnected:NO];
}

RCT_EXPORT_METHOD(switchCamera) {
    [self.connector CycleCamera];
}

RCT_EXPORT_METHOD(toggleCamera:(BOOL)enabled) {
    self.cameraOn = enabled;
}

#pragma mark IConnect methods

- (void)OnSuccess {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.videoView setConnecting:NO];
        self.connected = YES;
        if (self.videoView.onConnect) {
            self.videoView.onConnect(@{});
        }
    });
}

- (void)OnFailure:(ConnectorFailReason)reason {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.videoView setConnecting:NO];
        self.connected = NO;
        if (self.videoView.onFailure) {
            self.videoView.onFailure(@{@"reason": @(reason)});
        }
    });
    
}

- (void)OnDisconnected:(ConnectorDisconnectReason)reason {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.videoView setConnecting:NO];
        self.connected = NO;
        if (self.videoView.onDisconnect) {
            self.videoView.onDisconnect(@{@"reason": @(reason)});
        }
    });
}

- (void)viewDidMoveToSuperview {
    [VidyoClientConnector Initialize];
    [self.videoView setNeedsLayout];
    [self.videoView layoutIfNeeded];
    UIView *videoContainerView = self.videoView.videoContainerView;
    self.connector = [[Connector alloc] init:&videoContainerView ViewStyle:CONNECTORVIEWSTYLE_Default RemoteParticipants:16 LogFileFilter:"" LogFileName:"" UserData:0];
    [self.connector ShowViewAt:&videoContainerView X:0 Y:0 Width:videoContainerView.bounds.size.width Height:videoContainerView.bounds.size.height];
    if (self.videoView.onReady) {
        self.videoView.onReady(@{});
    }
}

- (void)cameraButtonTapped:(UIButton *)sender {
    BOOL result = [self.connector SetCameraPrivacy:!self.isCameraOn];
    if (result) {
        self.cameraOn = !self.isCameraOn;
    }
}

- (void)connectButtonTapped:(UIButton *)sender {
    if (self.isConnected) {
        [self disconnect];
    } else {
        [self connectToRoom];
    }
}

- (void)setConnected:(BOOL)connected {
    _connected = connected;
    [self.videoView setConnected:connected];
}

- (void)setCameraOn:(BOOL)cameraOn {
    _cameraOn = cameraOn;
    [self.videoView setCameraOn:cameraOn];
}

@end

