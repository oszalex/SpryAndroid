<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateForeignKeys extends Migration {

	public function up()
	{
		Schema::table('categories', function(Blueprint $table) {
			$table->foreign('parent_id')->references('id')->on('categories')
						->onDelete('restrict')
						->onUpdate('restrict');
		});
	}

	public function down()
	{
		Schema::table('categories', function(Blueprint $table) {
			$table->dropForeign('categories_parent_id_foreign');
		});
	}
}