<?php

class Taggable extends Eloquent {

	protected $table = 'taggables';
	public $timestamps = false;
	protected $softDelete = false;

}