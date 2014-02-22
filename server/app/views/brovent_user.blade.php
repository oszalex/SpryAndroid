{{ Form::open(array('route' => 'route.name', 'method' => 'POST')) }}
	<ul>
		<li>
			{{ Form::label('brovent_id', 'Brovent_id:') }}
			{{ Form::text('brovent_id') }}
		</li>
		<li>
			{{ Form::label('users_id', 'Users_id:') }}
			{{ Form::text('users_id') }}
		</li>
		<li>
			{{ Form::label('state_id', 'State_id:') }}
			{{ Form::text('state_id') }}
		</li>
		<li>
			{{ Form::submit() }}
		</li>
	</ul>
{{ Form::close() }}