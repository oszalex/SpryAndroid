//
//  EventTableViewCell.h
//  BRO
//
//  Created by Christians Hotz-Behofsits on 28/02/14.
//  Copyright (c) 2014 Christians Hotz-Behofsits. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface EventTableViewCell : UITableViewCell
@property (nonatomic, strong) IBOutlet UIImageView *eventImage;
@property (nonatomic, strong) IBOutlet UILabel *eventTitle;
@property (nonatomic, strong) IBOutlet UILabel *eventTags;
@property (nonatomic, strong) IBOutlet UILabel *eventTimeLeft;
@end
