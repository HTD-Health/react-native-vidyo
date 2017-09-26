
#import "RCTVidyoLocalVideoViewManager.h"

#import "RCTVidyoModule.h"

@interface RCTVidyoLocalVideoViewManager()
@end

@implementation RCTVidyoLocalVideoViewManager

RCT_EXPORT_MODULE()

- (UIView *)view {
  UIView *container = [[UIView alloc] init];
  TVIVideoView *inner = [[TVIVideoView alloc] init];
  inner.contentMode = UIViewContentModeScaleAspectFill;
  [container addSubview:inner];
  return container;
}

RCT_CUSTOM_VIEW_PROPERTY(enabled, BOOL, TVIVideoView) {
  if (json) {
    RCTVidyoModule *videoModule = [self.bridge moduleForName:@"VidyoModule"];
    BOOL isEnabled = [RCTConvert BOOL:json];

    if (isEnabled) {
      [videoModule addLocalView:view.subviews[0]];
    } else {
      [videoModule removeLocalView:view.subviews[0]];
    }
  }
}

@end
