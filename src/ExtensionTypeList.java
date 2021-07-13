public class ExtensionTypeList
	extends Parser
{
	protected static String ext_type_name = "ExtensionTypeList: Type";
	protected static String ext_post_type_name = "ExtensionTypeList: Post Type";
	protected static String ext_class_extends_name = "ExtensionTypeList: Class Extends";
	protected static String ext_class_implements_name = "ExtensionTypeList: Class Implements";
	protected static String ext_enum_implements_name = "ExtensionTypeList: Enum Implements";
	protected static String ext_method_throws_name = "ExtensionTypeList: Method Throws";
	protected int ext_type(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_type_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_type(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_type_name, token_ID);
		}
	}
	protected int ext_post_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_post_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case DOT:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case LESS:
				token_ID = ext_common.ext_parameters(Lexer.next_token());
				continue;
			case COMMA:
				return ext_type(Lexer.next_token());
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_post_type_name, token_ID);
			}
		}
	}
	protected int ext_class_extends(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_class_extends_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IMPLEMENTS:
			return ext_class_implements(ext_type(Lexer.next_token()));
		case LCURLYBRACE:
			return ext_class.ext_type(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_class_extends_name, token_ID);
		}
	}
	protected int ext_class_implements(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_class_implements_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LCURLYBRACE:
			return ext_class.ext_type(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_class_implements_name, token_ID);
		}
	}
	protected int ext_enum_implements(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_enum_implements_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LCURLYBRACE:
			return ext_enum.ext_first_value(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_enum_implements_name, token_ID);
		}
	}
	protected int ext_method_throws(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_method_throws_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LCURLYBRACE:
			return ext_method.ext_type(Lexer.next_token());
		case SEMICOLON:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_method_throws_name, token_ID);
		}
	}
}
