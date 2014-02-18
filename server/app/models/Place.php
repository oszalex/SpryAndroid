<?php

class Place extends Eloquent {

	protected $table = 'place';
	public $timestamps = true;
	protected $softDelete = false;

	protected $hidden = array('created_at', 'updated_at');

}