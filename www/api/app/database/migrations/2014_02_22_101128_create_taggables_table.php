<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateTaggablesTable extends Migration {

	public function up()
	{
		Schema::create('taggables', function(Blueprint $table) {
			$table->increments('id');
			$table->string('taggable_type')->index();
			$table->integer('taggable_id')->unsigned()->index();
			$table->integer('tag_id')->unsigned()->index();
		});
	}

	public function down()
	{
		Schema::drop('taggables');
	}
}