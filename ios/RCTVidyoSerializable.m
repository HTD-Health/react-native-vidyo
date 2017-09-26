//
//  RCTTWSerialization.m
//  Black
//
//  Created by Martín Fernández on 6/13/17.
//
//

#import "RCTVidyoSerializable.h"

@implementation TVIParticipant(RCTVidyoSerializable)

- (id)toJSON {
  return @{ @"identity": self.identity };
}

@end

@implementation TVITrack(RCTVidyoSerializable)

- (id)toJSON {
  return @{ @"trackId": self.trackId };
}

@end
