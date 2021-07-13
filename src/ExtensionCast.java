public class ExtensionCast
	extends Parser
{
	protected static String ext_check_start_name = "ExtensionCast: Check Start";
	protected static String ext_check_identifier_name = "ExtensionCast: Check Identifier";
	protected static String ext_check_primitive_name = "ExtensionCast: Check Primitive";
	protected static String ext_check_generic_name = "ExtensionCast: Check Generic";
	protected static String ext_check_array_name = "ExtensionCast: Check Array";
	protected static String ext_check_generic_array_name = "ExtensionCast: Check Generic Array";
	protected static String ext_check_generic_identifier_name = "ExtensionCast: Check Generic Identifier";
	protected static String ext_check_type_name = "ExtensionCast: Check Type";
	protected static String ext_stop_name = "ExtensionCast: Stop";
	protected static String ext_operand_name = "ExtensionCast: Operand";
	protected static String ext_primitive_name = "ExtensionCast: Primitive";
	protected int ext_check_start(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_start_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_check_identifier(Lexer.next_token());
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case TRUE:
		case FALSE:
		case NULL:
		case CHARACTER_LITERAL:
			return accept(RPAREN, ext_common.ext_operand(Lexer.next_token()));
		case THIS:
		case SUPER:
			return accept(RPAREN, ext_common.ext_method_check(Lexer.next_token()));
		case NEW:
			return accept(RPAREN, ext_common.ext_method_check(ext_common.ext_new(Lexer.next_token())));
		case INT:
		case LONG:
		case FLOAT:
		case DOUBLE:
		case CHAR:
		case SHORT:
		case BYTE:
		case BOOLEAN:
			return ext_check_primitive(Lexer.next_token());
		case PLUS:
		case MINUS:
		case PLUS_PLUS:
		case MINUS_MINUS:
		case NOT:
		case TILDE:
			return accept(RPAREN, ext_common.ext_value(Lexer.next_token()));
		case LPAREN:
			return accept(RPAREN, ext_common.ext_method_check(ext_check_start(Lexer.next_token())));
		case STRING_LITERAL:
			return accept(RPAREN, ext_common.ext_method_check(Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_check_start_name, token_ID);
		}
	}
	protected int ext_check_identifier(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_identifier_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case INSTANCEOF:
			return accept(RPAREN, ext_common.ext_instanceof(Lexer.next_token()));
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
		case PLUS:
		case MINUS:
		case MULTIPLY:
		case DIVIDE:
		case REMAINDER:
		case AND:
		case OR:
		case XOR:
		case AND_AND:
		case OR_OR:
		case EQUAL_EQUAL:
		case LESS_EQUAL:
		case GREATER:
		case GREATER_EQUAL:
		case NOT_EQUAL:
		case LEFT_SHIFT:
		case RIGHT_SHIFT:
		case UNSIGNED_RIGHT_SHIFT:
			return accept(RPAREN, ext_common.ext_value(Lexer.next_token()));
		case QUESTIONMARK:
			return accept(RPAREN, ext_common.ext_value(ext_value_end.ext_tertiary_assignment(ext_common.ext_value(Lexer.next_token()))));
		case PLUS_PLUS:
		case MINUS_MINUS:
			return accept(RPAREN, ext_common.ext_operand(Lexer.next_token()));
		case LPAREN:
			return accept(RPAREN, ext_common.ext_post_method_check(ext_value_end.ext_arguments(ext_common.ext_value(Lexer.next_token()))));
		case RPAREN:
			return ext_check_type(Lexer.next_token());
		case LESS:
			return ext_check_generic(Lexer.next_token());
		case DOT:
			return ext_dot.ext_check_cast(Lexer.next_token());
		case LSQUAREBRACKET:
			return ext_check_array(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_check_identifier_name, token_ID);
		}
	}
	protected int ext_check_primitive(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_primitive_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case DOT:
			return accept(RPAREN, ext_common.ext_post_primitive(Lexer.next_token()));
		case LSQUAREBRACKET:
			return ext_stop(accept(RSQUAREBRACKET, Lexer.next_token()));
		case RPAREN:
			return ext_primitive(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_check_primitive_name, token_ID);
		}
	}
	protected int ext_check_generic(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_generic_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_check_generic_identifier(Lexer.next_token());
		/* CHARACTER_LITERAL: For the where a character constant is
		   used in place of its integer value. */
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case CHARACTER_LITERAL:
		case TRUE:
		case FALSE:
		case NULL:
			return accept(RPAREN, ext_common.ext_operand(Lexer.next_token()));
		case THIS:
		case SUPER:
			return accept(RPAREN, ext_common.ext_method_check(Lexer.next_token()));
		case INT:
		case LONG:
		case FLOAT:
		case DOUBLE:
		case CHAR:
		case SHORT:
		case BYTE:
		case BOOLEAN:
			return ext_stop(ext_common.ext_parameters(Lexer.next_token()));
		case PLUS:
		case MINUS:
		case PLUS_PLUS:
		case MINUS_MINUS:
		case NOT:
		case TILDE:
			return accept(RPAREN, ext_common.ext_value(Lexer.next_token()));
		case STRING_LITERAL:
			return accept(RPAREN, ext_common.ext_method_check(Lexer.next_token()));
		case LPAREN:
			return accept(RPAREN, ext_common.ext_method_check(accept(RPAREN, ext_common.ext_value(Lexer.next_token()))));
		case QUESTIONMARK:
			return ext_stop(ext_common.ext_parameters(Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_check_generic_name, token_ID);
		}
	}
	protected int ext_check_array(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_array_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
		case THIS:
		case SUPER:
			return accept(RPAREN, ext_common.ext_method_check(accept(RSQUAREBRACKET, ext_common.ext_method_check(Lexer.next_token()))));
		/* CHARACTER_LITERAL: For the where a character constant is used
		   in place of its integer value. */
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case CHARACTER_LITERAL:
		case TRUE:
		case FALSE:
		case NULL:
			return accept(RPAREN, ext_common.ext_method_check(accept(RSQUAREBRACKET, ext_common.ext_operand(Lexer.next_token()))));
		case NEW:
			return accept(RPAREN, ext_common.ext_method_check(accept(RSQUAREBRACKET, ext_common.ext_method_check(ext_common.ext_new(Lexer.next_token())))));
		case PLUS:
		case MINUS:
		case PLUS_PLUS:
		case MINUS_MINUS:
		case NOT:
		case TILDE:
			return accept(RPAREN, ext_common.ext_method_check(accept(RSQUAREBRACKET, ext_common.ext_value(Lexer.next_token()))));
		case RSQUAREBRACKET:
			return ext_stop(Lexer.next_token());
		case LPAREN:
			return accept(RPAREN, ext_common.ext_operand(accept(RSQUAREBRACKET, ext_common.ext_method_check(ext_check_start(Lexer.next_token())))));
		case INVALID:
		default:
			return parse_error(ext_check_array_name, token_ID);
		}
	}
	protected int ext_check_generic_array(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_generic_array_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return accept(RPAREN, ext_common.ext_method_check(accept(RSQUAREBRACKET, ext_common.ext_method_check(Lexer.next_token()))));
		/* CHARACTER_LITERAL: For the where a character constant is used
		   in place of its integer value. */
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case CHARACTER_LITERAL:
		case TRUE:
		case FALSE:
		case NULL:
			return accept(RPAREN, ext_common.ext_operand(accept(RSQUAREBRACKET, ext_common.ext_value(Lexer.next_token()))));
		case THIS:
		case SUPER:
			return accept(RPAREN, ext_common.ext_method_check(accept(RSQUAREBRACKET, ext_common.ext_method_check(Lexer.next_token()))));
		case NEW:
			return accept(RPAREN, ext_common.ext_method_check(accept(RSQUAREBRACKET, ext_common.ext_method_check(ext_common.ext_new(Lexer.next_token())))));
		case PLUS:
		case MINUS:
		case PLUS_PLUS:
		case MINUS_MINUS:
		case NOT:
		case TILDE:
			return accept(RPAREN, ext_common.ext_value(accept(RSQUAREBRACKET, ext_common.ext_value(Lexer.next_token()))));
		case RSQUAREBRACKET:
			return ext_stop(ext_common.ext_parameters(Lexer.next_token()));
		case LPAREN:
			return accept(RPAREN, ext_common.ext_operand(accept(RSQUAREBRACKET, ext_common.ext_method_check(ext_check_start(Lexer.next_token())))));
		case INVALID:
		default:
			return parse_error(ext_check_generic_array_name, token_ID);
		}
	}
	protected int ext_check_generic_identifier(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_check_generic_identifier_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
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
			case PLUS:
			case MINUS:
			case MULTIPLY:
			case DIVIDE:
			case REMAINDER:
			case AND:
			case OR:
			case XOR:
			case AND_AND:
			case OR_OR:
			case EQUAL_EQUAL:
			case LESS_EQUAL:
			case GREATER_EQUAL:
			case NOT_EQUAL:
			case LEFT_SHIFT:
				return accept(RPAREN, ext_common.ext_value(Lexer.next_token()));
			case GREATER:
				return ext_stop(Lexer.next_token());
			case QUESTIONMARK:
				return accept(RPAREN, ext_common.ext_value(ext_value_end.ext_tertiary_assignment(ext_common.ext_value(Lexer.next_token()))));
			case PLUS_PLUS:
			case MINUS_MINUS:
				return accept(RPAREN, ext_common.ext_value(Lexer.next_token()));
			case LPAREN:
				return accept(RPAREN, ext_common.ext_post_method_check(ext_value_end.ext_arguments(ext_common.ext_value(Lexer.next_token()))));
			case RPAREN:
				return Lexer.next_token();
			case COMMA:
				return ext_stop(ext_common.ext_parameters(Lexer.next_token()));
			case LESS:
				return ext_stop(ext_common.ext_parameters(ext_common.ext_parameters(Lexer.next_token())));
			case DOT:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case LSQUAREBRACKET:
				return ext_check_generic_array(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_check_generic_identifier_name, token_ID);
			}
		}
	}
	protected int ext_check_type(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_check_type_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		/* CHARACTER_LITERAL: For the where a character constant is used
		   in place of its integer value. */
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case TRUE:
		case FALSE:
		case NULL:
		case IDENTIFIER:
		case THIS:
		case SUPER:
			return Lexer.next_token();
		case NOT:
		case TILDE:
			return ext_primitive(Lexer.next_token());
		case LPAREN:
			return ext_check_start(Lexer.next_token());
		case NEW:
			return ext_common.ext_new(Lexer.next_token());
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_check_type_name, token_ID);
		}
	}
	protected int ext_stop(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_stop_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case RPAREN:
				return ext_operand(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_stop_name, token_ID);
			}
		}
	}
	protected int ext_operand(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_operand_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		/* CHARACTER_LITERAL: For the where a character constant is used
		   in place of its integer value. */
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case IDENTIFIER:
		case TRUE:
		case FALSE:
		case NULL:
		case THIS:
		case SUPER:
			return Lexer.next_token();
		case PLUS:
		case MINUS:
		case NOT:
		case TILDE:
			return ext_primitive(Lexer.next_token());
		case NEW:
			return ext_common.ext_new(Lexer.next_token());
		case LPAREN:
			return ext_check_start(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_operand_name, token_ID);
		}
	}
	protected int ext_primitive(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_primitive_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			/* CHARACTER_LITERAL: For the where a character constant is used
			   in place of its integer value. */
			case INTEGER_LITERAL:
			case LONG_LITERAL:
			case FLOATING_POINT_LITERAL:
			case DOUBLE_LITERAL:
			case CHARACTER_LITERAL:
			case IDENTIFIER:
			case TRUE:
			case FALSE:
			case NULL:
			case THIS:
			case SUPER:
				return Lexer.next_token();
			case PLUS:
			case MINUS:
			case NOT:
			case TILDE:
				token_ID = Lexer.next_token();
				continue;
			case NEW:
				return ext_common.ext_new(Lexer.next_token());
			case LPAREN:
				return ext_check_start(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_primitive_name, token_ID);
			}
		}
	}
}
