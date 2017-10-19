//
//  RNTVideoView.m
//  nutrimedy_app
//
//  Created by Aleksander Maj on 29/09/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNTVideoView.h"
#import "RNTVideoButton.h"

@interface RNTVideoView ()
@property (nonatomic, weak) RNTVideoButton *connectButton;
@property (nonatomic, weak) RNTVideoButton *cameraButton;
@property (nonatomic, weak) UIActivityIndicatorView *activityIndicator;
@property (nonatomic, weak, readwrite) UIView *videoContainerView;
@property (nonatomic, weak) UIView *activityContainer;
@end

@implementation RNTVideoView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self setUp];
    }
    return self;
}

- (void)didMoveToSuperview {
    [super didMoveToSuperview];
    [self.delegate viewDidMoveToSuperview];
}

- (dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

#pragma mark Private methods

- (void)setUp {
    UIView *videoContainerView = [UIView new];
    videoContainerView.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:videoContainerView];
    self.videoContainerView = videoContainerView;
    
    if (!self.hudHidden) {
        RNTVideoButton *connectButton = [RNTVideoButton button];
        connectButton.translatesAutoresizingMaskIntoConstraints = NO;
        [connectButton setImage:[UIImage imageNamed:@"button-disconnect"] forState:UIControlStateSelected];
        [connectButton setImage:[UIImage imageNamed:@"button-connect"] forState:UIControlStateNormal];
        [connectButton setBackgroundImage:[UIImage imageNamed:@"button-oval-red-bg"] forState:UIControlStateSelected];
        [connectButton setBackgroundImage:[UIImage imageNamed:@"button-oval-green-bg"] forState:UIControlStateNormal];
        connectButton.height = 86.0;
        [connectButton addTarget:self action:@selector(didTapConnectButton:) forControlEvents:UIControlEventTouchUpInside];
        
        RNTVideoButton *cameraButton = [RNTVideoButton button];
        cameraButton.translatesAutoresizingMaskIntoConstraints = NO;
        [cameraButton setImage:[UIImage imageNamed:@"button-turn-camera-off"] forState:UIControlStateNormal];
        [cameraButton setBackgroundImage:[UIImage imageNamed:@"button-oval-transparent-bg"] forState:UIControlStateNormal];
        cameraButton.height = 86.0;
        [cameraButton addTarget:self action:@selector(didTapCameraButton:) forControlEvents:UIControlEventTouchUpInside];
        
        UIStackView *buttonsStackView = [UIStackView new];
        buttonsStackView.translatesAutoresizingMaskIntoConstraints = NO;
        buttonsStackView.axis = UILayoutConstraintAxisHorizontal;
        [buttonsStackView addArrangedSubview:connectButton];
        [buttonsStackView addArrangedSubview:cameraButton];
        
        UIImageView *buttonsContainer = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"gradient"]];
        buttonsContainer.contentMode = UIViewContentModeScaleAspectFill;
        buttonsContainer.translatesAutoresizingMaskIntoConstraints = NO;
        buttonsContainer.backgroundColor = [UIColor clearColor];
        buttonsContainer.userInteractionEnabled = YES;
        [buttonsContainer addSubview:buttonsStackView];
        
        [self addSubview:buttonsContainer];
        
        UIActivityIndicatorView *activityIndicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
        activityIndicator.translatesAutoresizingMaskIntoConstraints = NO;
        self.activityIndicator = activityIndicator;
        
        UILabel *activityLabel = [UILabel new];
        activityLabel.translatesAutoresizingMaskIntoConstraints = NO;
        activityLabel.textColor = [UIColor whiteColor];
        activityLabel.text = @"Connecting...";
        
        UIStackView *activityStackView = [UIStackView new];
        activityStackView.translatesAutoresizingMaskIntoConstraints = NO;
        activityStackView.axis = UILayoutConstraintAxisVertical;
        [activityStackView addArrangedSubview:activityIndicator];
        [activityStackView addArrangedSubview:activityLabel];
        
        UIView *activityContainer = [UIView new];
        activityContainer.translatesAutoresizingMaskIntoConstraints = NO;
        activityContainer.backgroundColor = [UIColor colorWithWhite:0.0 alpha:0.5];
        activityContainer.hidden = YES;
        [activityContainer addSubview:activityStackView];
        self.activityContainer = activityContainer;
        
        [self addSubview:activityContainer];
        
        
        NSArray *layoutConstraints = @[[videoContainerView.topAnchor constraintEqualToAnchor:self.topAnchor],
                                       [videoContainerView.bottomAnchor constraintEqualToAnchor:self.bottomAnchor],
                                       [videoContainerView.leadingAnchor constraintEqualToAnchor:self.leadingAnchor],
                                       [videoContainerView.trailingAnchor constraintEqualToAnchor:self.trailingAnchor],
                                       [buttonsStackView.topAnchor constraintEqualToAnchor:buttonsContainer.layoutMarginsGuide.topAnchor],
                                       [buttonsStackView.bottomAnchor constraintEqualToAnchor:buttonsContainer.layoutMarginsGuide.bottomAnchor],
                                       [buttonsStackView.centerXAnchor constraintEqualToAnchor:buttonsContainer.centerXAnchor],
                                       [buttonsStackView.leadingAnchor constraintGreaterThanOrEqualToAnchor:buttonsContainer.layoutMarginsGuide.leadingAnchor],
                                       [buttonsContainer.bottomAnchor constraintEqualToAnchor:self.bottomAnchor constant:0.0],
                                       [buttonsContainer.leadingAnchor constraintEqualToAnchor:self.leadingAnchor constant:0.0],
                                       [buttonsContainer.trailingAnchor constraintEqualToAnchor:self.trailingAnchor constant:0.0],
                                       [activityStackView.topAnchor constraintEqualToAnchor:activityContainer.layoutMarginsGuide.topAnchor],
                                       [activityStackView.bottomAnchor constraintEqualToAnchor:activityContainer.layoutMarginsGuide.bottomAnchor],
                                       [activityStackView.leadingAnchor constraintEqualToAnchor:activityContainer.layoutMarginsGuide.leadingAnchor],
                                       [activityStackView.trailingAnchor constraintEqualToAnchor:activityContainer.layoutMarginsGuide.trailingAnchor],
                                       [activityContainer.centerXAnchor constraintEqualToAnchor:self.centerXAnchor],
                                       [activityContainer.centerYAnchor constraintEqualToAnchor:self.centerYAnchor],
                                       [activityLabel.widthAnchor constraintEqualToConstant:activityLabel.intrinsicContentSize.width]];
        
        [NSLayoutConstraint activateConstraints:layoutConstraints];
        
        self.connectButton = connectButton;
        self.cameraButton = cameraButton;
        
    } else {
        NSArray *layoutConstraints = @[[videoContainerView.topAnchor constraintEqualToAnchor:self.topAnchor],
                                       [videoContainerView.bottomAnchor constraintEqualToAnchor:self.bottomAnchor],
                                       [videoContainerView.leadingAnchor constraintEqualToAnchor:self.leadingAnchor],
                                       [videoContainerView.trailingAnchor constraintEqualToAnchor:self.trailingAnchor]];
        [NSLayoutConstraint activateConstraints:layoutConstraints];
    }
}

- (void)didTapConnectButton:(UIButton *)sender {
    if ([self.delegate respondsToSelector:@selector(connectButtonTapped:)]) {
        [self.delegate connectButtonTapped:sender];
    }
}

- (void)didTapCameraButton:(UIButton *)sender {
    if ([self.delegate respondsToSelector:@selector(cameraButtonTapped:)]) {
        [self.delegate cameraButtonTapped:sender];
    }
}

- (void)setConnecting:(BOOL)connecting {
    self.connectButton.enabled = !connecting;
    self.activityContainer.hidden = !connecting;
    if (connecting) {
        [self.activityIndicator startAnimating];
    } else {
        [self.activityIndicator stopAnimating];
    }
}

- (void)setConnected:(BOOL)connected {
    [self.connectButton setSelected:connected];
}

- (void)setCameraOn:(BOOL)on {
    [self.cameraButton setSelected:on];
}

@end

