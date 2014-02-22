<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateBroventsTable extends Migration {

	public function up()
	{
		Schema::create('brovents', function(Blueprint $table) {
			$table->increments('id');
			$table->timestamps();
			$table->string('name');
			$table->datetime('datetime');
			$table->integer('venue_id')->index();
		});
	}

	public function down()
	{
		Schema::drop('brovents');
	}
}