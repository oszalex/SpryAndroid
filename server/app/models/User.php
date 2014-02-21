<?php

class User extends Eloquent {

	protected $table = 'users';
	public $timestamps = true;
	protected $softDelete = false;

	public function followers()
	{
		return $this->hasMany('User');
	}

	public function following()
	{
		return $this->belongsToMany('User');
	}

	public function events()
	{
		return $this->hasMany('');
	}

}