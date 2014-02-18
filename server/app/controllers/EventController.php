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
			return "validator error";
		} else {

			// store

			$bvt = new Brovent;
			$bvt->name = Input::get('name');
			$bvt->venue_id = (int) Input::get('venue_id');

			/// parse datetime
			$datetime = Input::get('datetime');

			if(empty($datetime)){
				$dt = carbon::now();
			} else {
				$dt = Carbon::createFromFormat('Y-m-d\TH:i:sO', $datetime);
			}

			$bvt->datetime = $dt->toDateTimeString();

			/// associate participants
			$participants = json_decode(Input::get('participant_ids'));

			if(is_array($participants)){
				foreach($participants as $participant_id){
					//TODO: asociate object
				}
			}

			/// associate category
			//test if exists. if not set default

			///associate creator
			//TODO Input::get('creator_id');

			$user->save();

			return Response::json($user->toArray());
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