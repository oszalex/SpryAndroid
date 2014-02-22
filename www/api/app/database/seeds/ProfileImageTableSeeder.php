<?php

class ProfileImageTableSeeder extends Seeder {

	public function run()
	{
		//DB::table('profileimages')->delete();

		// kitty
		ProfileImage::create(array(
				'path' => 'http://placekitten.com/g/200/300'
			));
	}
}