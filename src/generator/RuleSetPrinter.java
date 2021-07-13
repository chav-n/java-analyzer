package	generator;

import	java.util.Vector;
import	java.util.Iterator;
import	java.io.PrintStream;

public		class	RuleSetPrinter
	implements	RuleSymbolKind
{
	private		PrintStream			out;
	private		int					tabulation;
	private		int					default_flag;
	public		void	add_default ( )
	{
		println ( "default:" );
		default_flag  =  0;
	}
	public		void	print_class_header ( String class_name )
	{
		println ( format ( "public class %s", class_name ) );
		println ( "\textends Parser" );
	}
	public		void	print_rule_name_variable ( String class_name,
	  Rule rule )
	{
		println ( format ( "protected static String %s = \"%s: %s\";",
		  rule.name+"_name", class_name, rule.debug_name ) );
	}
	public		void	print_rule_method ( Rule rule )
	{
		println ( format ( "protected int %s(int token_ID)",
		  rule.name ) );
		open_block ( );
		String debug_statement;
		if	( rule . is_recursive ( ) )
		{
			println ( "for(;;)" );
			open_block ( );
		}
		debug_statement  = "if\t(debug_out != null) ";
		debug_statement += "debug_out.println(%s_name+\": \"+";
		debug_statement += "Lexer.get_token_name(token_ID));";
		println ( format ( debug_statement, rule.name ) );
		println ( "switch(token_ID)" );
		println ( "{" );
		default_flag  =  1;
		for	( RuleExpression expression : rule.expressions )
		{
			for	( String comment : expression.comments )
				println ( comment );
			consume_expression ( rule.name,
			  expression.symbols . iterator ( ), expression.target );
		}
		println ( "case INVALID:" );
		if	( default_flag ==  1 )
			add_default ( );
		println ( format (
		  "\treturn parse_error(%s_name, token_ID);",
		  rule.name ) );
		println ( "}" );
		if	( rule . is_recursive ( ) )
			close_block ( );
		close_block ( );
	}
	public		void	consume_expression ( String name,
	  Iterator <RuleSymbol> iterator, String target )
	{
		RuleSymbol  next_symbol;
		String      expression;

		// Check for empty symbol
		if	( iterator . hasNext ( ) )
		{
			next_symbol  = iterator . next ( );
		}
		else
		{
			add_default ( );
			println ( format ( "\treturn %s;", target ) );
			return;
		}
		// Generate case labels.
		switch	( next_symbol.type_ID )
		{
		case TERMINAL:
			for	(;;)
			{
				println ( format ( "case %s:", next_symbol.name ) );
				if	( iterator . hasNext ( ) )
				{
					next_symbol  = iterator . next ( );
					if	( next_symbol.type_ID     != TERMINAL ||
						  next_symbol.relation_ID != OR          )
						break;
				}
				else
				{
					println ( format ( "\treturn %s;", target ) );
					return;
				}
			}
			break;
		case NON_TERMINAL:
			next_symbol  = next_symbol;
			add_default ( );
			break;
		default:
			next_symbol  = null;
		}
		// Generate function expression.
		expression = target;
		for	(;;)
		{
			switch	( next_symbol.type_ID )
			{
			case TERMINAL:
				expression  = format ( "accept(%s, %s)",
				  next_symbol.name, expression );
				break;
			case NON_TERMINAL:
				String  symbol_name;
				if	( next_symbol.name . equals ( "ext" ) )
				{
					if	( iterator . hasNext ( ) )
					{
						symbol_name  = name;
					}
					else
					{
						println ( format ( "\ttoken_ID = %s;",
						  expression ) );
						println ( "\tcontinue;" );
						return;
					}
				}
				else
				{
					symbol_name  = next_symbol.name;
				}
				expression  = format ( "%s(%s)",
				  symbol_name, expression );
			}
			if	( iterator . hasNext ( ) )
			{
				next_symbol  = iterator . next ( );
			}
			else
			{
				println	( format ( "\treturn %s;", expression ) );
				return;
			}
		}
	}
	private		String	format ( String text, Object ... args )
	{
		return	String . format ( text, args );
	}
	public		void	open_block ( )
	{
		println ( "{" );
		tabulation++;
	}
	public		void	close_block ( )
	{
		tabulation--;
		println ( "}" );
	}
	public		void	println ( String line )
	{
		for	( int i  =  0; i  < tabulation; i++ )
			out . print ( '\t' );
		out . println ( line );
	}
	public		void	println ( )
	{
		out . println ( );
	}
	public		void	print ( RuleSet rule_set,
	  String output_dir_path )
		throws	Exception
	{
		String  class_name,
		        output_file;
		class_name       = "Extension"+rule_set.name;
		output_file      = output_dir_path+"\\"+class_name+".java";
		out              = new PrintStream ( output_file );
		tabulation       =  0;
		print_class_header ( class_name );
		open_block ( );
		for	( Rule rule : rule_set.rules )
			print_rule_name_variable ( class_name, rule );
		for ( Rule rule : rule_set.rules )
			print_rule_method ( rule );
		close_block ( );
		out . close ( );
	}
}
