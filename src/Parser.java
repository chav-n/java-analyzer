import	java.io.PrintStream;
import	java.io.IOException;

//import	scanner.Scanner;
import	scanner.InvalidInputException;
//import	scanner.ClassFileConstants;

public		class	Parser
	implements	EXTENSION_SET,
				SYMBOL_ID
{
	private		static	String				ext_name;
	protected	static	String				attribute;
	protected	static	int					branch;
	private		static	int					error_flag;
	protected	static	int					case_count;
	protected	static	PrintStream			debug_out;
	protected	static	String[]			att_stack;
	protected	static	int					att_stack_count;
	private		static	int					att_stack_size;
	protected	static	ASTNode[]			ast_stack;
	protected	static	int					ast_stack_count;
	private		static	int					ast_stack_size;
	protected	static	int[]				branch_stack;
	protected	static	int					branch_stack_count;
	private		static	int					branch_stack_size;
	private		static	int					line_in_max_size;
	private		static	final	int			stack_increment;
	static
	{
		stack_increment		= 25;
	}
	public		static	void	init ( PrintStream ps )
	{
		attribute			= "";
		att_stack			= new String[stack_increment];
		att_stack_size		= stack_increment;
		att_stack_count		=  0;
		ast_stack			= new ASTNode[stack_increment];
		ast_stack_size		= stack_increment;
		ast_stack_count		=  0;
		branch_stack		= new int[stack_increment];
		branch_stack_size	= stack_increment;
		branch_stack_count	=  0;
		ext_name			= null;
		error_flag			=  0;
		case_count			=  0;
		line_in_max_size    =  0;
		debug_out			= ps;
	}
	protected	static	void	run_automaton ( )
		throws	SyntaxException,
				IOException,
				InvalidInputException
	{
		//while	( Lexer . next_token ( ) != EOF );
		/*Scanner  scanner  = new Scanner ( true, true, true,
		  ClassFileConstants.JDK1_6, null, null, true );
		scanner . setSource ( Lexer.source );*/
		if	( ext_head.ext_package_prologue ( Lexer . next_token ( ) ) != EOF )
		{
			ParserConsole . error ( error_flag );
		}
	}
	protected	int		accept ( int token_a, int token_b )
	{
		if	( debug_out != null ) debug_out . printf (
		  "Accept: %s\n", Lexer . get_token_name ( token_b ) );
		if	( token_a == token_b )
			return	Lexer . next_token ( );
		else
			return	parse_error(
			  "Accept("+Lexer . get_token_name ( token_a )+")",
			  token_b );
	}
	protected	static	int		parse_error ( String ext_name_in,
	  int token_ID )
	{
		if	( ext_name == null )
		{
			ext_name     = ext_name_in;
			error_flag   = token_ID;
			if	( debug_out != null )
				new Exception ( ) . printStackTrace ( );
		}
		return	INVALID;
	}
	protected	static	void	attribute_push ( String att )
	{
		if	( att_stack_count == att_stack_size )
		{
			System.arraycopy (
			  att_stack, 0,
			  att_stack = new String
				[att_stack_size + stack_increment],
			  0, att_stack_size );
			att_stack_size += stack_increment;
		}
		att_stack[att_stack_count++]  = att;
	}
	protected	static	void	attribute_pop ( )
	{
		if	( att_stack_count !=  0 )
		{
			attribute  = att_stack[--att_stack_count];
		}
		else
		{
			error_flag  =  3;
		}
	}
	protected	static	void	ast_push ( ASTNode node )
	{
		if	( ast_stack_count == ast_stack_size )
		{
			System.arraycopy (
			  ast_stack, 0,
			  ast_stack = new ASTNode
				[ast_stack_size + stack_increment],
			  0, ast_stack_size );
			ast_stack_size += stack_increment;
		}
		ast_stack[ast_stack_count++]  = node;
	}
	protected	static	void	branch_push ( )
	{
		if	( branch_stack_count == branch_stack_size )
		{
			System.arraycopy (
			  branch_stack, 0,
			  branch_stack = new int
				[branch_stack_size + stack_increment],
			  0, branch_stack_size );
			branch_stack_size += stack_increment;
		}
		branch_stack[branch_stack_count++]  = ast_stack_count;
	}
	protected	static	void	branch_pop ( )
	{
		branch  = branch_stack[--branch_stack_count];
	}
	protected	static	String	get_ext_name ( )
	{
		return	ext_name;
	}
	public		static	void	println ( String str )
	{
		if	( debug_out != null )
		{
			debug_out . println ( str );
		}
	}
	protected	static	void	check_parser ( )
		throws	SyntaxException
	{
		if	( case_count  >  0 )
		{
			throw	new SyntaxException ( "Cases found." );
		}
	}
}
