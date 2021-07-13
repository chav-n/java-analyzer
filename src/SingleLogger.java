import	java.io.File;
import	java.io.PrintStream;

public		class	SingleLogger
	extends	Logger
{
	private		String				file_path;
	private		final	int			runs;
	private		final	int			test_runs;
	private		static	final	double		time_limit;
	static
	{
		time_limit			= 0.7E9; /* nanoseconds */
	}
	public		SingleLogger ( ParseLogger pl, String input_file_path,
	  String output_file_path, int r, int tr )
		throws	Exception
	{
		parse_logger		= pl;
		file_path			= input_file_path;
		file_size			=  0;
		token_count			=  0;
		parse_time			=  0;
		read_time			=  0;
		out					= new PrintStream ( output_file_path );
		runs				= r;
		test_runs			= tr;
	}
	public		void	log ( )
		throws	Exception
	{
		double	time_first;
		parse_logger . debug ( file_path, out );
		time_first  = parse_logger.parse_time;
		println ( new File ( file_path ) . getCanonicalPath ( ) );
		println ( );
		file_size    = parse_logger.file_size;
		token_count  = parse_logger.token_count;
		parse_time   = do_timed_parse ( );
		read_time    = parse_logger.read_time;
		println ( );
		println ( format_ms ( "First run: ", time_first ) );
		println ( String . format (
		  "Mean Time: %.2fms (%d runs)",
		  get_parse_time ( ),
		  runs ) );
		out . close ( );
	}
	private		double	do_timed_parse ( )
		throws	Exception
	{
		double	time_next;
		double	time_start;
		int		run;
		MeanTimeLogger  time_logger;
		run          =  1;
		time_logger  = new MeanTimeLogger ( runs, test_runs );
		time_start   = System . nanoTime ( );
		while	( true )
		{
			parse_logger . parse ( file_path );
			time_next   = parse_logger.parse_time;
			println ( format_ms ( "", time_next ) );
			switch	( time_logger . log ( time_next ) )
			{
			/*
			case MeanTimeLogger.REPLACEMENT:
				println ( "Replace" );
				continue;
			case MeanTimeLogger.SKIP:
				println ( "Skip" );
				continue;
			*/
			case MeanTimeLogger.NEXT:
				run++;
				if	( run == runs )
				{
					double duration  = time_logger . get_duration ( );
					if	( time_logger . check_duration ( ) )
					{
						double	elapsed_time  = System . nanoTime ( ) -
						  time_start;
						if	( elapsed_time > time_limit )
						{
							if	( elapsed_time  > 1E9 )
							{
								println ( format_ms ( "Total time: ",
								  elapsed_time ) );
							}
							return	duration;
						}
					}
					println ( format_ms ( "Duration: ", duration ) );
					run -= test_runs;
				}
			}
		}
	}
	private		String	format_ms ( String message, double ns )
	{
		return	String . format ( message+"%.2fms", ns /  1E6 );
	}
	private		void	println ( String text )
	{
		out . println ( "REM "+text );
	}
	private		void	println ( )
	{
		out . println ( );
	}
	public		double	get_file_size ( )
	{
		return	file_size / 1024;
	}
	public		double	get_parse_time ( )
	{
		return	parse_time / 1E6;
	}
	public		double	get_read_time ( )
	{
		return	read_time / 1E6;
	}
}
