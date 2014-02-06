//
//  BroModelController.h
//  bro
//
//  Created by Christians Hotz-Behofsits on 05/02/14.
//  Copyright (c) 2014 Christians Hotz-Behofsits. All rights reserved.
//

#import <UIKit/UIKit.h>

@class BroDataViewController;

@interface BroModelController : NSObject <UIPageViewControllerDataSource>

- (BroDataViewController *)viewControllerAtIndex:(NSUInteger)index storyboard:(UIStoryboard *)storyboard;
- (NSUInteger)indexOfViewController:(BroDataViewController *)viewController;

@end
