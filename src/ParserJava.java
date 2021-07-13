import	java.io.File;

public		class	ParserJava
{
	public		static	void	main ( String[] args )
		throws	Exception
	{
		ParseLogger  parse_logger;
		Logger       logger;
		String	input_file_path;
		String	output_file_path;
		switch	( args.length )
		{
		case  0:
			System.err . println ( "No input file specified." );
		case  1:
			System.err . println ( "No output file specified." );
			System . exit (  1 );
		default:
			input_file_path   = args[ 0];
			output_file_path  = args[ 1];
		}
		parse_logger  = new ParserConsole ( );
		if	( new File ( input_file_path ) . isDirectory ( ) )
		{
			logger  = new ListLogger ( parse_logger, input_file_path,
			  output_file_path );
		}
		else if	( input_file_path . endsWith ( ".java" ) )
		{
			logger  = new SingleLogger ( parse_logger, input_file_path,
			  output_file_path, 20,  5 );
		}
		else
		{
			System.err . println ( "Invalid input file." );
			return;
		}
		try
		{
			logger . log ( );
		}
		catch	( SyntaxException e )
		{
			System.err . println ( e . getMessage ( ) );
		}
		logger . print ( );
	}
}
