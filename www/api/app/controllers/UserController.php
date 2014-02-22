<?php

class UserController extends \BaseController {

	/**
	 * Display a listing of the resource.
	 *
	 * @return Response
	 */
	public function index()
	{
		return Response::json(User::all()->toArray());
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
			'email'      => 'required|email',
		);
		$validator = Validator::make(Input::all(), $rules);

		// process the login
		if ($validator->fails()) {
			return "validator error";
		} else {
			// store
			try{
				$user = new User;
				$user->name       = Input::get('name');
				$user->email      = Input::get('email');
			
				$user->save();
			} catch(Exception $e){
				$return['success'] = false;
				$return['error'] = $e->getMessage();

				Log::error($e->__toString());
				return Response::json($return, 500);
			}
			

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
		return Response::json(User::findOrFail($id)->toArray());
	}


	/**
	 * Update the specified resource in storage.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function update($id)
	{
		// validate
		// read more on validation at http://laravel.com/docs/validation

		

		$rules = array(
			'name'       => 'required',
			'email'      => 'required|email',
		);
		$validator = Validator::make(Input::all(), $rules);

		// process the login
		if ($validator->fails()) {
			return "validator error: ". implode(" ", $validator->messages());
		} else {
			// store
			$user = User::find($id);
			$user->name       = Input::get('name');
			$user->email      = Input::get('email');
			$user->save();

			return Response::json($user->toArray());
		}
	}

	/**
	 * Remove the specified resource from storage.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function destroy($id)
	{
		return Response::json(User::findOrFail($id)->delete()->toArray());
	}

}