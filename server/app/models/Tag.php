<?php

class Tag extends Eloquent {

	protected $table = 'tags';
	public $timestamps = false;
	protected $softDelete = false;

	public function brovents()
	{
		return $this->morphedByMany('Brovent', 'taggable');
	}

}