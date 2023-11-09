import	java.util.Vector;
import	java.io.File;
import	java.io.FileReader;
import	java.io.BufferedReader;
import	java.io.PrintStream;
import	java.io.IOException;

public		class	ListLogger
	extends	Logger
{
	private		File				root_directory;
	private		int					file_count;
	private		int					file_error_count;
	public		ListLogger ( ParseLogger pl, String root_directory_path,
	  String output_file )
		throws	IOException
	{
		parse_logger		= pl;
		root_directory		= new File ( root_directory_path );
		file_size			=  0;
		token_count			=  0;
		parse_time			=  0;
		read_time			=  0;
		out					= new PrintStream ( output_file );
	}
	public		void	parse_in_directory ( File directory )
	{
		Vector <File> subdirectories  = new Vector <File> ( );
		for	( File file : directory . listFiles ( ) )
		{
			if	( file . isFile ( ) )
			{
				String file_path  = file . getAbsolutePath ( );
				if	( file_path . endsWith ( ".java" ) )
				{
					file_count++;
					try
					{
						parse_logger . parse ( file_path );
						log_stats ( file_path );
						/*time_last_file  = parse_logger.total_time - time_last_file;
						System.out . println ( parse_logger . get_file_path ( ) );
						System.out . println ( time_last_file / 1E6 );*/
						/*size_last_file  = parse_logger.file_size - size_last_file;
						System.out . printf ( "Speed: %.2f MB/s\n",
						  /* 1048576  = 1024 * 1024 */
						/*  ( size_last_file / 1048576 ) /
						  ( time_last_file / 1E9 ) );
						System.out . println ( );*/
					}
					catch	( Exception e )
					{
						out . println ( file_path );
						out . println ( e . getMessage ( ) );
						out . println ( );
						file_error_count++;
					}
				}
			}
			else
			{
				subdirectories . add ( file );
			}
		}
		for	( File subdirectory : subdirectories )
		{
			parse_in_directory ( subdirectory );
		}
	}
	public		void	log ( )
		throws	Exception
	{
		double	time;
		double	time_last_file;
		double	size_last_file;
		file_error_count  =  0;
		time_last_file    =  0;
		size_last_file    =  0;
		list_startup ( );
		time  = System . nanoTime ( );
		parse_in_directory ( root_directory );
		time    = System . nanoTime ( ) - time;
		if	( file_error_count !=  0 )
		{
			System.err . printf (
			  "File errors found: %d in %d\n",
			  file_error_count,
			  file_count );
		}
		out . printf ( "%d αρχεία αναλύθηκαν.\n", file_count );
		out . close ( );
		System.err . printf ( "Time (s): %.2f\n", time / 1E9 );
	}
	private		void	log_stats ( String file_path )
	{
		file_size   += parse_logger.file_size;
		token_count += parse_logger.token_count;
		parse_time  += parse_logger.parse_time;
		read_time   += parse_logger.read_time;
	}
	private		void	list_startup ( )
		throws	Exception
	{
		try
		{
			for	( int run  =  0; run  <  0; run++ )
			{
				parse_logger . parse ( "..\\samples\\Parser.java" );
			}
		}
		catch	( Exception e )
		{
			throw	new SyntaxException ( "Error in startup file." );
		}
	}
	public		double	get_file_size ( )
	{
		return	(double) file_size / ( 1024 * 1024 );
	}
	public		double	get_parse_time ( )
	{
		return	parse_time / 1E9;
	}
	public		double	get_read_time ( )
	{
		return	read_time / 1E9;
	}
}
