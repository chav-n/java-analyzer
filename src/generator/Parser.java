package	generator;

import	java.util.Vector;

public		class	Parser
	implements	KEYWORD_ID, RuleSymbolKind
{
	private		RuleSet		rule_set;
	private		Rule		next_rule;
	private		Lexer		lexer;
	public		int		ext_command_dot ( )
		throws	Exception
	{
		String  command  = "ext_"+lexer.token;
		return	ext_command_dot ( read ( ), command );
	}
	public		int		ext_set_head ( int token_ID )
		throws	Exception
	{
		switch	( token_ID )
		{
		case K_SET:
			accept ( K_IDENTIFIER );
			rule_set = new RuleSet ( lexer.token );
			return	ext_state_head ( read ( ) );
		default:
			return	syntax_error ( );
		}
	}
	public		int		ext_state_head ( int token_ID )
		throws	Exception
	{
		for	(;;)
		switch	( token_ID )
		{
		case K_STATE:
			accept ( K_IDENTIFIER );
			next_rule  = new Rule ( lexer.token );
			rule_set . add_rule ( next_rule );
			token_ID  = ext_rule_case ( read ( ) );
			continue;
		case K_EOL:
			return	K_EOL;
		default:
			return	syntax_error ( );
		}
	}
	public		int		ext_rule_case ( int token_ID )
		throws	Exception
	{
		for	(;;)
		switch	( token_ID )
		{
		case K_TERMINAL:
			next_rule . new_expression ( );
			next_rule . add_symbol ( lexer.token, TERMINAL, APPEND );
			token_ID  = ext_post_case ( read ( ) );
			continue;
		case K_NON_TERMINAL:
			next_rule . new_expression ( );
			token_ID  = ext_command_dot ( );
			continue;
		case K_POP:
			token_ID  = ext_rule_post_pop ( ext_rule_pop ( read ( ) ) );
			continue;
		case K_END:
			return	read ( );
		default:
			return	syntax_error ( );
		}
	}
	public		int		ext_rule_pop ( int token_ID )
		throws	Exception
	{
		switch	( token_ID )
		{
		case K_POP:
			accept ( K_TERMINAL );
			next_rule . new_expression ( lexer.token );
			return	read ( );
		default:
			next_rule . new_expression ( "token_ID" );
			return	token_ID;
		}
	}
	public		int		ext_rule_post_pop ( int token_ID )
		throws	Exception
	{
		switch	( token_ID )
		{
		case K_TERMINAL:
			next_rule . add_symbol ( lexer.token, TERMINAL, APPEND );
			return	ext_post_case ( read ( ) );
		case K_NON_TERMINAL:
			return	ext_command_dot ( );
		case K_COLON:
			next_rule . save_expression ( );
			return	read ( );
		default:
			return	syntax_error ( );
		}
	}
	public		int		ext_post_case ( int token_ID )
		throws	Exception
	{
		for	(;;)
		switch	( token_ID )
		{
		case K_COMMA:
			accept ( K_TERMINAL );
			next_rule . add_symbol ( lexer.token, TERMINAL, OR );
			token_ID  = read ( );
			continue;
		case K_COLON:
			next_rule . save_expression ( );
			return	read ( );
		case K_TERMINAL:
			next_rule . add_symbol ( lexer.token, TERMINAL, APPEND );
			return	ext_command ( read ( ) );
		case K_NON_TERMINAL:
			return	ext_command_dot ( );
		case K_EXT:
			next_rule . add_symbol_self ( APPEND );
			return	ext_command ( read ( ) );
		default:
			return	syntax_error ( );
		}
	}
	public	int		ext_command ( int token_ID )
		throws	Exception
	{
		for	(;;)
		switch	( token_ID )
		{
		case K_TERMINAL:
			next_rule . add_symbol ( lexer.token, TERMINAL, APPEND );
			token_ID  = read ( );
			continue;
		case K_NON_TERMINAL:
			return	ext_command_dot ( );
		case K_EXT:
			next_rule . add_symbol_self ( APPEND );
			token_ID  = read ( );
			continue;
		case K_COLON:
			next_rule . save_expression ( );
			return	read ( );
		default:
			return	syntax_error ( );
		}
	}
	public	int		ext_command_dot ( int token_ID, String command )
		throws	Exception
	{
		for	(;;)
		switch	( token_ID )
		{
		case K_TERMINAL:
			next_rule . add_symbol ( command, NON_TERMINAL, APPEND );
			next_rule . add_symbol ( lexer.token, TERMINAL, APPEND );
			return	ext_command ( read ( ) );
		case K_NON_TERMINAL:
			next_rule . add_symbol ( command, NON_TERMINAL, APPEND );
			command   = "ext_"+lexer.token;
			token_ID  = read ( );
			continue;
		case K_EXT:
			next_rule . add_symbol ( command, NON_TERMINAL, APPEND );
			next_rule . add_symbol_self ( APPEND );
			return	ext_command ( read ( ) );
		case K_DOT:
			accept ( K_NON_TERMINAL );
			command += ".ext_"+lexer.token;
			next_rule . add_symbol ( command, NON_TERMINAL, APPEND );
			return	ext_command ( read ( ) );
		case K_COLON:
			next_rule . add_symbol ( command, NON_TERMINAL, APPEND );
			next_rule . save_expression ( );
			return	read ( );
		default:
			return	syntax_error ( );
		}
	}
	public		void	accept ( int token_ID )
		throws	Exception
	{
		if	( read ( ) != token_ID )
			syntax_error ( );
	}
	public		int		read ( )
		throws	Exception
	{
		int  token_ID;
		token_ID  = lexer . read_token ( );
		if	( token_ID == K_EOL && read_line ( ) )
			token_ID  = lexer . read_token ( );
		return	token_ID;
	}
	public		boolean  read_line ( )
		throws	Exception
	{
		if	( lexer.read_line ( ) != null )
		{
			if	( lexer.line . trim ( ) . indexOf ( "/*" ) ==  0 )
				return	read_comment_block ( );
			else
				return	true;
		}
		return	false;
	}
	public		int		syntax_error ( )
		throws	SyntaxException
	{
		lexer . syntax_error ( );
		return	 0;
	}
	public		boolean   read_comment_block ( )
		throws	Exception
	{
		if	( next_rule == null )
			syntax_error ( );
		do
		{
			next_rule . add_comment ( lexer.line );
			if	( lexer.line . trim ( ) . endsWith ( "*/" ) )
				return	read_line ( );
		}
		while	( lexer . read_line ( ) != null );
		return	syntax_error ( ) !=  0;
	}
	public		RuleSet	parse ( String file_path )
		throws	Exception
	{
		lexer  = new Lexer ( file_path );
		ext_set_head ( read ( ) );
		return	rule_set;
	}
}
