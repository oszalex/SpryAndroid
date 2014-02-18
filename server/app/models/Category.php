<?php

class Category extends Eloquent {

	protected $table = 'categories';
	public $timestamps = true;
	protected $softDelete = false;

	protected $hidden = array('created_at', 'updated_at');

	public function parent()
	{
		return $this->hasOne('Category', 'parent_id');
	}

}