<?php

class Tag extends Eloquent {

	protected $table = 'tags';
	public $timestamps = false;
	protected $softDelete = false;

	protected $fillable = array('name');

	public function brovents()
	{
		//return $this->morphedByMany('Brovent', 'taggable');
		return $this->morphedByMany('Brovent', 'taggable', 'taggables', 'tag_id', 'taggable_id');
	}

}