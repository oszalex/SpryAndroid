<?php

class DatabaseSeeder extends Seeder {

	public function run()
	{
		Eloquent::unguard();

		$this->call('UserTableSeeder');
		$this->command->info('User table seeded!');

		$this->call('StateTableSeeder');
		$this->command->info('State table seeded!');

		$this->call('ProfileImageTableSeeder');
		$this->command->info('ProfileImage table seeded!');
	}
}