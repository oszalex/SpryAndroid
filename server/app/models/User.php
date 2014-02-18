<?php

class User extends Eloquent {

	protected $table = 'users';
	public $timestamps = true;
	protected $softDelete = false;

	protected $hidden = array('created_at', 'updated_at');

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