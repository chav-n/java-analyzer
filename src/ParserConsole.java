import	java.util.Vector;
import	java.io.File;
import	java.io.PrintStream;
import	java.io.IOException;

import	scanner.InvalidInputException;

public		class	ParserConsole
	extends	ParseLogger
{
	protected	static	void	init ( PrintStream out )
		throws	IOException
	{
		Lexer . init ( );
		Parser . init ( out );
	}
	public		void	parse ( String file_path )
		throws	SyntaxException,
				IOException
	{
		parse ( file_path, null );
	}
	public		void	debug ( String file_path,
	  PrintStream out )
		throws	SyntaxException,
				IOException
	{
		try
		{
			parse ( file_path, out );
		}
		catch	( SyntaxException e )
		{
			parse ( file_path, out );
		}
	}
	private		void	parse ( String file_path,
	  PrintStream out )
		throws	SyntaxException,
				IOException
	{
		init ( out );
		double	finish_time;
		read_time    = System . nanoTime ( );
		Lexer . load_file ( file_path );
		parse_time   = System . nanoTime ( );
		read_time    = parse_time - read_time;
		try
		{
			Parser . run_automaton ( );
		}
		catch	( InvalidInputException e )
		{
			throw	new SyntaxException ( e . getMessage ( ) );
		}
		parse_time  = System . nanoTime ( ) - parse_time;
		Parser . check_parser ( );
		file_size    = Lexer . get_file_size ( );
		token_count  = Lexer . get_token_count ( );
	}
	protected	static	void	error ( int error_flag )
		throws	SyntaxException
	{
		switch	( error_flag )
		{
		case  2:
			simple_error (
			  "(Internal) Accessing empty section stack." );
		case  3:
			simple_error (
			  "(Internal) Accessing empty attribute stack." );
		case  4:
			simple_error (
			  "(Internal) Accessing empty value stack." );
		default:
			simple_error (
			  "Token: "+Lexer . get_token_name ( error_flag ) );
		}
	}
	protected	static	void	section_error ( String section_name )
		throws	SyntaxException
	{
		throw	new SyntaxException (
		  "Illegal end section: "+section_name );
	}
	private		static	void	simple_error ( String message )
		throws	SyntaxException
	{
		throw	new SyntaxException ( message,
		  Lexer . get_line_number ( ), Parser . get_ext_name ( ) );
	}
}
