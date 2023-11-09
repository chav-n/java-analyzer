import	java.util.Vector;
import	java.util.HashSet;
import	java.util.HashMap;

public		final	class	KeywordToID
	implements	SYMBOL_ID
{
	public		static	final	short[][]	token_map;
	public		static	final	String[]	token_name;
	public		static	final	int			NUM_KEYWORDS;
	public		static	final	int			NUM_SYMBOLS;
	public		static	final	int			TOTAL_COMPLEXITY;
	// short variables are short because they have a copy relation
	// with the token_map
	public		static	final	short		KEYWORD_COMPLEXITY;
	public		static	final	short		TOKEN_ID_OFFSET;
	public		static	final	short		IDENTIFIER_INDEX;
	public		static	final	short		ZERO_INDEX;
	public		static	final	short		REAL_INDEX;
	public		static	final	short		DECIMAL_INDEX;
	public		static	final	short		OCTAL_INDEX;
	public		static	final	short		HEX_INDEX;
	public		static	final	short		KEYWORD_GAP;
	static
	{
		// Between symbol and keyword groups, literals are placed.
		String[]  symbol_name  = (
		  "&= |= ^= += -= *= /= %= <<= >>= >>>= "+
		  "<= >= != == << >> >>> && || ++ -- ... "+
		  "= + - * / % & | ^ ! ~ < > ( ) [ ] { } "+
		  ", : ; . ? @" ) . split ( "\\s" );
		// Groups: other, types, [this, super], comment, text
		String[]  keyword_name  = (
		  "assert default do goto private abstract "+
		  "continue break implements protected "+
		  "throw else import public throws const "+
		  "case enum instanceof return transient "+
		  "catch extends try for new native final "+
		  "interface static void package class "+
		  "finally strictfp volatile false true "+
		  "null while if synchronized switch int "+
		  "long float double char short byte "+
		  "boolean this super // /* ' \"" ) . split ( "\\s" );
		HashSet<String>  token_set  = new HashSet<String> ( );
		for	( String token : symbol_name )
			if	( ! token_set . add ( token ) )
				System.err . println ( "Duplicate token: "+token );
		for	( String token : keyword_name )
			if	( ! token_set . add ( token ) )
				System.err . println ( "Duplicate token: "+token );
		NUM_SYMBOLS			= symbol_name.length;
		NUM_KEYWORDS		= keyword_name.length;
		token_name			= new String[NUM_SYMBOLS + NUM_KEYWORDS];
		System . arraycopy ( symbol_name,  0, token_name,  0,
		  NUM_SYMBOLS );
		System . arraycopy ( keyword_name,  0, token_name, NUM_SYMBOLS,
		  NUM_KEYWORDS );
		TOKEN_ID_OFFSET		= 128;
		/* Automatically generated. */
		KEYWORD_COMPLEXITY	= 296;
		int index  = KEYWORD_COMPLEXITY;
		IDENTIFIER_INDEX	= (short) index++;
		ZERO_INDEX			= (short) index++;
		REAL_INDEX			= (short) index++;
		DECIMAL_INDEX		= (short) index++;
		OCTAL_INDEX			= (short) index++;
		HEX_INDEX			= (short) index++;
		TOTAL_COMPLEXITY	= (short) index++;
		KEYWORD_GAP         =  5;
		token_map			= generate_token_map ( token_name,
		  TOKEN_ID_OFFSET, TOTAL_COMPLEXITY );
	}
	private		static	void	set_range ( short[][] map, int index,
	  short pointer, int from, int to )
	{
		for	( int i  =  from; i <= to; i++ )
			if	( map[index][i] ==  0 )
				map[index][i]  = pointer;
	}
	public		static	short[][]  generate_token_map_root (
	  String name[], short offset, int map_length )
	{
		short[][]  name_map;
		short	complexity;
		short	key;
		name_map  = new short[map_length][128];
		complexity  =  0;
		key         = offset;
		for	( int i = 0; i < name.length; i++ )
		{
			String	name_n;
			int		name_n_length;
			int		next_array;
			name_n         = name[i];
			name_n_length  = name_n . length ( );
			next_array     =  0;
			for	( int c = 0; c < name_n_length; c++)
			{
				int		array_value  =
				  name_map[next_array][name_n . charAt ( c )];
				if	( array_value ==  0 )
				{
					complexity++;
					name_map[next_array][name_n . charAt ( c )]  =
					  complexity;
					next_array  = complexity;
				}
				else
				{
					next_array  = array_value;
				}
			}
			if	( name_map[next_array][ 0] ==  0 )
				name_map[next_array][ 0]  = key++;
		}
		return	name_map;
	}
	public		static	short[][]  generate_token_map ( String name[],
	  short offset, int map_length )
	{
		short[][]  name_map;
		short	complexity;
		short	key;
		name_map    = new short[map_length][128];
		complexity  =  0;
		key			= offset;
		set_range ( name_map,  0, IDENTIFIER_INDEX, 'A', 'Z' );
		set_range ( name_map,  0, IDENTIFIER_INDEX, 'a', 'z' );
		name_map[ 0]['_']  = IDENTIFIER_INDEX;
		name_map[ 0]['$']  = IDENTIFIER_INDEX;
		for	( int i = 0; i < name.length; i++ )
		{
			String   name_n;
			int      name_n_length;
			int      next_array;
			boolean  is_subset;
			name_n         = name[i];
			name_n_length  = name_n . length ( );
			next_array     =  0;
			is_subset      = false;
			for	( int c = 0; c < name_n_length; c++ )
			{
				char	next_char;
				int		array_value;
				next_char    = name_n . charAt ( c );
				array_value  = name_map[next_array][next_char];
				block_0: {
				block_1: {
					if	( array_value == IDENTIFIER_INDEX )
					{
						is_subset  = true;
						break block_1;
					}
					else if ( array_value ==  0 )
					{
						break block_1;
					}
					else
					{
						next_array  = array_value;
						is_subset   = is_subset ||
						  name_map[next_array][ 0] == PREFIX_IDENTIFIER;
					}
					break block_0;
				} // block_1
					complexity++;
					name_map[next_array][next_char]  = complexity;
					next_array  =  complexity;
					if	( is_subset )
						name_map[next_array][ 0]  = PREFIX_IDENTIFIER;
				} // block_0
			}
			if	( is_subset )
			{
				set_range ( name_map, next_array,
				  IDENTIFIER_INDEX, 'A', 'Z' );
				set_range ( name_map, next_array,
				  IDENTIFIER_INDEX, 'a', 'z' );
				set_range ( name_map, next_array,
				  IDENTIFIER_INDEX, '0', '9' );
				name_map[next_array]['_'] = IDENTIFIER_INDEX;
				name_map[next_array]['$'] = IDENTIFIER_INDEX;
			}
			name_map[next_array][ 0]  = key;
			if	( key == AT )
			{
				key++;
				key += KEYWORD_GAP;
			}
			else
			{
				key++;
			}
		}
		name_map[ 0]['0']	       = ZERO_INDEX;
		name_map[ZERO_INDEX][ 0]   = PREFIX_ZERO;
		name_map[ZERO_INDEX]['.']  = DECIMAL_INDEX;
		name_map[ZERO_INDEX]['x']  = HEX_INDEX;
		name_map[ZERO_INDEX]['X']  = HEX_INDEX;
		set_range ( name_map, ZERO_INDEX, OCTAL_INDEX, '0', '7' );
		set_range ( name_map,  0, REAL_INDEX, '1', '9' );
		set_range ( name_map, name_map[ 0]['.'], DECIMAL_INDEX, '0',
		  '9' );
		name_map[REAL_INDEX]['.']       = DECIMAL_INDEX;
		name_map[IDENTIFIER_INDEX][ 0]  = PREFIX_IDENTIFIER;
		name_map[REAL_INDEX][ 0]        = PREFIX_REAL;
		name_map[DECIMAL_INDEX][ 0]     = PREFIX_DECIMAL;
		name_map[OCTAL_INDEX][ 0]       = PREFIX_OCTAL;
		name_map[HEX_INDEX][ 0]         = PREFIX_HEX;
		assert complexity == KEYWORD_COMPLEXITY : complexity;
		return	name_map;
	}
	public		static	String	get_token_name ( int ID )
	{
		if	( ID  > AT )
		{
			ID -= KEYWORD_GAP;
		}
		ID -= KeywordToID.TOKEN_ID_OFFSET;
		try {
			return	token_name[ID];
		}
		catch ( Exception e )
		{
			return	null;
		}
	}
	public		static	void	print_keyword_constants ( int offset )
	{
		int  keyword_index;
		int  keyword_ID;
		int  keyword_limit;
		keyword_index  = NUM_SYMBOLS;
		keyword_ID     = offset + NUM_SYMBOLS + KEYWORD_GAP;
		keyword_limit  = NUM_SYMBOLS + NUM_KEYWORDS;
		while	( keyword_index  < keyword_limit )
		{
			System.out . printf (
			  "\tpublic\t\tchar\t\t%s  = %d;\n",
			  token_name[keyword_index++] . toUpperCase ( ),
			  keyword_ID++ );
		}
	}
	public		static	void	print_parse_functions ( )
	{
		SourcePrinter out  = new SourcePrinter (  1 );
		for	( int i  =  0; i  < KEYWORD_COMPLEXITY; i++ )
		{
			out.println ( "symbol_part"+i+"(char symbol)" );
			out.open_block ( );
			out.println ( "switch(symbol)" );
			out.open_block ( );
			for	( int j  =  0; j  < 128; j++ )
			{
				int  next_function_ID  = token_map[i][j];
				if	( next_function_ID !=  0 )
				{
					out.println ( "case "+j+":" );
					out.println ( "return symbol_part"+next_function_ID+
					  "(source[++source_index]);" );
				}
			}
			out.println ( "default:" );
			out.println ( "return "+token_map[i][0] );
			out.close_block ( );
			out.close_block ( );
		}
	}
	public		static	void	main ( String[] args )
	{
		print_keyword_constants ( TOKEN_ID_OFFSET );
	}
}
