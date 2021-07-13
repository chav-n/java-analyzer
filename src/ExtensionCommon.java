public class ExtensionCommon
	extends Parser
{
	protected static String ext_value_name = "ExtensionCommon: Value";
	protected static String ext_operand_name = "ExtensionCommon: Operand";
	protected static String ext_method_check_name = "ExtensionCommon: Method Check";
	protected static String ext_post_method_check_name = "ExtensionCommon: Post Method Check";
	protected static String ext_inc_dec_name = "ExtensionCommon: Inc Dec";
	protected static String ext_new_name = "ExtensionCommon: New";
	protected static String ext_post_new_name = "ExtensionCommon: Post New";
	protected static String ext_new_array_name = "ExtensionCommon: New Array";
	protected static String ext_new_object_name = "ExtensionCommon: New Object";
	protected static String ext_instanceof_name = "ExtensionCommon: Instanceof";
	protected static String ext_post_instanceof_name = "ExtensionCommon: Post Instanceof";
	protected static String ext_parameters_name = "ExtensionCommon: Parameters";
	protected static String ext_primitive_name = "ExtensionCommon: Primitive";
	protected static String ext_post_primitive_name = "ExtensionCommon: Post Primitive";
	protected static String ext_class_operand_name = "ExtensionCommon: Class Operand";
	protected static String ext_pre_brackets_name = "ExtensionCommon: Pre Brackets";
	protected static String ext_pre_square_brackets_name = "ExtensionCommon: Pre Square Brackets";
	protected static String ext_primitive_declaration_name = "ExtensionCommon: Primitive Declaration";
	protected int ext_value(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_value_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case IDENTIFIER:
			case THIS:
			case SUPER:
				return ext_method_check(Lexer.next_token());
			case INTEGER_LITERAL:
			case LONG_LITERAL:
			case FLOATING_POINT_LITERAL:
			case DOUBLE_LITERAL:
			case TRUE:
			case FALSE:
			case NULL:
			case CHARACTER_LITERAL:
				return ext_operand(Lexer.next_token());
			case NEW:
				return ext_method_check(ext_new(Lexer.next_token()));
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
			case VOID:
				return ext_primitive(Lexer.next_token());
			case STRING_LITERAL:
				return ext_method_check(Lexer.next_token());
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, ext_value(Lexer.next_token()));
				continue;
			case LPAREN:
				return ext_method_check(ext_cast.ext_check_start(Lexer.next_token()));
			case LCURLYBRACE:
				token_ID = ext_value_end.ext_braces(ext_value(Lexer.next_token()));
				continue;
			case PLUS_PLUS:
			case MINUS_MINUS:
				return ext_inc_dec(Lexer.next_token());
			case NOT:
			case TILDE:
			case PLUS:
			case MINUS:
			case FINAL:
				token_ID = Lexer.next_token();
				continue;
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_value_name, token_ID);
			}
		}
	}
	protected int ext_operand(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_operand_name+": "+Lexer.get_token_name(token_ID));
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
		case LESS:
		case LESS_EQUAL:
		case GREATER:
		case GREATER_EQUAL:
		case NOT_EQUAL:
		case LEFT_SHIFT:
		case RIGHT_SHIFT:
		case UNSIGNED_RIGHT_SHIFT:
			return ext_value(Lexer.next_token());
		case QUESTIONMARK:
			return ext_value(ext_value_end.ext_tertiary_assignment(ext_value(Lexer.next_token())));
		case INSTANCEOF:
			return ext_instanceof(Lexer.next_token());
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_operand_name, token_ID);
		}
	}
	protected int ext_method_check(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_method_check_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case INSTANCEOF:
				return ext_instanceof(Lexer.next_token());
			case DOT:
				return ext_dot.ext_check_method(Lexer.next_token());
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, ext_value(Lexer.next_token()));
				continue;
			case LPAREN:
				return ext_post_method_check(ext_value_end.ext_arguments(ext_value(Lexer.next_token())));
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
				return ext_value(Lexer.next_token());
			case PLUS_PLUS:
			case MINUS_MINUS:
				return ext_operand(Lexer.next_token());
			default:
				return ext_operand(token_ID);
			case INVALID:
				return parse_error(ext_method_check_name, token_ID);
			}
		}
	}
	protected int ext_post_method_check(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_post_method_check_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case INSTANCEOF:
				return ext_instanceof(Lexer.next_token());
			case DOT:
				return ext_dot.ext_check_method(Lexer.next_token());
			case LSQUAREBRACKET:
				return ext_method_check(accept(RSQUAREBRACKET, ext_value(Lexer.next_token())));
			case PLUS_PLUS:
			case MINUS_MINUS:
				token_ID = Lexer.next_token();
				continue;
			default:
				return ext_operand(token_ID);
			case INVALID:
				return parse_error(ext_post_method_check_name, token_ID);
			}
		}
	}
	protected int ext_inc_dec(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_inc_dec_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case THIS:
		case IDENTIFIER:
			return ext_method_check(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_inc_dec_name, token_ID);
		}
	}
	protected int ext_new(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_new_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_new(Lexer.next_token());
		case INT:
		case LONG:
		case FLOAT:
		case DOUBLE:
		case CHAR:
		case SHORT:
		case BYTE:
		case BOOLEAN:
			return ext_new_array(ext_pre_square_brackets(Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_new_name, token_ID);
		}
	}
	protected int ext_post_new(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_post_new_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case DOT:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case LESS:
				token_ID = ext_parameters(Lexer.next_token());
				continue;
			case LSQUAREBRACKET:
				return ext_new_array(accept(RSQUAREBRACKET, ext_value(Lexer.next_token())));
			case LPAREN:
				return ext_new_object(ext_value_end.ext_arguments(ext_value(Lexer.next_token())));
			case INVALID:
			default:
				return parse_error(ext_post_new_name, token_ID);
			}
		}
	}
	protected int ext_new_array(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_new_array_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, ext_value(Lexer.next_token()));
				continue;
			case LCURLYBRACE:
				token_ID = ext_value_end.ext_braces(ext_value(Lexer.next_token()));
				continue;
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_new_array_name, token_ID);
			}
		}
	}
	protected int ext_new_object(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_new_object_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LCURLYBRACE:
			return ext_post_method_check(ext_class.ext_type(Lexer.next_token()));
		case DOT:
			return ext_dot.ext_check_method(Lexer.next_token());
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_new_object_name, token_ID);
		}
	}
	protected int ext_instanceof(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_instanceof_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		/* In a primitive type is used in place of
		   its corresponding class type. */
		case INT:
		case LONG:
		case FLOAT:
		case DOUBLE:
		case CHAR:
		case SHORT:
		case BYTE:
		case BOOLEAN:
		case IDENTIFIER:
			return ext_post_instanceof(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_instanceof_name, token_ID);
		}
	}
	protected int ext_post_instanceof(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_post_instanceof_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LESS:
				token_ID = ext_parameters(Lexer.next_token());
				continue;
			case DOT:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case QUESTIONMARK:
				return ext_value(ext_value_end.ext_tertiary_assignment(ext_value(Lexer.next_token())));
			case AND:
			case OR:
			case XOR:
			case AND_AND:
			case OR_OR:
			case EQUAL_EQUAL:
			case NOT_EQUAL:
				return ext_value(Lexer.next_token());
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_post_instanceof_name, token_ID);
			}
		}
	}
	protected int ext_parameters(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_parameters_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LESS:
				token_ID = ext_parameters(Lexer.next_token());
				continue;
			case GREATER:
				return Lexer.next_token();
			case RIGHT_SHIFT:
				return GREATER;
			case UNSIGNED_RIGHT_SHIFT:
				return RIGHT_SHIFT;
			case COMMA:
			case LSQUAREBRACKET:
			case RSQUAREBRACKET:
			case DOT:
			case QUESTIONMARK:
			case AND:
			case EXTENDS:
			case IMPLEMENTS:
			case SUPER:
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
			case IDENTIFIER:
				token_ID = Lexer.next_token();
				continue;
			case INVALID:
			default:
				return parse_error(ext_parameters_name, token_ID);
			}
		}
	}
	protected int ext_primitive(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_primitive_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case DOT:
				return ext_post_primitive(Lexer.next_token());
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case INVALID:
			default:
				return parse_error(ext_primitive_name, token_ID);
			}
		}
	}
	protected int ext_post_primitive(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_primitive_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case CLASS:
			return ext_class_operand(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_post_primitive_name, token_ID);
		}
	}
	protected int ext_class_operand(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_class_operand_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case INSTANCEOF:
		case EQUAL:
		case NOT_EQUAL:
		case AND:
		case OR:
		case XOR:
		case AND_AND:
		case OR_OR:
			return ext_value(Lexer.next_token());
		case DOT:
			return ext_dot.ext_check_method(Lexer.next_token());
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_class_operand_name, token_ID);
		}
	}
	protected int ext_pre_brackets(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_pre_brackets_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LPAREN:
			return accept(RPAREN, ext_value(Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_pre_brackets_name, token_ID);
		}
	}
	protected int ext_pre_square_brackets(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_pre_square_brackets_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LSQUAREBRACKET:
			return accept(RSQUAREBRACKET, ext_value(Lexer.next_token()));
		case INVALID:
		default:
			return parse_error(ext_pre_square_brackets_name, token_ID);
		}
	}
	protected int ext_primitive_declaration(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_primitive_declaration_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case IDENTIFIER:
				return Lexer.next_token();
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case INVALID:
			default:
				return parse_error(ext_primitive_declaration_name, token_ID);
			}
		}
	}
}
