package	util;

import	java.util.Vector;
import	java.util.HashMap;
import	java.io.FileReader;
import	java.io.BufferedReader;
import	java.io.FileNotFoundException;

public		class	InnerClassSorter
{
	private		Vector <String>  	state_names;
	private		reader				File_Reader;
	private		InnerClassSorter ( String file_name );
	{
		state_names			= new Vector <String> ( );
		reader				= new File_Reader ( file_name );
	}
	public		static	void	main ( String[] args )
	{
		try
		{
			input_branch ( args );
			open_file ( class_file );
			pre_declaration_branch ( );
			declaration_branch ( );
			declaration_to_init_branch ( );
			init_branch ( );
			init_to_def_branch ( );
			read_inner_classes (  0 );
			post_def_branch ( );
			close_file ( );
			if	( inner_class_file != "" )
			{
				open_file ( inner_class_file );
				read_line ( );
				if	( read_word ( ) == null )
				{
					throw	new Exception (
					  "Wrong inner class file format." );
				}
				read_inner_classes (  1 );
				close_file ( );
			}
			check_branch ( );
			output_branch ( );
		}
		catch	( Exception e )
		{
			System.err . println ( get_exception_message ( e ) );
		}
	}
	private		static	void	pre_declaration_branch ( )
		throws	Exception
	{
		pre_init_source  = "";
		for	(;;)
		{
			read_line ( );
			if	( line . indexOf ( ';' ) != -1   &&
				  read_word ( )          != null    )
			{
				break;
			}
			pre_init_source += line+"\n";
		}
	}
	private		static	void	declaration_branch ( )
		throws	Exception
	{
		for	(;;)
		{
			pre_init_source += line+"\n";
			inner_class_names . add ( word );
			read_line ( );
			if	( line . indexOf ( ';' ) == -1   ||
					  read_word ( )      == null    )
			{
				break;
			}
		}
	}
	private		static	void	declaration_to_init_branch ( )
		throws	Exception
	{
		for	(;;)
		{
			if	( line . indexOf ( '=' ) != -1   &&
				  read_word ( )          != null    )
			{
				break;
			}
			pre_init_source += line+"\n";
			read_line ( );
		}
	}
	private		static	void	init_branch ( )
		throws	Exception
	{
		String	init_text;
		for	(;;)
		{
			init_text  = line+"\n";
			read_line ( );
			init_text += line+"\n";
			inner_class_inits . put ( word, init_text );
			read_line ( );
			if	( read_word ( ) == null )
			{
				break;
			}
		}
	}
	private		static	void	init_to_def_branch ( )
		throws	Exception
	{
		pre_def_source  = "";
		for	(;;)
		{
			if	( read_word ( ) != null )
			{
				break;
			}
			pre_def_source += line+"\n";
			read_line ( );
		}
	}
	private		static	void	post_def_branch ( )
		throws	Exception
	{
		post_def_source  = "";
		try
		{
			for	(;;)
			{
				post_def_source += line+"\n";
				read_line ( );
			}
		}
		catch	( Exception e )
		{
		}
	}
	private		static	void	read_inner_classes ( int post_def_flag )
		throws	Exception
	{
		String	inner_class_source;
		try
		{
			for	(;;)
			{
				inner_class_source  = "";
				for	(;;)
				{
					inner_class_source += line+"\n";
					if	( line . equals ( "\t}" ) )
					{
						break;
					}
					read_line ( );
				}
				inner_class_defs . put ( word, inner_class_source );
				read_line ( );
				/* Goes to post_def_branch ( ). */
				if	( read_word ( ) == null )
				{
					break;
				}
			}
		}
		catch	( Exception e )
		{
			if	( post_def_flag ==  0 )
			{
				throw	e;
			}
		}
	}
	private		static	String				class_file;
	private		static	String				inner_class_file;
	private		static	void	input_branch ( String[] args )
		throws	Exception
	{
		class_file        = "";
		inner_class_file  = "";
		if	( args.length ==  0 )
		{
			throw	new Exception ( "No arguments given." );
		}
		if	( args.length >=  1 )
		{
			class_file  = args[ 0];
		}
		if	( args.length >=  2 )
		{
			if	( ! find_base_file_name ( args[ 1] ) . equals ( "" ) )
			{
				inner_class_file  = args[ 1];
			}
		}
	}
	private		static	void	check_branch ( )
		throws	Exception
	{
		String	error_report_init;
		String	error_report_def;
		String	class_name;
		error_report_init  = "";
		error_report_def   = "";
		class_name         = "";
		for	( int i  =  0; i  < inner_class_names . size ( ); i++ )
		{
			class_name  = inner_class_names . get ( i );
			if	( inner_class_inits . get ( class_name ) == null )
			{
				error_report_init += "Missing initialization for "+
				  "class \""+class_name+"\".\n";
			}
		}
		for	( int i  =  0; i  < inner_class_names . size ( ); i++ )
		{
			class_name  = inner_class_names . get ( i );
			if	( inner_class_defs . get ( class_name ) == null )
			{
				error_report_def += "Missing definition for class \""+
				  class_name+"\".\n";
			}
		}
		if	( ! error_report_init . equals ( "" ) ||
			  ! error_report_def . equals ( "" )     )
		{
			throw	new Exception (
			  "Class content is missing.\n\n"+
			  error_report_init+
			  error_report_def );
		}
	}
	private		static	void	output_branch ( )
		throws	Exception
	{
		System.out . print ( pre_init_source );
		for	( int i  =  0; i  < inner_class_names . size ( ); i++ )
		{
			System.out . print ( inner_class_inits . get (
			  inner_class_names . get ( i ) ) );
		}
		System.out . print ( pre_def_source );
		for	( int i  =  0; i  < inner_class_names . size ( ); i++ )
		{
			System.out . print ( inner_class_defs . get (
			  inner_class_names . get ( i ) ) );
		}
		System.out . print ( post_def_source );
	}
	private		static	String				word;
	private		static	final	String		prefix;
	private		static	String	read_word ( )
	{
		int start_index;
		start_index  = line . indexOf ( "Extension" );
		if	( start_index == -1 )
		{
			start_index  = line . indexOf ( "Check" );
		}
		if	( start_index == -1 )
		{
			return	word  = null;
		}
		else
		{
			int end_index  = start_index;
			while	( end_index != line . length ( )  &&
					  Character . isLetterOrDigit (
						line . charAt ( end_index ) )    )
			{
				end_index++;
			}
			return	word  = line . substring ( start_index, end_index );
		}
	}
	private		static	BufferedReader		reader;
	private		static	String				line;
	private		static	String				reader_file_path;
	private		static	void	read_line ( )
		throws	Exception
	{
		line  = reader . readLine ( );
		if	( line == null )
		{
			throw	new Exception ( "Unexpected file termination." );
		}
	}
	private		static	void	open_file ( String file_path )
		throws	Exception
	{
		reader_file_path  = file_path;
		reader            =
		  new BufferedReader ( new FileReader ( file_path ) );
	}
	private		static	void	close_file ( )
		throws	Exception
	{
		reader . close ( );
	}
	private		static	String	find_base_file_name ( String file_path )
	{
		return	file_path . substring (
		  file_path . lastIndexOf ( '\\' ) + 1,
		  file_path . lastIndexOf ( '.' )       );
	}
	private		static	String	get_exception_message ( Exception e )
	{
		if	( e instanceof FileNotFoundException )
		{
			return	"File not found: "+reader_file_path;
		}
		else
		{
			e . printStackTrace ( );
			return	"Error: "+e . getMessage ( );
		}
	}
}
