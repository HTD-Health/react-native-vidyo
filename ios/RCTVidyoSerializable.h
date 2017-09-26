//
//  RCTTWSerialization.h
//  Black
//
//  Created by Martín Fernández on 6/13/17.
//
//

#import <Foundation/Foundation.h>
#import <Vidyo/Vidyo.h>

@protocol RCTVidyoSerializable <NSObject>

- (id)toJSON;

@end

@interface TVIParticipant(RCTVidyoSerializable)<RCTVidyoSerializable>
@end

@interface TVITrack(RCTVidyoSerializable)<RCTVidyoSerializable>
@end
