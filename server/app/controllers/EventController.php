<?php

use Carbon\Carbon;

class EventController extends \BaseController {

	/**
	 * Display a listing of the resource.
	 *
	 * @return Response
	 */
	public function index()
	{
		return Response::json(Brovent::all()->toArray());
	}


	/**
	 * Store a newly created resource in storage.
	 *
	 * @return Response
	 */
	public function store()
	{

		$rules = array(
			'name'       => 'required',
			'participant_ids' => 'required',
			'creator_id' => 'required'
		);

		$validator = Validator::make(Input::all(), $rules);

		// process the login
		if ($validator->fails()) {
			return "validator error". $validator->messages();
		} else {

			// store

			$bvt = new Brovent;
			$bvt->name = Input::get('name');
			$bvt->venue_id = (int) Input::get('venue_id');

			/// parse datetime
			$datetime = Input::get('datetime');


			try{
				if(empty($datetime)){
					$dt = Carbon::now();
				} else {
					$dt = Carbon::createFromFormat(DateTime::ISO8601, $datetime);
				}

				$dt_str = $dt->toDateTimeString();
			} catch (Exception $e) {
				//TODO: log error
			    $dt_str = date('Y-m-d H:i:s');
			}

			$bvt->datetime = $dt_str;

			/// associate participants
			$participants = json_decode(Input::get('participant_ids'));

			if(is_array($participants)){
				foreach($participants as $participant_id){
					$p = User::find($participant_id);

					if(! is_null($p))
						$p->events()->attach($p->id);
				}
			}

			/// associate category
			$cat_id = (int) Input::get('category_id');

			// set to default if unset
			if(empty($cat_id)) $cat_id = 1;

			$cat = Category::findOrFail($cat_id);
			//$cat->includes()->save($bvt);
			//$bvt->category()->attach($cat->id);

			$bvt = $cat->includes()->save($bvt);
			

			///associate creator
			//TODO Input::get('creator_id');
			//$user = User::findOrFail(Input::get('creator_id'));

			

			$bvt->save();
			//$user->events()->attach($bvt->id);

			return Response::json($bvt->toArray());
		}
	}

	/**
	 * Display the specified resource.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function show($id)
	{
		return Response::json(Brovent::findOrFail($id)->toArray());
	}


	/**
	 * Update the specified resource in storage.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function update($id)
	{
		//
	}

	/**
	 * Remove the specified resource from storage.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function destroy($id)
	{
		return Response::json(Brovent::findOrFail($id)->delete()->toArray());
	}

}