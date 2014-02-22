<?php

class StateTableSeeder extends Seeder {

	public function run()
	{
		//DB::table('states')->delete();

		// invited
		State::create(array(
				'name' => 'invited'
			));

		// yes
		State::create(array(
				'name' => 'yes'
			));

		// no
		State::create(array(
				'name' => 'no'
			));

		// maybe
		State::create(array(
				'name' => 'maybe'
			));
	}
}