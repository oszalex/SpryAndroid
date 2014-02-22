<?php

class BroventUser extends Eloquent {

	protected $table = 'brovent_user';
	public $timestamps = false;
	protected $softDelete = false;

	public function state()
	{
		return $this->belongsTo('State');
	}

}