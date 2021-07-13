public class ExtensionValueEnd
	extends Parser
{
	protected static String ext_single_value_name = "ExtensionValueEnd: Single Value";
	protected static String ext_arguments_name = "ExtensionValueEnd: Arguments";
	protected static String ext_method_variable_name = "ExtensionValueEnd: Method Variable";
	protected static String ext_braces_name = "ExtensionValueEnd: Braces";
	protected static String ext_for_assignment_name = "ExtensionValueEnd: For Assignment";
	protected static String ext_for_variable_name = "ExtensionValueEnd: For Variable";
	protected static String ext_for_assignment_next_name = "ExtensionValueEnd: For Assignment Next";
	protected static String ext_for_list_name = "ExtensionValueEnd: For List";
	protected static String ext_tertiary_assignment_name = "ExtensionValueEnd: Tertiary Assignment";
	protected static String ext_assert_clause_name = "ExtensionValueEnd: Assert Clause";
	protected int ext_single_value(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_single_value_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case SEMICOLON:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_single_value_name, token_ID);
		}
	}
	protected int ext_arguments(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_arguments_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case COMMA:
				token_ID = ext_common.ext_value(Lexer.next_token());
				continue;
			case RPAREN:
				return Lexer.next_token();
			case INVALID:
			default:
				return parse_error(ext_arguments_name, token_ID);
			}
		}
	}
	protected int ext_method_variable(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_method_variable_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case COMMA:
				token_ID = ext_common.ext_value(Lexer.next_token());
				continue;
			case SEMICOLON:
				return Lexer.next_token();
			case INVALID:
			default:
				return parse_error(ext_method_variable_name, token_ID);
			}
		}
	}
	protected int ext_braces(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_braces_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case COMMA:
				token_ID = ext_common.ext_value(Lexer.next_token());
				continue;
			case RCURLYBRACE:
				return Lexer.next_token();
			case INVALID:
			default:
				return parse_error(ext_braces_name, token_ID);
			}
		}
	}
	protected int ext_for_assignment(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_for_assignment_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		/* For the ( String param : params . next ( ) )
		   case. */
		case COMMA:
			return ext_method.ext_for_type_next(Lexer.next_token());
		/* For the ( String param : params . next ( ) )
		   case. */
		case SEMICOLON:
			return ext_arguments(ext_common.ext_value(ext_single_value(ext_common.ext_value(Lexer.next_token()))));
		/* For the ( String param : params . next ( ) )
		   case. */
		case COLON:
			return ext_arguments(ext_common.ext_value(Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_for_assignment_name, token_ID);
		}
	}
	protected int ext_for_variable(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_for_variable_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case COMMA:
			return ext_method.ext_for_next_variable(Lexer.next_token());
		case SEMICOLON:
			return ext_arguments(ext_common.ext_value(ext_single_value(ext_common.ext_value(Lexer.next_token()))));
		case INVALID:
		default:
			return parse_error(ext_for_variable_name, token_ID);
		}
	}
	protected int ext_for_assignment_next(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_for_assignment_next_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case COMMA:
			return ext_method.ext_for_type_next(Lexer.next_token());
		case SEMICOLON:
			return ext_arguments(ext_common.ext_value(ext_single_value(ext_common.ext_value(Lexer.next_token()))));
		case INVALID:
		default:
			return parse_error(ext_for_assignment_next_name, token_ID);
		}
	}
	protected int ext_for_list(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_for_list_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case RPAREN:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_for_list_name, token_ID);
		}
	}
	protected int ext_tertiary_assignment(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_tertiary_assignment_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case COLON:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_tertiary_assignment_name, token_ID);
		}
	}
	protected int ext_assert_clause(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_assert_clause_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case SEMICOLON:
			return Lexer.next_token();
		case COLON:
			return ext_single_value(ext_common.ext_value(Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_assert_clause_name, token_ID);
		}
	}
}
