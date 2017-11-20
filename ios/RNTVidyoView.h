//
//  RNTVidyoView.h
//  nutrimedy_app
//
//  Created by Aleksander Maj on 29/09/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTComponent.h>

@protocol RNTVidyoViewDelegate <NSObject>
- (void)viewDidMoveToSuperview;
- (void)cameraButtonTapped:(UIButton *)sender;
- (void)connectButtonTapped:(UIButton *)sender;
@end

@interface RNTVidyoView : UIView

@property (nonatomic, weak) id<RNTVidyoViewDelegate> delegate;
@property (nonatomic, weak, readonly) UIView *videoContainerView;

@property (nonatomic, assign) BOOL hudHidden;
@property (nonatomic, copy) NSString *host;
@property (nonatomic, copy) NSString *token;
@property (nonatomic, copy) NSString *displayName;
@property (nonatomic, copy) NSString *roomId;

@property (nonatomic, copy) RCTBubblingEventBlock onReady;
@property (nonatomic, copy) RCTBubblingEventBlock onInitFailed;
@property (nonatomic, copy) RCTBubblingEventBlock onConnect;
@property (nonatomic, copy) RCTBubblingEventBlock onDisconnect;
@property (nonatomic, copy) RCTBubblingEventBlock onFailure;

- (void)setConnected:(BOOL)connected;
- (void)setConnecting:(BOOL)connecting;
- (void)setCameraOn:(BOOL)on;

@end

