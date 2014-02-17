<?php

class CategoryTableSeeder extends Seeder {

	public function run()
	{
		//DB::table('categories')->delete();

		// root category
		Category::create(array(
				'name' => "all"
			));
	}
}