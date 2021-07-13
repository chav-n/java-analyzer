public		class	MeanTimeLogger
{
	public		static	final	int			REPLACEMENT=0;
	public		static	final	int			SKIP=1;
	public		static	final	int			NEXT=2;
	private		double[]			time_buffer;
	private		int					time_buffer_count;
	private		int					test_size;
	private		double				test_duration;
	private		int					last_index;
	public		MeanTimeLogger ( int bs, int ts )
	{
		time_buffer			= new double[bs];
		time_buffer[ 0]		= Double.MAX_VALUE;
		last_index			=  0;
		time_buffer_count	=  1;
		test_size			= ts;
	}
	public		int		log ( double time_next )
	{
		if	( time_buffer[last_index] / 1.25  > time_next )
		{
			time_buffer[last_index]  = time_next;
			return	REPLACEMENT;
		}
		else if	( time_buffer[last_index] * 1.25  < time_next )
		{
			return	SKIP;
		}
		else
		{
			last_index  = time_buffer_count;
			time_buffer[time_buffer_count++]  = time_next;
			if	( time_buffer_count == time_buffer.length )
			{
				time_buffer_count  =  0;
			}
			return	NEXT;
		}
	}
	public		boolean		check_duration ( )
	{
		int  start_index;
		if	( time_buffer_count == time_buffer.length )
		{
			start_index  =  0;
		}
		else
		{
			start_index  = time_buffer_count;
		}
		test_duration  = get_range_duration ( start_index, test_size );
		if	( time_buffer_count  < test_size )
		{
			start_index  = time_buffer.length - test_size;
		}
		else
		{
			start_index  = time_buffer_count - test_size;
		}
		if	( test_duration  > get_range_duration (
			    start_index, test_size ) * 1.10 )
		{
			return	false;
		}
		else
		{
			return	true;
		}
	}
	private		double	get_range_duration ( int index, int size )
	{
		double  duration  =  0;
		for	( int i  =  0; i  < size; i++ )
		{
			duration += time_buffer[index++];
		}
		duration /= size;
		return	duration;
	}
	public		double	get_duration ( )
	{
		return	get_range_duration (  0, time_buffer.length );
	}
	public		double	get_test_duration ( )
	{
		return	test_duration;
	}
}
