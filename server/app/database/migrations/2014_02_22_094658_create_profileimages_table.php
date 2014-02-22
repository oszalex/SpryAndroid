<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateProfileimagesTable extends Migration {

	public function up()
	{
		Schema::create('profileimages', function(Blueprint $table) {
			$table->increments('id');
			$table->string('path');
		});
	}

	public function down()
	{
		Schema::drop('profileimages');
	}
}