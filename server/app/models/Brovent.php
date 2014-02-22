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
		//return $this->morphToMany('Tag', 'taggable');
		 return $this->morphToMany('Tag', 'taggable', 'taggables', 'taggable_id', 'tag_id');
	}


	public function toArray(){
		$old = parent::toArray();

		$tags = [];
		foreach($this->tags()->get() as $tag){
			$tags[] = $tag->name;
		}


		$old["tags"] = $tags;

		return $old;
	}

}