//
//  RNTVideoButton.m
//  nutrimedy_app
//
//  Created by Aleksander Maj on 10/10/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNTVideoButton.h"

@interface RNTVideoButton ()
@property (weak, nonatomic) NSLayoutConstraint *heightConstraint;
@end

@implementation RNTVideoButton

+ (instancetype)button {
  RNTVideoButton *button = [RNTVideoButton  buttonWithType:UIButtonTypeCustom];
  [button setUp];
  return button;
}

#pragma mark - Private methods

- (void)setUp {
  self.translatesAutoresizingMaskIntoConstraints = NO;
  NSArray *layoutConstraints = @[[self.widthAnchor constraintEqualToAnchor:self.heightAnchor multiplier:1.0]];
  [NSLayoutConstraint activateConstraints:layoutConstraints];
  self.backgroundColor = nil;
}

- (void)setHeight:(CGFloat)height {
  if (self.heightConstraint == nil) {
    NSLayoutConstraint *heightConstraint = [self.heightAnchor constraintEqualToConstant:height];
    heightConstraint.active = YES;
    self.heightConstraint = heightConstraint;
  } else {
    self.heightConstraint.constant = height;
  }
}

@end
