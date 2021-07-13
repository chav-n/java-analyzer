public class ExtensionMethod
	extends Parser
{
	protected static String ext_prologue_name = "ExtensionMethod: Prologue";
	protected static String ext_statement_name = "ExtensionMethod: Statement";
	protected static String ext_type_name = "ExtensionMethod: Type";
	protected static String ext_post_type_name = "ExtensionMethod: Post Type";
	protected static String ext_variable_name = "ExtensionMethod: Variable";
	protected static String ext_label_name = "ExtensionMethod: Label";
	protected static String ext_post_label_name = "ExtensionMethod: Post Label";
	protected static String ext_for_type_name = "ExtensionMethod: For Type";
	protected static String ext_for_post_type_name = "ExtensionMethod: For Post Type";
	protected static String ext_for_variable_name = "ExtensionMethod: For Variable";
	protected static String ext_for_next_variable_name = "ExtensionMethod: For Next Variable";
	protected static String ext_for_post_next_variable_name = "ExtensionMethod: For Post Next Variable";
	protected static String ext_for_type_next_name = "ExtensionMethod: For Type Next";
	protected static String ext_for_post_type_next_name = "ExtensionMethod: For Post Type Next";
	protected static String ext_for_inc_dec_name = "ExtensionMethod: For Inc Dec";
	protected static String ext_for_post_inc_dec_name = "ExtensionMethod: For Post Inc Dec";
	protected static String ext_do_value_name = "ExtensionMethod: Do Value";
	protected static String ext_switch_name = "ExtensionMethod: Switch";
	protected static String ext_switch_case_name = "ExtensionMethod: Switch Case";
	protected static String ext_post_if_name = "ExtensionMethod: Post If";
	protected int ext_prologue(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_prologue_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case THROWS:
				return ext_type_list.ext_method_throws(ext_type_list.ext_type(Lexer.next_token()));
			case DEFAULT:
				return ext_value_end.ext_single_value(ext_common.ext_value(Lexer.next_token()));
			case LCURLYBRACE:
				return ext_type(Lexer.next_token());
			case SEMICOLON:
				return Lexer.next_token();
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case INVALID:
			default:
				return parse_error(ext_prologue_name, token_ID);
			}
		}
	}
	protected int ext_statement(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_statement_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case IF:
				return ext_post_if(ext_statement(ext_common.ext_pre_brackets(Lexer.next_token())));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case WHILE:
				token_ID = ext_common.ext_pre_brackets(Lexer.next_token());
				continue;
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case FOR:
				token_ID = ext_for_type(accept(LPAREN, Lexer.next_token()));
				continue;
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case DO:
				return accept(SEMICOLON, ext_common.ext_pre_brackets(accept(WHILE, ext_statement(Lexer.next_token()))));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case SWITCH:
				return ext_switch(accept(LCURLYBRACE, ext_common.ext_pre_brackets(Lexer.next_token())));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case TRY:
				return ext_try.ext_prologue(Lexer.next_token());
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case SYNCHRONIZED:
				return ext_type(accept(LCURLYBRACE, ext_common.ext_pre_brackets(Lexer.next_token())));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case NEW:
				return ext_value_end.ext_single_value(ext_common.ext_method_check(ext_common.ext_new(Lexer.next_token())));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case ASSERT:
				return ext_value_end.ext_assert_clause(ext_common.ext_value(Lexer.next_token()));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case RETURN:
			case THROW:
				return ext_value_end.ext_single_value(ext_common.ext_value(Lexer.next_token()));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case BREAK:
			case CONTINUE:
				return ext_label(Lexer.next_token());
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case CLASS:
			case INTERFACE:
				return ext_class.ext_definition(Lexer.next_token());
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
				return ext_variable(ext_common.ext_primitive_declaration(Lexer.next_token()));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case IDENTIFIER:
			case SUPER:
			case THIS:
			case STRING_LITERAL:
				return ext_post_type(Lexer.next_token());
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case LCURLYBRACE:
				return ext_type(Lexer.next_token());
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case LPAREN:
				return ext_value_end.ext_single_value(ext_common.ext_post_method_check(accept(RPAREN, ext_common.ext_value(Lexer.next_token()))));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case PLUS_PLUS:
			case MINUS_MINUS:
				return ext_value_end.ext_single_value(ext_common.ext_inc_dec(Lexer.next_token()));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case FINAL:
			case ABSTRACT:
			case SEMICOLON:
				return Lexer.next_token();
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			case AT:
				return ext_annotation.ext_method_type(ext_annotation.ext_type(Lexer.next_token()));
			/* For ((SubClass) superClass).method ( ) case. */
			/* For [++i;] and [--i;] cases. */
			/* SEMICOLON: For blocks that end with a ';' and empty
			   statements. */
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_statement_name, token_ID);
			}
		}
	}
	protected int ext_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case RCURLYBRACE:
				return Lexer.next_token();
			default:
				token_ID = ext_statement(token_ID);
				continue;
			case INVALID:
				return parse_error(ext_type_name, token_ID);
			}
		}
	}
	protected int ext_post_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_post_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			/* For the label case. */
			case IDENTIFIER:
				return ext_variable(Lexer.next_token());
			/* For the label case. */
			case DOT:
				return ext_dot.ext_check_type(Lexer.next_token());
			/* For the label case. */
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, ext_common.ext_value(Lexer.next_token()));
				continue;
			/* For the label case. */
			case LPAREN:
				return accept(SEMICOLON, ext_common.ext_post_method_check(ext_value_end.ext_arguments(ext_common.ext_value(Lexer.next_token()))));
			/* For the label case. */
			case LESS:
				token_ID = ext_common.ext_parameters(Lexer.next_token());
				continue;
			/* For the label case. */
			case COLON:
				return Lexer.next_token();
			/* For the label case. */
			case EQUAL:
			case AND_EQUAL:
			case OR_EQUAL:
			case XOR_EQUAL:
			case PLUS_EQUAL:
			case MINUS_EQUAL:
			case MULTIPLY_EQUAL:
			case DIVIDE_EQUAL:
			case REMAINDER_EQUAL:
			case LEFT_SHIFT_EQUAL:
			case RIGHT_SHIFT_EQUAL:
			case UNSIGNED_RIGHT_SHIFT_EQUAL:
				return ext_value_end.ext_single_value(ext_common.ext_value(Lexer.next_token()));
			/* For the label case. */
			case PLUS_PLUS:
			case MINUS_MINUS:
				return accept(SEMICOLON, Lexer.next_token());
			/* For the label case. */
			case SEMICOLON:
				return Lexer.next_token();
			case INVALID:
			default:
				return parse_error(ext_post_type_name, token_ID);
			}
		}
	}
	protected int ext_variable(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_variable_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case EQUAL:
				return ext_value_end.ext_method_variable(ext_common.ext_value(Lexer.next_token()));
			case COMMA:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case IDENTIFIER:
				token_ID = Lexer.next_token();
				continue;
			case SEMICOLON:
				return Lexer.next_token();
			case INVALID:
			default:
				return parse_error(ext_variable_name, token_ID);
			}
		}
	}
	protected int ext_label(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_label_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_label(Lexer.next_token());
		case SEMICOLON:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_label_name, token_ID);
		}
	}
	protected int ext_post_label(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_label_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case SEMICOLON:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_post_label_name, token_ID);
		}
	}
	protected int ext_for_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_for_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
				return ext_for_variable(ext_common.ext_primitive_declaration(Lexer.next_token()));
			case IDENTIFIER:
			case SUPER:
			case THIS:
				return ext_for_post_type(Lexer.next_token());
			case AT:
				return ext_annotation.ext_for_type(ext_annotation.ext_type(Lexer.next_token()));
			case FINAL:
				token_ID = Lexer.next_token();
				continue;
			case PLUS_PLUS:
			case MINUS_MINUS:
				return ext_value_end.ext_for_assignment_next(ext_for_inc_dec(Lexer.next_token()));
			case SEMICOLON:
				return ext_value_end.ext_arguments(ext_common.ext_value(ext_value_end.ext_single_value(ext_common.ext_value(Lexer.next_token()))));
			case INVALID:
			default:
				return parse_error(ext_for_type_name, token_ID);
			}
		}
	}
	protected int ext_for_post_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_for_post_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case IDENTIFIER:
				return ext_for_variable(Lexer.next_token());
			case DOT:
				return ext_dot.ext_check_for_type(Lexer.next_token());
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, ext_common.ext_value(Lexer.next_token()));
				continue;
			case LESS:
				token_ID = ext_common.ext_parameters(Lexer.next_token());
				continue;
			case EQUAL:
			case AND_EQUAL:
			case OR_EQUAL:
			case XOR_EQUAL:
			case PLUS_EQUAL:
			case MINUS_EQUAL:
			case MULTIPLY_EQUAL:
			case DIVIDE_EQUAL:
			case REMAINDER_EQUAL:
			case LEFT_SHIFT_EQUAL:
			case RIGHT_SHIFT_EQUAL:
			case UNSIGNED_RIGHT_SHIFT_EQUAL:
				return ext_value_end.ext_for_assignment(ext_common.ext_value(Lexer.next_token()));
			case LPAREN:
				return ext_value_end.ext_for_assignment(ext_value_end.ext_arguments(ext_common.ext_value(Lexer.next_token())));
			case COMMA:
				return ext_for_type_next(Lexer.next_token());
			case PLUS_PLUS:
			case MINUS_MINUS:
				return ext_value_end.ext_for_assignment_next(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_for_post_type_name, token_ID);
			}
		}
	}
	protected int ext_for_variable(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_for_variable_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case EQUAL:
				return ext_value_end.ext_for_variable(ext_common.ext_value(Lexer.next_token()));
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case COMMA:
				return ext_for_next_variable(Lexer.next_token());
			case SEMICOLON:
				return ext_value_end.ext_arguments(ext_common.ext_value(ext_value_end.ext_single_value(ext_common.ext_value(Lexer.next_token()))));
			case COLON:
				return ext_value_end.ext_for_list(ext_common.ext_value(Lexer.next_token()));
			case INVALID:
			default:
				return parse_error(ext_for_variable_name, token_ID);
			}
		}
	}
	protected int ext_for_next_variable(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_for_next_variable_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_for_post_next_variable(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_for_next_variable_name, token_ID);
		}
	}
	protected int ext_for_post_next_variable(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_for_post_next_variable_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case EQUAL:
			case AND_EQUAL:
			case OR_EQUAL:
			case XOR_EQUAL:
			case PLUS_EQUAL:
			case MINUS_EQUAL:
			case MULTIPLY_EQUAL:
			case DIVIDE_EQUAL:
			case REMAINDER_EQUAL:
			case LEFT_SHIFT_EQUAL:
			case RIGHT_SHIFT_EQUAL:
			case UNSIGNED_RIGHT_SHIFT_EQUAL:
				return ext_value_end.ext_for_variable(ext_common.ext_value(Lexer.next_token()));
			case LPAREN:
				return ext_value_end.ext_for_variable(ext_value_end.ext_arguments(ext_common.ext_value(Lexer.next_token())));
			case COMMA:
				return ext_for_next_variable(Lexer.next_token());
			case SEMICOLON:
				return ext_value_end.ext_arguments(ext_common.ext_value(ext_value_end.ext_single_value(ext_common.ext_value(Lexer.next_token()))));
			case INVALID:
			default:
				return parse_error(ext_for_post_next_variable_name, token_ID);
			}
		}
	}
	protected int ext_for_type_next(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_for_type_next_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
		case SUPER:
		case THIS:
			return ext_for_post_type_next(Lexer.next_token());
		case PLUS_PLUS:
		case MINUS_MINUS:
			return ext_value_end.ext_for_assignment(ext_for_inc_dec(Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_for_type_next_name, token_ID);
		}
	}
	protected int ext_for_post_type_next(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_for_post_type_next_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case DOT:
				return ext_dot.ext_check_for_type(Lexer.next_token());
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, ext_common.ext_value(Lexer.next_token()));
				continue;
			case EQUAL:
			case AND_EQUAL:
			case OR_EQUAL:
			case XOR_EQUAL:
			case PLUS_EQUAL:
			case MINUS_EQUAL:
			case MULTIPLY_EQUAL:
			case DIVIDE_EQUAL:
			case REMAINDER_EQUAL:
			case LEFT_SHIFT_EQUAL:
			case RIGHT_SHIFT_EQUAL:
			case UNSIGNED_RIGHT_SHIFT_EQUAL:
				return ext_value_end.ext_for_assignment_next(ext_common.ext_value(Lexer.next_token()));
			case LPAREN:
				return ext_value_end.ext_for_assignment_next(ext_value_end.ext_arguments(ext_common.ext_value(Lexer.next_token())));
			case COMMA:
				return ext_for_type_next(Lexer.next_token());
			case PLUS_PLUS:
			case MINUS_MINUS:
				token_ID = Lexer.next_token();
				continue;
			case INVALID:
			default:
				return parse_error(ext_for_post_type_next_name, token_ID);
			}
		}
	}
	protected int ext_for_inc_dec(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_for_inc_dec_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_for_post_inc_dec(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_for_inc_dec_name, token_ID);
		}
	}
	protected int ext_for_post_inc_dec(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_for_post_inc_dec_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case DOT:
			return ext_for_inc_dec(Lexer.next_token());
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_for_post_inc_dec_name, token_ID);
		}
	}
	protected int ext_do_value(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_do_value_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case WHILE:
				token_ID = ext_statement(ext_common.ext_pre_brackets(Lexer.next_token()));
				continue;
			case IF:
				token_ID = ext_post_if(ext_statement(ext_common.ext_pre_brackets(Lexer.next_token())));
				continue;
			case DO:
				token_ID = accept(SEMICOLON, ext_common.ext_pre_brackets(accept(WHILE, Lexer.next_token())));
				continue;
			case FOR:
				token_ID = ext_statement(ext_for_type(accept(LPAREN, Lexer.next_token())));
				continue;
			case TRY:
				token_ID = ext_try.ext_prologue(Lexer.next_token());
				continue;
			case RCURLYBRACE:
				return Lexer.next_token();
			default:
				token_ID = ext_statement(token_ID);
				continue;
			case INVALID:
				return parse_error(ext_do_value_name, token_ID);
			}
		}
	}
	protected int ext_switch(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_switch_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case CASE:
				token_ID = accept(COLON, ext_switch_case(Lexer.next_token()));
				continue;
			case DEFAULT:
				token_ID = accept(COLON, Lexer.next_token());
				continue;
			case RCURLYBRACE:
				return Lexer.next_token();
			default:
				token_ID = ext_statement(token_ID);
				continue;
			case INVALID:
				return parse_error(ext_switch_name, token_ID);
			}
		}
	}
	protected int ext_switch_case(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_switch_case_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LPAREN:
		case IDENTIFIER:
		case PLUS:
		case MINUS:
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case THIS:
		case CHARACTER_LITERAL:
			return ext_common.ext_value(token_ID);
		case INVALID:
		default:
			return parse_error(ext_switch_case_name, token_ID);
		}
	}
	protected int ext_post_if(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_if_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case ELSE:
			return ext_statement(Lexer.next_token());
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_post_if_name, token_ID);
		}
	}
}
