//
//  RNTVideoView.h
//  nutrimedy_app
//
//  Created by Aleksander Maj on 29/09/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTComponent.h>

@protocol RNTVideoViewDelegate <NSObject>
- (void)viewDidMoveToSuperview;
- (void)cameraButtonTapped:(UIButton *)sender;
- (void)connectButtonTapped:(UIButton *)sender;
@end

@interface RNTVideoView : UIView

@property (nonatomic, weak) id<RNTVideoViewDelegate> delegate;
@property (nonatomic, weak, readonly) UIView *videoContainerView;

@property (nonatomic, copy) NSString *host;
@property (nonatomic, copy) NSString *token;
@property (nonatomic, copy) NSString *displayName;
@property (nonatomic, copy) NSString *resourceId;

@property (nonatomic, copy) RCTBubblingEventBlock onConnect;
@property (nonatomic, copy) RCTBubblingEventBlock onDisconnect;
@property (nonatomic, copy) RCTBubblingEventBlock onFailure;


- (void)setConnected:(BOOL)connected;
- (void)setConnecting:(BOOL)connecting;


@end
