{{ Form::open(array('route' => 'route.name', 'method' => 'POST')) }}
	<ul>
		<li>
			{{ Form::label('taggable_type', 'Taggable_type:') }}
			{{ Form::text('taggable_type') }}
		</li>
		<li>
			{{ Form::label('taggable_id', 'Taggable_id:') }}
			{{ Form::text('taggable_id') }}
		</li>
		<li>
			{{ Form::label('tag_id', 'Tag_id:') }}
			{{ Form::text('tag_id') }}
		</li>
		<li>
			{{ Form::submit() }}
		</li>
	</ul>
{{ Form::close() }}