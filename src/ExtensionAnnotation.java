public class ExtensionAnnotation
	extends Parser
{
	protected static String ext_type_name = "ExtensionAnnotation: Type";
	protected static String ext_post_type_name = "ExtensionAnnotation: Post Type";
	protected static String ext_argument_name = "ExtensionAnnotation: Argument";
	protected static String ext_post_identifier_name = "ExtensionAnnotation: Post Identifier";
	protected static String ext_post_argument_name = "ExtensionAnnotation: Post Argument";
	protected static String ext_dot_identifier_name = "ExtensionAnnotation: Dot Identifier";
	protected static String ext_package_prologue_name = "ExtensionAnnotation: Package Prologue";
	protected static String ext_type_prologue_name = "ExtensionAnnotation: Type Prologue";
	protected static String ext_class_type_name = "ExtensionAnnotation: Class Type";
	protected static String ext_argument_type_name = "ExtensionAnnotation: Argument Type";
	protected static String ext_method_type_name = "ExtensionAnnotation: Method Type";
	protected static String ext_method_switch_name = "ExtensionAnnotation: Method Switch";
	protected static String ext_for_type_name = "ExtensionAnnotation: For Type";
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
			case LPAREN:
				return accept(RPAREN, ext_argument(Lexer.next_token()));
			case AT:
				return ext_type(Lexer.next_token());
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_post_type_name, token_ID);
			}
		}
	}
	protected int ext_argument(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_argument_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_identifier(Lexer.next_token());
		case AT:
			return ext_post_argument(ext_type(Lexer.next_token()));
		case STRING_LITERAL:
		case CHARACTER_LITERAL:
		case INTEGER_LITERAL:
		case LONG_LITERAL:
		case FLOATING_POINT_LITERAL:
		case DOUBLE_LITERAL:
		case TRUE:
		case FALSE:
		case NULL:
			return ext_post_argument(Lexer.next_token());
		case LCURLYBRACE:
			return ext_post_argument(accept(RCURLYBRACE, ext_argument(Lexer.next_token())));
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_argument_name, token_ID);
		}
	}
	protected int ext_post_identifier(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_identifier_name+": "+Lexer.get_token_name(token_ID));
    for (;;)
    {
      switch(token_ID)
      {
      case DOT:
        token_ID = ext_dot_identifier(Lexer.next_token());
        continue;
      default:
        return ext_post_argument(token_ID);
      case INVALID:
        return parse_error(ext_post_identifier_name, token_ID);
      }
    }
	}
	protected int ext_post_argument(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_argument_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case COMMA:
			return ext_argument(Lexer.next_token());
		case EQUAL:
			return ext_argument(Lexer.next_token());
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_post_argument_name, token_ID);
		}
	}
	protected int ext_dot_identifier(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_dot_identifier_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
		case CLASS:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_dot_identifier_name, token_ID);
		}
	}
	protected int ext_package_prologue(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_package_prologue_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case AT:
				token_ID = ext_type(Lexer.next_token());
				continue;
			case PACKAGE:
				return ext_head.ext_package(Lexer.next_token());
			case CLASS:
			case INTERFACE:
				return ext_head.ext_type_prologue(ext_class.ext_definition(Lexer.next_token()));
			case ENUM:
				return ext_head.ext_type_prologue(ext_enum.ext_prologue(Lexer.next_token()));
			case PUBLIC:
			case ABSTRACT:
			case FINAL:
			case STRICTFP:
				return ext_head.ext_type_prologue(ext_head.ext_pre_type(Lexer.next_token()));
			case INVALID:
			default:
				return parse_error(ext_package_prologue_name, token_ID);
			}
		}
	}
	protected int ext_type_prologue(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_type_prologue_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case AT:
				token_ID = ext_type(Lexer.next_token());
				continue;
			case CLASS:
			case INTERFACE:
				return ext_head.ext_type_prologue(ext_class.ext_definition(Lexer.next_token()));
			case ENUM:
				return ext_head.ext_type_prologue(ext_enum.ext_prologue(Lexer.next_token()));
			case PUBLIC:
			case ABSTRACT:
			case FINAL:
			case STRICTFP:
				return ext_head.ext_type_prologue(ext_head.ext_pre_type(Lexer.next_token()));
			case INVALID:
			default:
				return parse_error(ext_type_prologue_name, token_ID);
			}
		}
	}
	protected int ext_class_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_class_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case AT:
				token_ID = ext_type(Lexer.next_token());
				continue;
			case CLASS:
			case INTERFACE:
				return ext_class.ext_definition(Lexer.next_token());
			case ENUM:
				return ext_enum.ext_prologue(Lexer.next_token());
			case VOID:
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
				return ext_class.ext_attribute_primitive(Lexer.next_token());
			case IDENTIFIER:
				return ext_class.ext_attribute_object(Lexer.next_token());
			case STATIC:
				return ext_class.ext_type_static(Lexer.next_token());
			case PUBLIC:
			case PRIVATE:
			case PROTECTED:
			case FINAL:
			case ABSTRACT:
			case NATIVE:
			case TRANSIENT:
			case VOLATILE:
			case SYNCHRONIZED:
			case STRICTFP:
				return ext_class.ext_type_classifier(Lexer.next_token());
			case LESS:
				return ext_class.ext_pre_type(ext_common.ext_parameters(Lexer.next_token()));
			case INVALID:
			default:
				return parse_error(ext_class_type_name, token_ID);
			}
		}
	}
	protected int ext_argument_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_argument_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case AT:
				token_ID = ext_type(Lexer.next_token());
				continue;
			case FINAL:
				return ext_class.ext_argument_type(Lexer.next_token());
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
			case IDENTIFIER:
				return ext_class.ext_argument_post_type(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_argument_type_name, token_ID);
			}
		}
	}
	protected int ext_method_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_method_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case AT:
				token_ID = ext_type(Lexer.next_token());
				continue;
			case CLASS:
			case INTERFACE:
				return ext_method.ext_statement(ext_class.ext_definition(Lexer.next_token()));
			case ABSTRACT:
			case FINAL:
				return ext_method.ext_statement(Lexer.next_token());
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
			case IDENTIFIER:
				return ext_method.ext_post_type(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_method_type_name, token_ID);
			}
		}
	}
	protected int ext_method_switch(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_method_switch_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case AT:
				token_ID = ext_type(Lexer.next_token());
				continue;
			case FINAL:
				return ext_method.ext_switch(Lexer.next_token());
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
			case IDENTIFIER:
				return ext_method.ext_switch(ext_method.ext_post_type(Lexer.next_token()));
			case INVALID:
			default:
				return parse_error(ext_method_switch_name, token_ID);
			}
		}
	}
	protected int ext_for_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_for_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case AT:
				token_ID = ext_type(Lexer.next_token());
				continue;
			case FINAL:
				return ext_method.ext_for_type(Lexer.next_token());
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
				return ext_method.ext_for_variable(ext_common.ext_primitive_declaration(Lexer.next_token()));
			case IDENTIFIER:
				return ext_method.ext_for_post_type(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_for_type_name, token_ID);
			}
		}
	}
}
