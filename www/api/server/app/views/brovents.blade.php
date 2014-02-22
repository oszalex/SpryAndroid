{{ Form::open(array('route' => 'route.name', 'method' => 'POST')) }}
	<ul>
		<li>
			{{ Form::label('name', 'Name:') }}
			{{ Form::text('name') }}
		</li>
		<li>
			{{ Form::label('datetime', 'Datetime:') }}
			{{ Form::text('datetime') }}
		</li>
		<li>
			{{ Form::label('venue_id', 'Venue_id:') }}
			{{ Form::text('venue_id') }}
		</li>
		<li>
			{{ Form::submit() }}
		</li>
	</ul>
{{ Form::close() }}