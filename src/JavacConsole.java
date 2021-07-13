import	java.io.File;
import	java.io.FileReader;
import	java.io.PrintStream;
import	java.io.IOException;

import	com.sun.tools.javac.main.JavaCompiler;
import	com.sun.tools.javac.util.Context;

import	com.sun.tools.javac.parser.Scanner;
import	com.sun.tools.javac.parser.Scanner.Factory;

public		class	JavacConsole
	extends	ParseLogger
{
	public		void	parse ( String file_path )
		throws	IOException
	{
		Context context;
		Scanner scanner;
		JavaCompiler compiler;
		context     = new Context ( );
		read_time   = System . nanoTime ( );
		file_size   = new File ( file_path ) . length ( );
		scanner     = get_scanner (
		  context, file_path, (int) file_size );
		parse_time  = System . nanoTime ( );
		read_time   = parse_time - read_time;
		compiler    = new JavaCompiler ( context );
		compiler . parse ( scanner );
		parse_time  = System . nanoTime ( ) - parse_time;
	}
	public		Scanner  get_scanner ( Context context,
	  String file_path, int source_length )
		throws	IOException
	{
		FileReader reader = new ( file_path );
		char[] source = new char[source_length];
		reader . read ( source );
		Factory factory = Factory . instance ( context );
		return	factory . newScanner ( source, source_length );
	}
	public		void	debug ( String file_path, PrintStream out )
		throws	IOException
	{
		parse ( file_path );
	}
}
