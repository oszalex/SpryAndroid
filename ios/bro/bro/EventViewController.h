//
//  EventViewController.h
//  BRO
//
//  Created by Christians Hotz-Behofsits on 28/02/14.
//  Copyright (c) 2014 Christians Hotz-Behofsits. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface EventViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, strong) NSArray *eventImages;
@property (nonatomic, strong) NSArray *eventTitle;
@property (nonatomic, strong) NSArray *eventTags;
@property (nonatomic,strong) NSArray *eventTimeLeft;

@end
