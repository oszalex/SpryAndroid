<?php

class Brovent extends Eloquent {

	protected $table = 'brovent';
	public $timestamps = true;
	protected $softDelete = false;

	public function place()
	{
		return $this->hasOne('Place');
	}

	public function creator()
	{
		return $this->belongsTo('User');
	}

	public function participants()
	{
		return $this->hasMany('User');
	}

	public function category()
	{
		return $this->hasOne('Category');
	}

}