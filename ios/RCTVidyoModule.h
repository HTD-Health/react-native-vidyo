//
//  RCTVidyoModule.h
//  Black
//
//  Created by Martín Fernández on 6/13/17.
//
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

#import <Vidyo/Vidyo.h>

@interface RCTVidyoModule : RCTEventEmitter <RCTBridgeModule>

//- (void)addLocalView:(TVIVideoView *)view;
//- (void)removeLocalView:(TVIVideoView *)view;
- (void)addParticipantView:(TVIVideoView *)view identity:(NSString *)identity trackId:(NSString *)trackId;

@end
