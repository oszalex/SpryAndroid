<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateBroventUserTable extends Migration {

	public function up()
	{
		Schema::create('brovent_user', function(Blueprint $table) {
			$table->increments('id');
			$table->integer('brovent_id')->unsigned()->index();
			$table->integer('user_id')->unsigned()->index();
			$table->integer('state_id')->unsigned()->index()->default('1');
		});
	}

	public function down()
	{
		Schema::drop('brovent_user');
	}
}