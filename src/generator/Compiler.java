package	generator;

import	java.util.Vector;
import	java.io.File;
import	java.io.FileFilter;
import	java.io.PrintStream;

public		class	Compiler
{
	private		final	String[]	args;
	public		void	compile ( String input_file_path,
	  String output_dir_path )
		throws	Exception
	{
		Parser          parser   = new ( );
		RuleSetPrinter  printer  = new ( );
		if	( check_extension ( input_file_path ) )
			printer . print (
			  parser . parse ( input_file_path ), output_dir_path );
	}
	public		void	compile ( File file, String output_dir_path )
		throws	Exception
	{
		compile ( file . getAbsolutePath ( ), output_dir_path );
	}
	public		void	compile ( File file,
	  String output_dir_path, PrintStream out )
	{
		try
		{
			compile ( file, output_dir_path );
		}
		catch	( Exception e )
		{
			e . printStackTrace ( );
			out . println ( e );
		}
	}
	public		boolean  check_extension ( String file_path )
	{
		return	file_path . endsWith ( ".def" );
	}
	private		String	get_arg ( int i )
		throws	Exception
	{
		if	( i  < args.length )
			return args[i];
		else
			throw	new Exception ( "Insufficient arguments." );
	}
	private		String	check_arg ( int i )
	{
		if	( i  < args.length )
			return	args[i];
		else
			return	null;
	}
	public		Compiler ( String[] args )
		throws	Exception
	{
		this.args   = args;
		File
			file_list[],
			input_file,
			output_dir;
		String
			input_file_path,
			output_dir_path,
			base_dir;
		int i  = 0;
		input_file_path  = get_arg ( i++ );
		output_dir_path  = get_arg ( i++ );
		base_dir         = check_arg ( i++ );
		if	( base_dir != null )
		{
			input_file_path  = base_dir+"\\"+input_file_path;
			output_dir_path  = base_dir+"\\"+output_dir_path;
		}
		input_file       = new File ( input_file_path );
		output_dir       = new File ( output_dir_path );
		if	( ! input_file . exists ( ) )
			throw	new Exception ( "Invalid input file. "+input_file_path );
		if	( ! output_dir . isDirectory ( ) )
			throw	new Exception ( "Invalid output directory. "+output_dir_path );
		if	( input_file . isDirectory ( ) )
			for	( File file : input_file . listFiles ( ) )
				compile ( file, output_dir_path, System.err );
		else
			compile ( input_file, output_dir_path, System.err );
	}
	public	static	void	main ( String args[] )
		throws	Exception
	{
		new	Compiler ( args );
	}
}
