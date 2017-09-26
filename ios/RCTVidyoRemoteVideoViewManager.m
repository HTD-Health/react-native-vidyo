//
//  RCTVidyoRemoteVideoViewManager.m
//  Black
//
//  Created by Martín Fernández on 6/13/17.
//
//

#import "RCTVidyoRemoteVideoViewManager.h"

#import <React/RCTConvert.h>
#import "RCTVidyoModule.h"

@interface RCTTWVideoTrackIdentifier : NSObject

@property (strong) NSString *participantIdentity;
@property (strong) NSString *videoTrackId;

@end

@implementation RCTTWVideoTrackIdentifier

@end

@interface RCTConvert(RCTTWVideoTrackIdentifier)

+ (RCTTWVideoTrackIdentifier *)RCTTWVideoTrackIdentifier:(id)json;

@end

@implementation RCTConvert(RCTTWVideoTrackIdentifier)

+ (RCTTWVideoTrackIdentifier *)RCTTWVideoTrackIdentifier:(id)json {
  RCTTWVideoTrackIdentifier *trackIdentifier = [[RCTTWVideoTrackIdentifier alloc] init];
  trackIdentifier.participantIdentity = json[@"participantIdentity"];
  trackIdentifier.videoTrackId = json[@"videoTrackId"];

  return trackIdentifier;
}

@end

@interface RCTVidyoRemoteVideoViewManager()
@end

@implementation RCTVidyoRemoteVideoViewManager

RCT_EXPORT_MODULE()

- (UIView *)view {
  UIView *container = [[UIView alloc] init];
  TVIVideoView *inner = [[TVIVideoView alloc] init];
  inner.contentMode = UIViewContentModeScaleAspectFill;
  [container addSubview:inner];
  return container;
}

RCT_CUSTOM_VIEW_PROPERTY(trackIdentifier, RCTTWVideoTrackIdentifier, TVIVideoView) {
  if (json) {
    RCTVidyoModule *videoModule = [self.bridge moduleForName:@"VidyoModule"];
    RCTTWVideoTrackIdentifier *id = [RCTConvert RCTTWVideoTrackIdentifier:json];

    [videoModule addParticipantView:view.subviews[0] identity:id.participantIdentity trackId:id.videoTrackId];
  }
}


@end
