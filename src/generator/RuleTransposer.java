package	generator;

import	java.io.FileReader;
import	java.io.BufferedReader;
import	java.util.Vector;

public		class	RuleTransposer
{
	private		BufferedReader		reader;
	private		Vector <String>		rules;
	private		String	ext_plain ( String line )
		throws	Exception
	{
		for	(;;)
		{
			if	( line == null )
			{
				return	null;
			}
			else if	( line . startsWith ( "case" ) )
			{
				println ( line );
				line  = ext_rule_check ( reader . readLine ( ) );
				continue;
			}
			else
			{
				println ( line );
				line  = reader . readLine ( );
				continue;
			}
		}
	}
	private		String	ext_rule ( String line )
		throws	Exception
	{
		for	(;;)
		{
			if	( line == null )
			{
				return	null;
			}
			else if	( line . startsWith ( "case" ) ||
					  line . startsWith ( "end" )     )
			{
				consume_rules ( );
				return line;
			}
			else
			{
				rules . add ( line );
				line  = reader . readLine ( );
				continue;
			}
		}
	}
	private		String	ext_rule_check ( String line )
		throws	Exception
	{
		for	(;;)
		{
			if	( line == null )
			{
				return	null;
			}
			else if	( line . startsWith ( "case" ) )
			{
				println ( line );
				line  = reader . readLine ( );
				continue;
			}
			else if	( line . startsWith ( "end" ) )
			{
				println ( line );
				return	reader . readLine ( );
			}
			else
			{
				rules . add ( line );
				return	ext_rule ( reader . readLine ( ) );
			}
		}
	}
	private		BufferedReader	open_input_file ( String file_name )
		throws	Exception
	{
		return	new BufferedReader ( new FileReader ( file_name ) );
	}
	private		void	println ( String line )
	{
		System.out . println ( line );
	}
	private		void	consume_rules ( )
	{
		for	( int i  = rules . size ( ); i !=  0; )
		{
			i--;
			println ( rules . get ( i ) );
		}
		rules . clear ( );
	}
	private		void	transpose ( String file_name )
		throws	Exception
	{
		reader  = open_input_file ( file_name );
		rules   = new Vector <String> ( );
		ext_plain ( reader . readLine ( ) );
	}
	public		static	void	main ( String[] args )
		throws	Exception
	{
		if	( args.length ==  0 )
			System.err . println ( "No input file specified." );
		else
			new RuleTransposer ( ) . transpose ( args[ 0] );
	}
}
