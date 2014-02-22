<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateForeignKeys extends Migration {

	public function up()
	{
		Schema::table('users', function(Blueprint $table) {
			$table->foreign('profileimage_id')->references('id')->on('profileimages')
						->onDelete('restrict')
						->onUpdate('restrict');
		});
		Schema::table('brovents', function(Blueprint $table) {
			$table->foreign('creator_id')->references('id')->on('users')
						->onDelete('restrict')
						->onUpdate('restrict');
		});
		Schema::table('brovent_user', function(Blueprint $table) {
			$table->foreign('brovent_id')->references('id')->on('brovents')
						->onDelete('restrict')
						->onUpdate('restrict');
		});
		Schema::table('brovent_user', function(Blueprint $table) {
			$table->foreign('user_id')->references('id')->on('users')
						->onDelete('restrict')
						->onUpdate('restrict');
		});
		Schema::table('brovent_user', function(Blueprint $table) {
			$table->foreign('state_id')->references('id')->on('states')
						->onDelete('restrict')
						->onUpdate('restrict');
		});
	}

	public function down()
	{
		Schema::table('users', function(Blueprint $table) {
			$table->dropForeign('users_profileimage_id_foreign');
		});
		Schema::table('brovents', function(Blueprint $table) {
			$table->dropForeign('brovents_creator_id_foreign');
		});
		Schema::table('brovent_user', function(Blueprint $table) {
			$table->dropForeign('brovent_user_brovent_id_foreign');
		});
		Schema::table('brovent_user', function(Blueprint $table) {
			$table->dropForeign('brovent_user_user_id_foreign');
		});
		Schema::table('brovent_user', function(Blueprint $table) {
			$table->dropForeign('brovent_user_state_id_foreign');
		});
	}
}