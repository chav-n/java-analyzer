public class ExtensionTry
	extends Parser
{
	protected static String ext_prologue_name = "ExtensionTry: Prologue";
	protected static String ext_catch_name = "ExtensionTry: Catch";
	protected static String ext_catch_type_name = "ExtensionTry: Catch Type";
	protected static String ext_catch_variable_name = "ExtensionTry: Catch Variable";
	protected static String ext_post_try_name = "ExtensionTry: Post Try";
	protected static String ext_post_catch_name = "ExtensionTry: Post Catch";
	protected int ext_prologue(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_prologue_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LCURLYBRACE:
			return ext_post_try(ext_method.ext_type(Lexer.next_token()));
		case LPAREN:
			return ext_post_try(ext_method.ext_type(accept(LCURLYBRACE, accept(RPAREN, ext_common.ext_value(Lexer.next_token())))));
		case INVALID:
		default:
			return parse_error(ext_prologue_name, token_ID);
		}
	}
	protected int ext_catch(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_catch_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LPAREN:
			return ext_catch_type(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_catch_name, token_ID);
		}
	}
	protected int ext_catch_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_catch_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case IDENTIFIER:
				return ext_catch_variable(Lexer.next_token());
			case FINAL:
				token_ID = Lexer.next_token();
				continue;
			case INVALID:
			default:
				return parse_error(ext_catch_type_name, token_ID);
			}
		}
	}
	protected int ext_catch_variable(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_catch_variable_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case IDENTIFIER:
				return accept(RPAREN, Lexer.next_token());
			case DOT:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case INVALID:
			default:
				return parse_error(ext_catch_variable_name, token_ID);
			}
		}
	}
	protected int ext_post_try(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_try_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case CATCH:
			return ext_post_catch(ext_method.ext_type(accept(LCURLYBRACE, ext_catch(Lexer.next_token()))));
		case FINALLY:
			return ext_method.ext_type(accept(LCURLYBRACE, Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_post_try_name, token_ID);
		}
	}
	protected int ext_post_catch(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_post_catch_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case CATCH:
				token_ID = ext_method.ext_type(accept(LCURLYBRACE, ext_catch(Lexer.next_token())));
				continue;
			case FINALLY:
				return ext_method.ext_type(accept(LCURLYBRACE, Lexer.next_token()));
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_post_catch_name, token_ID);
			}
		}
	}
}
