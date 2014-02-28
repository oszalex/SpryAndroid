//
//  ViewController.m
//  BRO
//
//  Created by Christians Hotz-Behofsits on 17/02/14.
//  Copyright (c) 2014 Christians Hotz-Behofsits. All rights reserved.
//

#import "ViewController.h"
#import "NXOAuth2AccountStore.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)ListAccounts:(id)sender {
    for (NXOAuth2Account *account in [[NXOAuth2AccountStore sharedStore] accounts]) {
        NSLog(@"account nrnnrnr");
    };
}

- (IBAction)TwitterLogin:(id)sender {
    NSLog(@"test to login");
    
    [[NXOAuth2AccountStore sharedStore] requestAccessToAccountWithType:@"Twitter"
                                                              username:@"inkrement"
                                                              password:@"Vienna09"];
}
- (IBAction)InstagramLogin:(id)sender {
   /* [[NXOAuth2AccountStore sharedStore] requestAccessToAccountWithType:@"Instagram"
                                                              username:@"christian_hotz"
                                                              password:@"12312312a"];
    */
    [[NXOAuth2AccountStore sharedStore] requestAccessToAccountWithType:@"Instagram"];
    
    
}

@end
