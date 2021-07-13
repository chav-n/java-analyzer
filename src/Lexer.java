import	java.io.File;
import	java.io.FileReader;
import	java.io.IOException;

public		class	Lexer
	implements	SYMBOL_ID
{
	private		static	final	boolean[]	identifier_part;
	private		static	final	boolean[]	hexadecimal_part;
	private		static	int					error_flag;
	protected	static	String				token;
	protected	static	char[]				source;
	private		static	int					source_length;
	private		static	int					source_index;
	private		static	String				file_path;
	private		static	int					line_number;
	public		static	final	char		CHAR_LIMIT;
	private		static	int					token_count;
	static
	{
		CHAR_LIMIT			= 128;
		identifier_part		= new boolean[CHAR_LIMIT];
		hexadecimal_part	= new boolean[CHAR_LIMIT];
		/*
		for	[ char i : '0', i + 1, '9' ]
		char i : [ '0', i + 1, '9' ]
		char i : ( '0', i + 1, '9' )
		String line : ( reader . readLine ( ), null )
		String line : ( vec )
		*/
		for	( char i  = '0'; i <= '9'; i++ )
		{
			identifier_part[i]   = true;
			hexadecimal_part[i]  = true;
		}
		for	( char i  = 'A'; i <= 'F'; i++ )
		{
			identifier_part[i]        = true;
			identifier_part[i + 32]   = true;
			hexadecimal_part[i]       = true;
			hexadecimal_part[i + 32]  = true;
		}
		for	( char i  = 'G'; i <= 'Z'; i++ )
		{
			identifier_part[i]       = true;
			identifier_part[i + 32]  = true;
		}
		identifier_part['_']  = true;
		identifier_part['$']  = true;
	}
	public		static	void	init ( )
	{
		error_flag			=  0;
		token				= null;
		token_count			=  0;
	}
	protected	static	int next_token ( )
	{
		int  symbol  = next_token ( 0 );
		return	symbol;
	}
	protected	static	int	next_token ( int zero )
	{
		char  next_char;
		next_char  = source[source_index];
		for	(;;)
		{
			switch	( next_char )
			{
			case '\n':
				line_number++;
			case ' ':
			case '\t':
			case '\f':
			case '\r':
				next_char  = source[++source_index];
				continue;
			case EOF:
				return	EOF;
			}
			break;
		}
		int  token_ID;
		int  token_start;
		token_ID     =  0;
		token_start  = source_index;
		token        = null;
		token_count++;
		for	(int next_index;;)
		{
			if	( next_char > EOF )
			{
				if	( Character . isJavaIdentifierPart ( next_char ) )
				{
					token_ID  = PREFIX_IDENTIFIER;
					next_char  = source[++source_index];
					break;
				}
				else
				{
					break;
				}
			}
			next_index = token_ID;
			token_ID   = KeywordToID.token_map[next_index][next_char];
			if	( token_ID ==  0 )
			{
				token_ID = KeywordToID.token_map[next_index][ 0];
				break;
			}
			else
			{
				next_char = source[++source_index];
			}
		}
		if	( token_ID ==  0 )
		{
			source_index++;
			write_token ( token_start );
			return	INVALID;
		}
		switch	( token_ID )
		{
		case PREFIX_IDENTIFIER:
			for	(;;)
			{
				if	( next_char  < EOF )
				{
					if	( identifier_part[next_char] )
					{
						next_char  = source[++source_index];
						continue;
					}
					else
					{
						write_token ( token_start );
						return	IDENTIFIER;
					}
				}
				else
				{
					if	( Character . isJavaIdentifierPart (
					  next_char ) )
					{
						next_char  = source[++source_index];
						continue;
					}
					else
					{
						write_token ( token_start );
						return	IDENTIFIER;
					}
				}
			}
		case PREFIX_REAL:
			token_ID  = read_real ( read_digits ( next_char ) );
			write_token ( token_start );
			return	token_ID;
		case PREFIX_DECIMAL:
			token_ID  = read_decimal_part ( next_char );
			write_token ( token_start );
			return	token_ID;
		case PREFIX_OCTAL:
			token_ID  = read_octal ( next_char );
			write_token ( token_start );
			return	token_ID;
		case PREFIX_HEX:
			token_ID  = read_pre_hexadecimal ( next_char );
			write_token ( token_start );
			return	token_ID;
		case PREFIX_ZERO:
			token_ID  = read_zero_prefix ( next_char );
			write_token ( token_start );
			return	token_ID;
		case IDENTIFIER:
			write_token ( token_start );
			return	IDENTIFIER;
		case CHARACTER_LITERAL:
			return	read_character_literal ( next_char );
		case STRING_LITERAL:
			return	read_string_literal ( next_char );
		case COMMENT_LINE:
			return	read_line_comment ( next_char );
		case COMMENT_BLOCK:
			return	read_block_comment ( next_char );
		default:
			return	token_ID;
		}
	}
	private		static	int	read_character_literal (
	  char next_char )
	{
		switch	( next_char )
		{
		case EOF:
		case '\n':
		case '\f':
		case '\r':
		case '\'':
			return	INVALID;
		case '\\':
			return	read_character_end ( read_character_escape ( source[++source_index] ) );
		default:
			return	read_character_end ( source[++source_index] );
		}
	}
	private		static	char	read_character_escape ( char next_char )
	{
		switch	( next_char )
		{
		case EOF:
		case '\n':
		case '\t':
		case '\f':
		case '\r':
		default:
			return	INVALID;
		case 'u':
			return	read_sequence_unicode ( source[++source_index] );
		case '0':
		case '1':
		case '2':
		case '3':
			return	read_sequence_next_octal ( source[++source_index] );
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return	read_sequence_last_octal ( source[++source_index] );
		case 'b':
		case 't':
		case 'n':
		case 'f':
		case 'r':
		case '"':
		case '\'':
		case '\\':
			return	source[++source_index];
		}
	}
	private		static	char	read_sequence_last_octal (
	  char next_char )
	{
		if	( '0' <= next_char && next_char <= '7' )
		{
			return	source[++source_index];
		}
		else
		{
			return	next_char;
		}
	}
	private		static	char	read_sequence_next_octal (
	  char next_char )
	{
		if	( '0' <= next_char && next_char <= '7' )
		{
			return	read_sequence_last_octal ( source[++source_index] );
		}
		else
		{
			return	next_char;
		}
	}
	private		static	char	read_sequence_unicode ( char next_char )
	{
		for	( int i  =  0; i  <  4; i++ )
		{
			if	( hexadecimal_part[next_char] )
			{
				next_char  = source[++source_index];
			}
			else
			{
				return	INVALID;
			}
		}
		return	next_char;
	}
	private		static	int	read_character_end ( char next_char )
	{
		if	( next_char == '\'' )
		{
			source_index++;
			return	CHARACTER_LITERAL;
		}
		else
		{
			return	INVALID;
		}
	}
	private		static	int	read_string_literal ( char next_char )
	{
		for	(;;)
		{
			switch	( next_char )
			{
			case EOF:
			case '\n':
			case '\f':
			case '\r':
				return	INVALID;
			case '\\':
				next_char  = read_character_escape ( source[++source_index] );
				continue;
			case '"':
				source_index++;
				return	STRING_LITERAL;
			default:
				next_char  = source[++source_index];
			}
		}
	}
	private		static	int	read_line_comment ( char next_char )
	{
		for	(;;)
		{
			switch	( next_char )
			{
			case '\n':
				line_number++;
			case '\r':
				source_index++;
				return	next_token ( );
			case EOF:
				return	EOF;
			default:
				next_char  = source[++source_index];
			}
		}
	}
	private		static	int	read_block_comment ( char next_char )
	{
		for	(;;)
		{
			switch	( next_char )
			{
			case EOF:
				return	INVALID;
			case '*':
				return	read_block_comment_escape (
				  source[++source_index] );
			case '\n':
				line_number++;
			default:
				next_char  = source[++source_index];
			}
		}
	}
	private		static	int	read_block_comment_escape (
	  char next_char )
	{
		for	(;;)
		{
			switch	( next_char )
			{
			case EOF:
				return	INVALID;
			case '*':
				next_char  = source[++source_index];
				continue;
			case '/':
				source_index++;
				return	next_token ( );
			case '\n':
				line_number++;
			default:
				return	read_block_comment ( source[++source_index] );
			}
		}
	}
	private		static	char	read_digits ( char next_char )
	{
		while	( next_char >= '0' && next_char <= '9' )
		{
			next_char  = source[++source_index];
		}
		return	next_char;
	}
	private		static	char	read_hexadecimal_digits ( char next_char )
	{
		while ( hexadecimal_part[next_char] )
		{
			next_char  = source[++source_index];
		}
		return	next_char;
	}
	private		static	int	read_decimal_part ( char next_char )
	{
		return	read_float_postfix ( read_digits ( next_char ) );
	}
	private		static	int	read_pre_exponent ( char next_char )
	{
		switch	( next_char )
		{
		case '-':
		case '+':
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return	read_exponent ( source[++source_index] );
		default:
			return	INVALID;
		}
	}
	private		static	int	read_exponent ( char next_char )
	{
		next_char  = read_digits ( next_char );
		switch	( next_char )
		{
		case 'f':
		case 'F':
			source_index++;
			return	FLOATING_POINT_LITERAL;
		case 'd':
		case 'D':
			source_index++;
			return	DOUBLE_LITERAL;
		default:
			return	FLOATING_POINT_LITERAL;
		}
	}
	private		static	int	read_pre_hexadecimal ( char next_char )
	{
		if	( hexadecimal_part[next_char] )
		{
			return	read_hexadecimal ( source[++source_index] );
		}
		else if	( next_char == '.' )
		{
			return	read_pre_hexadecimal_float (
			  source[++source_index] );
		}
		else
		{
			return	INVALID;
		}
	}
	private		static	int	read_octal ( char next_char )
	{
		while	( next_char >= '0' && next_char <= '7' )
		{
			next_char  = source[++source_index];
		}
		return	read_integer_postfix ( next_char );
	}
	private		static	int	read_hexadecimal ( char next_char )
	{
		next_char  = read_hexadecimal_digits ( next_char );
		switch	( next_char )
		{
		case '.':
			return	read_pre_hexadecimal_float_part (
			  source[++source_index] );
		default:
			return	read_integer_postfix ( next_char );
		}
	}
	private		static	int	read_pre_hexadecimal_float_part (
	  char next_char )
	{
		if	( hexadecimal_part[next_char] )
		{
			return	read_hexadecimal_float_part (
			  source[++source_index] );
		}
		else
		{
			return	read_float_postfix ( next_char );
		}
	}
	private		static	int	read_hexadecimal_float_part (
	  char next_char )
	{
		next_char  = read_hexadecimal_digits ( next_char );
		switch	( next_char )
		{
		case 'P':
		case 'p':
			return	read_pre_exponent ( source[++source_index] );
		default:
			return	read_float_postfix ( next_char );
		}
	}
	private		static	int	read_pre_hexadecimal_float (
	  char next_char )
	{
		if	( hexadecimal_part[source[++source_index]] )
		{
			return	read_hexadecimal_float_part (
			  source[++source_index] );
		}
		else
		{
			return	INVALID;
		}
	}
	private		static	int	read_integer_postfix ( char next_char )
	{
		switch	( next_char )
		{
		case 'l':
		case 'L':
			source_index++;
			return	LONG_LITERAL;
		case 'f':
		case 'F':
			source_index++;
			return	FLOATING_POINT_LITERAL;
		case 'd':
		case 'D':
			source_index++;
			return	DOUBLE_LITERAL;
		default:
			return	INTEGER_LITERAL;
		}
	}
	private		static	int	read_float_postfix ( char next_char )
	{
		switch	( next_char )
		{
		case 'f':
		case 'F':
			source_index++;
			return	FLOATING_POINT_LITERAL;
		case 'd':
		case 'D':
			source_index++;
			return	DOUBLE_LITERAL;
		case 'e':
		case 'E':
			return	read_pre_exponent ( source[++source_index] );
		default:
			return	FLOATING_POINT_LITERAL;
		}
	}
	private		static	int	read_real ( char next_char )
	{
		switch	( next_char )
		{
		case '.':
			return	read_decimal_part ( source[++source_index] );
		case 'e':
		case 'E':
			return	read_pre_exponent ( source[++source_index] );
		default:
			return	read_integer_postfix ( next_char );
		}
	}
	private		static	int	read_zero_prefix ( char next_char )
	{
		switch	( next_char )
		{
		case 'e':
		case 'E':
			return	read_pre_exponent ( source[++source_index] );
		default:
			return	read_integer_postfix ( next_char );
		}
	}
	private		static	void	write_token ( int token_start )
	{
		//new SmallString ( source, token_start, source_index );
		token  = new String ( source, token_start, source_index -
		  token_start );
	}
	protected	static	void	load_file ( String fp )
		throws	IOException
	{
		file_path      = fp;
		source_length  = (int) new File ( file_path ) . length ( );
		source         = new char[source_length + 1];
		new FileReader ( file_path ) . read ( source );
		source[source_length]  = EOF;
		source_index   =  0;
		line_number    =  1;
	}
	public		static	String	get_file_path ( )
	{
		return	file_path;
	}
	public		static	long	get_file_size ( )
	{
		return	source_length;
	}
	public		static	long	get_token_count ( )
	{
		return	token_count;
	}
	public		static	String	get_token_name ( int ID )
	{
		switch	( ID )
		{
		case IDENTIFIER:
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case INVALID:
			return	token;
		default:
			String name = KeywordToID . get_token_name ( ID );
			if	( name == null )
				name  = String . valueOf ( ID );
			return	"'"+name+"'";
		}
	}
	public		static	int		get_line_number ( )
	{
		return	line_number;
	}
	protected	static	String	get_scanner_name ( int ID )
	{
		switch	( ID )
		{
		case COMMENT_LINE:
			return	"Line Comment";
		case COMMENT_BLOCK:
			return	"Block Comment";
		case CHARACTER_LITERAL:
			return	"Character Literal";
		case STRING_LITERAL:
			return	"String Literal";
		default:
			return	null;
		}
	}
}
