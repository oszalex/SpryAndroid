//
//  EventViewController.m
//  BRO
//
//  Created by Christians Hotz-Behofsits on 28/02/14.
//  Copyright (c) 2014 Christians Hotz-Behofsits. All rights reserved.
//

#import "EventViewController.h"
#import "EventTableViewCell.h"

@interface EventViewController ()

@end

@implementation EventViewController
@synthesize eventImages = _eventImages;
@synthesize eventTags = _eventTags;
@synthesize eventTimeLeft = _eventTimeLeft;
@synthesize eventTitle = _eventTitle;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	

    self.eventTitle = [[NSArray alloc]
                       initWithObjects:@"Fette Fette Feier",
                       @"Kino",
                       @"Paintball",
                       @"irgendwas",nil];
    
    self.eventTimeLeft = [[NSArray alloc]
                          initWithObjects:@"1h",
                          @"2h",
                          @"3t",
                          @"44w", nil];
    
    self.eventTags = [[NSArray alloc]
                      initWithObjects: @"#some #fancy #tags",
                      @"#wolf #of #wallstreet",
                      @"#paintball",
                      @"#something", nil];
    
    self.eventImages = [[NSArray alloc]
                        initWithObjects:@"placeholder.jpg",
                        @"placeholder.jpg",
                        @"placeholder.jpg",
                        @"placeholder.jpg", nil];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}




/* Table view DataSource */


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    
    // Return the number of rows in the section.
    return [self.eventTitle count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"eventTableCell";
    
    EventTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    cell.eventTitle.text = [self.eventTitle objectAtIndex:[indexPath row]];
    
    cell.eventTags.text = [self.eventTags objectAtIndex:[indexPath row]];
    
    cell.eventTimeLeft.text = [self.eventTimeLeft objectAtIndex:[indexPath row]];
    
    UIImage *eventPhoto = [UIImage imageNamed:[self.eventImages objectAtIndex:[indexPath row]]];
    
    cell.eventImage.image = eventPhoto;
    
    
    
    // Configure the cell...
    
    return cell;
}



@end




