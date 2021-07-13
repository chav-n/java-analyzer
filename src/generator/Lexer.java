package	generator;

import	java.io.FileReader;
import	java.io.BufferedReader;

public		class	Lexer
	implements	CHARACTER_ID, KEYWORD_ID
{
	protected	String				line;
	protected	String				token;
	protected	char				token_ID;
	private		char				symbol_ID;
	private		int					line_index;
	private		int					line_number;
	private		String				file_path;
	private		BufferedReader		reader;
	public		Lexer ( String fp )
		throws	Exception
	{
		System.err . println ( fp );
		file_path    = fp;
		reader	     = new BufferedReader ( new FileReader ( fp ) );
		line_number  =  0;
	}
	public		String	read_line ( )
		throws	Exception
	{
		for	(;;)
		{
			line  = reader . readLine ( );
			if	( line != null )
			{
				line_number++;
				if	( line . length ( ) ==  0 )
					continue;
				line_index  = -1;
				read_char ( );
			}
			break;
		}
		return	line;
	}
	private		char	read_char ( )
	{
		line_index++;
		if	( line_index == line . length ( ) )
		{
			symbol_ID  = C_EOL;
		}
		else
		{
			char  ch;
			ch  = line . charAt ( line_index );
			if	( Character . isWhitespace ( ch ) )
				symbol_ID  = C_WHITESPACE;
			else if	( Character . isLetter ( ch ) )
				symbol_ID  = C_LETTER;
			else if	( Character . isDigit ( ch ) )
				symbol_ID  = C_DIGIT;
			else if	( ch == '_' )
				symbol_ID  = C_LETTER;
			else
				symbol_ID  = C_SYMBOL;
		}
		return	symbol_ID;
	}
	protected	void	flush_line ( )
	{
		line_index  = line . length ( );
		symbol_ID  = C_EOL;
	}
	public		char	read_token ( )
		throws	SyntaxException
	{
		int  token_start;
		while	( symbol_ID == C_WHITESPACE )
			read_char ( );
		token_start  = line_index;
		switch	( symbol_ID )
		{
		case C_EOL:
			token_ID  = K_EOL;
			return	token_ID;
		case C_LETTER:
			for	(;;)
			{
				switch	( read_char ( ) )
				{
				case C_LETTER:
				case C_DIGIT:
					break;
				default:
					token  = line_part ( token_start );
					if	( token . equals ( "end" ) )
						token_ID  = K_END;
					else if	( token . equals ( "case" ) )
						token_ID  = K_CASE;
					else if	( token . equals ( "default" ) )
						token_ID  = K_DEFAULT;
					else if	( token . equals ( "set" ) )
						token_ID  = K_SET;
					else if ( token . equals ( "state" ) )
						token_ID  = K_STATE;
					else if	( token . equals ( "ext" ) )
						token_ID  = K_EXT;
					else
						token_ID  = check_identifier ( token );
					return	token_ID;
				}
			}
		case C_DIGIT:
			for	(;;)
			{
				switch	( read_char ( ) )
				{
				case C_DIGIT:
					break;
				default:
					token     = line_part ( token_start );
					token_ID  = K_NUMBER;
					return	token_ID;
				}
			}
		case C_SYMBOL:
			read_char ( );
			token  = line_part ( token_start );
			if	( token . equals ( "." ) )
				token_ID  = K_DOT;
			else if	( token . equals ( ":" ) )
				token_ID  = K_COLON;
			else if ( token . equals ( "," ) )
				token_ID  = K_COMMA;
			else if ( token . equals ( "%" ) )
				token_ID  = K_POP;
			else
				syntax_error ( line_part ( token_start ) );
			return	token_ID;
		default:
			syntax_error ( line_part ( token_start ) );
			return	token_ID;
		}
	}
	private		char	check_identifier ( String token )
	{
		if	( token . equals ( token.toUpperCase() ) )
			return	K_TERMINAL;
		else if	( token . equals ( token.toLowerCase() ) )
			return	K_NON_TERMINAL;
		else
			return	K_IDENTIFIER;
	}
	public		String	line_part ( int start_index )
	{
		return	line . substring ( start_index, line_index );
	}
	public		void	syntax_error ( )
		throws	SyntaxException
	{
		throw	new SyntaxException ( file_path,
		  line_number, line_index, token );
	}
	public		void	syntax_error ( String token )
		throws	SyntaxException
	{
		throw	new SyntaxException ( file_path, line_number,
		  line_index, token );
	}
}
