<?php

class Brovent extends Eloquent {

	protected $table = 'brovents';
	public $timestamps = true;
	protected $softDelete = false;

	public function creator()
	{
		return $this->belongsTo('User');
	}

	public function participants()
	{
		return $this->belongsToMany('User');
	}

	public function tags()
	{
		return $this->morphToMany('Tag', 'taggable');
	}

}