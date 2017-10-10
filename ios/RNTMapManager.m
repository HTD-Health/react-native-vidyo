//
//  RNTMapManager.m
//  nutrimedy_app
//
//  Created by Aleksander Maj on 29/09/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNTMapManager.h"
#import <MapKit/MapKit.h>

@implementation RNTMapManager

RCT_EXPORT_MODULE()

- (UIView *)view {
  return [MKMapView new];
}

@end
