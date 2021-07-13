public class ExtensionDot
	extends Parser
{
	protected static String ext_check_method_name = "ExtensionDot: Check Method";
	protected static String ext_check_type_name = "ExtensionDot: Check Type";
	protected static String ext_check_for_type_name = "ExtensionDot: Check For Type";
	protected static String ext_check_for_type_next_name = "ExtensionDot: Check For Type Next";
	protected static String ext_check_cast_name = "ExtensionDot: Check Cast";
	protected static String ext_post_parameters_name = "ExtensionDot: Post Parameters";
	protected int ext_check_method(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_method_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case NEW:
			return ext_common.ext_new(Lexer.next_token());
		case IDENTIFIER:
		case THIS:
		case SUPER:
		case CLASS:
			return ext_common.ext_method_check(Lexer.next_token());
		case LESS:
			return ext_common.ext_method_check(ext_post_parameters(ext_common.ext_parameters(Lexer.next_token())));
		case INVALID:
		default:
			return parse_error(ext_check_method_name, token_ID);
		}
	}
	protected int ext_check_type(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_type_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case NEW:
			return ext_common.ext_new(Lexer.next_token());
		case IDENTIFIER:
		case THIS:
		case SUPER:
		case CLASS:
			return ext_method.ext_post_type(Lexer.next_token());
		case LESS:
			return ext_method.ext_post_type(ext_post_parameters(ext_common.ext_parameters(Lexer.next_token())));
		case INVALID:
		default:
			return parse_error(ext_check_type_name, token_ID);
		}
	}
	protected int ext_check_for_type(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_for_type_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_method.ext_for_post_type(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_check_for_type_name, token_ID);
		}
	}
	protected int ext_check_for_type_next(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_for_type_next_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_method.ext_for_post_type_next(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_check_for_type_next_name, token_ID);
		}
	}
	protected int ext_check_cast(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_cast_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case NEW:
			return accept(RPAREN, ext_common.ext_new(Lexer.next_token()));
		case IDENTIFIER:
			return ext_cast.ext_check_identifier(Lexer.next_token());
		case THIS:
		case SUPER:
		case CLASS:
			return accept(RPAREN, ext_common.ext_method_check(Lexer.next_token()));
		case LESS:
			return accept(RPAREN, ext_common.ext_method_check(ext_post_parameters(ext_common.ext_parameters(Lexer.next_token()))));
		case INVALID:
		default:
			return parse_error(ext_check_cast_name, token_ID);
		}
	}
	protected int ext_post_parameters(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_parameters_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_post_parameters_name, token_ID);
		}
	}
}
