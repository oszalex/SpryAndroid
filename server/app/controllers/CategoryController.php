<?php

class CategoryController extends \BaseController {

	/**
	 * Display a listing of the resource.
	 *
	 * @return Response
	 */
	public function index()
	{
		return Response::json(Category::all()->toArray());
	}

	/**
	 * Show the form for creating a new resource.
	 *
	 * @return Response
	 */
	public function create()
	{
		//
	}

	/**
	 * Store a newly created resource in storage.
	 *
	 * @return Response
	 */
	public function store()
	{
		$rules = array(
			'name'       => 'required'
		);
		$validator = Validator::make(Input::all(), $rules);

		// process the login
		if ($validator->fails()) {
			return "validator error";
		} else {
			// store
			try{
				$cat = new Category;
				$cat->name = Input::get('name');

				$parent_id = Input::get('parent_id');

				if(empty($parent_id))
					$parent_id = 1; //TODO: bitte nicht hardcoden.

				$cat->save();

				$cat->parent()->update(Category::findOrFail($parent_id)->toArray());
				
			} catch(Exception $e){
				$return['success'] = false;
				$return['error'] = $e->getMessage();

				Log::error($e->__toString());
				return Response::json($return, 500);
			}
			

			return Response::json($cat->toArray());
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
		//
	}

	/**
	 * Show the form for editing the specified resource.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function edit($id)
	{
		//
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
		//
	}

}