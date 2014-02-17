<?php

class Category extends Eloquent {

	protected $table = 'categories';
	public $timestamps = true;
	protected $softDelete = false;

	public function parent()
	{
		return $this->hasOne('Category', 'parent_id');
	}

}