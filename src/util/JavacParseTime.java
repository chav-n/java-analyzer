package	util;

import	java.util.regex.Pattern;
import	java.util.regex.Matcher;
import	java.io.FileReader;
import	java.io.BufferedReader;

public		class	JavacParseTime
{
	private		static	PrefixFinder		file_prefix_finder;
	private		static	PrefixFinder		time_prefix_finder;
	private		static	RegexFinder			file_finder;
	private		static	RegexFinder			time_finder;
	private		static	int					time_limit  = 10;
	private		static	int					parse_time  =  0;
	private		static	BufferedReader		reader;
	private		static	String				line;
	private		static	String				log_file    =
	  "..\\arj.log";
	private		static	String				source_dir  =
	  "\\devel\\AttributeReportJava\\src\\";
	static
	{
		new JavacParseTime ( );
	}
	private		JavacParseTime ( )
	{
		file_prefix_finder	= new PrefixFinder (
		  "[parsing started " );
		time_prefix_finder	= new PrefixFinder (
		  "[parsing completed " );
		file_finder			= new RegexFinder (
		  "[[[\\w+]:|.]\\\\]?[\\w+\\\\]*\\w+[\\w.]*" );
		time_finder			= new RegexFinder ( "\\d+" );
	}
	private		static	void	find_parse_time ( )
		throws	Exception
	{
		String file_name;
		int file_time;
		int min_file_time;
		file_name   = "";
		file_time   =  0;
		min_file_time  = Integer.MAX_VALUE;
		while	( read_line ( ) != null )
		{
			if	( file_prefix_finder . find ( ) != -1 )
			{
				file_name  = file_finder . find (
				  file_prefix_finder . get_prefix_length ( ) );
				if	( file_name . indexOf ( ".\\" ) != -1 )
				{
					file_name  = file_name . substring (  2 );
				}
				file_name  = source_dir+file_name;
			}
			else if	( time_prefix_finder . find ( ) != -1 )
			{
				file_time  = new Integer ( time_finder . find (
				  time_prefix_finder . get_prefix_length ( ) ) );
				if	( file_time >= time_limit )
				{
					System.out . println ( file_name );
					if	( file_time  < min_file_time )
					{
						min_file_time  = file_time;
					}
					parse_time += file_time;
				}
			}
		}
	}
	private		static	int		get_first_letter_index ( String text )
	{
		for	( int i  =  0; i  < text . length ( ); i++ )
		{
			if	( Character . isLetter ( text . charAt ( i ) ) )
			{
				return	i;
			}
		}
		return	 0;
	}
	private		static	String	get_project_name ( )
	{
		int	start_index;
		int	end_index;
		if	( ( end_index  = log_file . indexOf ( ".log" ) ) != -1 )
		{
			/* If lastIndexOf ( ) returns -1 the result is 0, which
			   is still valid. */
			start_index  = log_file . lastIndexOf ( '\\' ) + 1;
			return	log_file . substring ( start_index, end_index ) .
			  toUpperCase ( );
		}
		else
		{
			return	"Project";
		}
	}
	private		static	String	read_line ( )
		throws	Exception
	{
		return	line  = reader . readLine ( );
	}
	private		static	void	load_file ( String file_name )
		throws	Exception
	{
		reader  = new BufferedReader ( new FileReader ( file_name ) );
	}
	private		static	void	close_file ( )
		throws	Exception
	{
		reader . close ( );
	}
	private		static	void	print_parse_time ( )
	{
		System.err . printf ( "%s parse time: %d(ms)\n",
		  get_project_name ( ),
		  parse_time );
	}
	private		static	void	print_error_message ( Exception e )
	{
		e . printStackTrace ( );
		System.err . printf ( "Error: %s\n", e . getMessage ( ) );
	}
	private		class	PrefixFinder
	{
		private		String				prefix;
		private		int					prefix_length;
		protected	PrefixFinder ( String p )
		{
			prefix				= p;
			prefix_length		= p . length ( );
		}
		protected	int		find ( )
		{
			if	( line . indexOf ( prefix ) == -1 )
			{
				return	-1;
			}
			else
			{
				return	prefix_length;
			}
		}
		protected	int	get_prefix_length ( )
		{
			return	prefix_length;
		}
	}
	private		class	RegexFinder
	{
		private		Pattern				pattern;
		protected	RegexFinder ( String p )
		{
			pattern				= Pattern . compile ( p );
		}
		protected	String	find ( int start_index )
		{
			Matcher matcher  = pattern . matcher ( line );
			if	( matcher . find ( start_index ) )
			{
				return	matcher . group ( );
			}
			else
			{
				return	null;
			}
		}
	}
	public		static	void	main ( String args[] )
	{
		try
		{
			load_file ( log_file );
			find_parse_time ( );
			close_file ( );
			print_parse_time ( );
		}
		catch	( Exception e )
		{
			print_error_message ( e );
		}
	}
}
