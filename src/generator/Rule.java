package	generator;

import	java.util.Vector;

public		class	Rule
{
	public		final		String		name;
	public		final		String		debug_name;
	public		final		Vector <RuleExpression>  expressions;
	private		RuleExpression  next_expression;
	private		Vector <String>  next_comments;
	private		int			recursion_flag;
	private		int			recursive_expressions;
	public		Rule ( String identifier )
	{
		Vector <String> words  = parse_identifier ( identifier );
		name				= parse_name ( words );
		debug_name			= parse_debug_name ( words );
		expressions			= new Vector <RuleExpression> ( );
		next_comments		= new Vector <String> ( );
		recursion_flag		=  1;
		recursive_expressions  =  0;
	}
	private		Vector <String>  parse_identifier (
	  String str )
	{
		Vector <String>  vec  = new ( );
		int  start_index  =  0;
		for	( int i : start_index + 1, i+1, str.length() )
			if	( Character . isUpperCase ( str . charAt ( i ) ) )
			{
				vec . add ( str . substring ( start_index, i ) );
				start_index  = i;
			}
		vec . add ( str . substring ( start_index ) );
		return	vec;
	}
	private		String	parse_name ( Vector <String> words )
	{
		String  name  = "ext";
		for	( String word : words )
			name += "_"+word . toLowerCase ( );
		return	name;
	}
	private		String	parse_debug_name ( Vector <String> words )
	{
		String  name  = "";
		for	( String word : words )
			name += " "+word;
		name  = name . substring (  1 );
		return	name;
	}
	public		void	new_expression ( String target )
	{
		next_expression  = new RuleExpression ( target, next_comments );
	}
	public		void	new_expression ( )
	{
		new_expression ( "Lexer.next_token()" );
	}
	public		void	add_symbol ( String name, int type_ID,
	  int relation_ID )
	{
		next_expression . add_symbol ( new RuleSymbol (
		  name, type_ID, relation_ID ) );
		recursion_flag  =  1;
	}
	public		void	add_symbol_self ( int relation_ID )
	{
		next_expression . add_symbol ( new RuleSymbol (
		  "ext", RuleSymbolKind.NON_TERMINAL, relation_ID ) );
		recursion_flag  =  0;
	}
	public		void	add_comment ( String comment )
	{
		next_comments . add ( comment );
	}
	public		void	save_expression ( )
	{
		if	( recursion_flag ==  0 )
			recursive_expressions++;
		expressions . add ( next_expression );
		next_expression = null;
		recursion_flag  =  1;
	}
	public		boolean  is_recursive ( )
	{
		return	recursive_expressions  >  0;
	}
}
