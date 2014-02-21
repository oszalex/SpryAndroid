<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateBroventTable extends Migration {

	public function up()
	{
		Schema::create('brovent', function(Blueprint $table) {
			$table->increments('id');
			$table->timestamps();
			$table->string('name');
			$table->datetime('datetime');
		});
	}

	public function down()
	{
		Schema::drop('brovent');
	}
}