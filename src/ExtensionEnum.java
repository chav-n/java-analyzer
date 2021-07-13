public class ExtensionEnum
	extends Parser
{
	protected static String ext_prologue_name = "ExtensionEnum: Prologue";
	protected static String ext_check_implements_name = "ExtensionEnum: Check Implements";
	protected static String ext_first_value_name = "ExtensionEnum: First Value";
	protected static String ext_next_value_name = "ExtensionEnum: Next Value";
	protected static String ext_value_name = "ExtensionEnum: Value";
	protected int ext_prologue(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_prologue_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_check_implements(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_prologue_name, token_ID);
		}
	}
	protected int ext_check_implements(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_implements_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IMPLEMENTS:
			return ext_type_list.ext_enum_implements(ext_type_list.ext_type(Lexer.next_token()));
		case LCURLYBRACE:
			return ext_first_value(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_check_implements_name, token_ID);
		}
	}
	protected int ext_first_value(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_first_value_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_value(Lexer.next_token());
		case RCURLYBRACE:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_first_value_name, token_ID);
		}
	}
	protected int ext_next_value(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_next_value_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_value(Lexer.next_token());
		case RCURLYBRACE:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_next_value_name, token_ID);
		}
	}
	protected int ext_value(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_value_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LPAREN:
				token_ID = ext_value_end.ext_arguments(ext_common.ext_value(Lexer.next_token()));
				continue;
			case LCURLYBRACE:
				token_ID = ext_class.ext_type(Lexer.next_token());
				continue;
			case RCURLYBRACE:
				return Lexer.next_token();
			case SEMICOLON:
				return ext_class.ext_type(Lexer.next_token());
			case COMMA:
				return ext_next_value(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_value_name, token_ID);
			}
		}
	}
}
