//
//  EventTableViewCell.m
//  BRO
//
//  Created by Christians Hotz-Behofsits on 28/02/14.
//  Copyright (c) 2014 Christians Hotz-Behofsits. All rights reserved.
//

#import "EventTableViewCell.h"

@implementation EventTableViewCell
@synthesize eventTitle = _eventTitle;
@synthesize eventTimeLeft = _eventTimeLeft;
@synthesize eventTags = _eventTags;
@synthesize eventImage = _eventImage;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
