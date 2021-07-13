public class ExtensionClass
	extends Parser
{
	protected static String ext_definition_name = "ExtensionClass: Definition";
	protected static String ext_post_definition_name = "ExtensionClass: Post Definition";
	protected static String ext_post_inheritance_name = "ExtensionClass: Post Inheritance";
	protected static String ext_post_implements_name = "ExtensionClass: Post Implements";
	protected static String ext_pre_type_name = "ExtensionClass: Pre Type";
	protected static String ext_type_name = "ExtensionClass: Type";
	protected static String ext_type_classifier_name = "ExtensionClass: Type Classifier";
	protected static String ext_type_static_name = "ExtensionClass: Type Static";
	protected static String ext_attribute_object_name = "ExtensionClass: Attribute Object";
	protected static String ext_attribute_primitive_name = "ExtensionClass: Attribute Primitive";
	protected static String ext_identifier_name = "ExtensionClass: Identifier";
	protected static String ext_post_variable_name = "ExtensionClass: Post Variable";
	protected static String ext_post_assignment_name = "ExtensionClass: Post Assignment";
	protected static String ext_argument_type_name = "ExtensionClass: Argument Type";
	protected static String ext_argument_type_final_name = "ExtensionClass: Argument Type Final";
	protected static String ext_argument_post_type_name = "ExtensionClass: Argument Post Type";
	protected static String ext_argument_pre_variable_name = "ExtensionClass: Argument Pre Variable";
	protected static String ext_argument_variable_name = "ExtensionClass: Argument Variable";
	protected int ext_definition(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_definition_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_definition(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_definition_name, token_ID);
		}
	}
	protected int ext_post_definition(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_post_definition_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case EXTENDS:
				return ext_type_list.ext_class_extends(ext_type_list.ext_type(Lexer.next_token()));
			case IMPLEMENTS:
				return ext_type_list.ext_class_implements(ext_type_list.ext_type(Lexer.next_token()));
			case LESS:
				token_ID = ext_common.ext_parameters(Lexer.next_token());
				continue;
			case LCURLYBRACE:
				return ext_type(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_post_definition_name, token_ID);
			}
		}
	}
	protected int ext_post_inheritance(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_inheritance_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IMPLEMENTS:
			return ext_type_list.ext_class_implements(ext_type_list.ext_type(Lexer.next_token()));
		case LCURLYBRACE:
			return ext_type(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_post_inheritance_name, token_ID);
		}
	}
	protected int ext_post_implements(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_implements_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case LCURLYBRACE:
			return ext_type(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_post_implements_name, token_ID);
		}
	}
	protected int ext_pre_type(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_pre_type_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case VOID:
		case INT:
		case LONG:
		case FLOAT:
		case DOUBLE:
		case CHAR:
		case SHORT:
		case BYTE:
		case BOOLEAN:
			return ext_attribute_primitive(Lexer.next_token());
		case IDENTIFIER:
			return ext_attribute_object(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_pre_type_name, token_ID);
		}
	}
	protected int ext_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case CLASS:
			case INTERFACE:
				token_ID = ext_definition(Lexer.next_token());
				continue;
			case ENUM:
				token_ID = ext_enum.ext_prologue(Lexer.next_token());
				continue;
			case STATIC:
				token_ID = ext_type_static(Lexer.next_token());
				continue;
			case VOID:
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case CHAR:
			case SHORT:
			case BYTE:
			case BOOLEAN:
				token_ID = ext_attribute_primitive(Lexer.next_token());
				continue;
			case IDENTIFIER:
				token_ID = ext_attribute_object(Lexer.next_token());
				continue;
			/* For static blocks without the "static"
			   keyword. */
			case LCURLYBRACE:
				token_ID = ext_method.ext_type(Lexer.next_token());
				continue;
			case AT:
				token_ID = ext_annotation.ext_class_type(ext_annotation.ext_type(Lexer.next_token()));
				continue;
			case LESS:
				token_ID = ext_pre_type(ext_common.ext_parameters(Lexer.next_token()));
				continue;
			case RCURLYBRACE:
				return Lexer.next_token();
			/* SEMICOLON: For enums, inner classes, and methods that end
			   with a semicolon. */
			case SEMICOLON:
				token_ID = Lexer.next_token();
				continue;
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
				token_ID = ext_type_classifier(Lexer.next_token());
				continue;
			case INVALID:
			default:
				return parse_error(ext_type_name, token_ID);
			}
		}
	}
	protected int ext_type_classifier(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_type_classifier_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case CLASS:
			case INTERFACE:
				return ext_definition(Lexer.next_token());
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
				return ext_attribute_primitive(Lexer.next_token());
			case IDENTIFIER:
				return ext_attribute_object(Lexer.next_token());
			case LESS:
				return ext_pre_type(ext_common.ext_parameters(Lexer.next_token()));
			case STATIC:
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
				token_ID = Lexer.next_token();
				continue;
			case INVALID:
			default:
				return parse_error(ext_type_classifier_name, token_ID);
			}
		}
	}
	protected int ext_type_static(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_type_static_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case CLASS:
		case INTERFACE:
			return ext_definition(Lexer.next_token());
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
			return ext_attribute_primitive(Lexer.next_token());
		case IDENTIFIER:
			return ext_attribute_object(Lexer.next_token());
		case LCURLYBRACE:
			return ext_method.ext_type(Lexer.next_token());
		/* Case: [... static <T extends ...] */
		case LESS:
			return ext_pre_type(ext_common.ext_parameters(Lexer.next_token()));
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
			return ext_type_classifier(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_type_static_name, token_ID);
		}
	}
	protected int ext_attribute_object(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_attribute_object_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case IDENTIFIER:
				return ext_identifier(Lexer.next_token());
			case DOT:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case LESS:
				token_ID = ext_common.ext_parameters(Lexer.next_token());
				continue;
			case LPAREN:
				return ext_method.ext_prologue(ext_argument_type(Lexer.next_token()));
			case INVALID:
			default:
				return parse_error(ext_attribute_object_name, token_ID);
			}
		}
	}
	protected int ext_attribute_primitive(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_attribute_primitive_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case IDENTIFIER:
				return ext_identifier(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_attribute_primitive_name, token_ID);
			}
		}
	}
	protected int ext_identifier(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_identifier_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case EQUAL:
				return ext_post_assignment(ext_common.ext_value(Lexer.next_token()));
			case COMMA:
				return ext_post_variable(accept(IDENTIFIER, Lexer.next_token()));
			case SEMICOLON:
				return Lexer.next_token();
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case LPAREN:
				return ext_method.ext_prologue(ext_argument_type(Lexer.next_token()));
			case INVALID:
			default:
				return parse_error(ext_identifier_name, token_ID);
			}
		}
	}
	protected int ext_post_variable(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_post_variable_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case EQUAL:
				return ext_post_assignment(ext_common.ext_value(Lexer.next_token()));
			case COMMA:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case SEMICOLON:
				return Lexer.next_token();
			case INVALID:
			default:
				return parse_error(ext_post_variable_name, token_ID);
			}
		}
	}
	protected int ext_post_assignment(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_assignment_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case COMMA:
			return ext_post_variable(accept(IDENTIFIER, Lexer.next_token()));
		case SEMICOLON:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_post_assignment_name, token_ID);
		}
	}
	protected int ext_argument_type(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_argument_type_name+": "+Lexer.get_token_name(token_ID));
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
		case IDENTIFIER:
			return ext_argument_post_type(Lexer.next_token());
		/* For no argument methods. */
		case RPAREN:
			return Lexer.next_token();
		case AT:
			return ext_annotation.ext_argument_type(ext_annotation.ext_type(Lexer.next_token()));
		case FINAL:
			return ext_argument_type_final(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_argument_type_name, token_ID);
		}
	}
	protected int ext_argument_type_final(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_argument_type_final_name+": "+Lexer.get_token_name(token_ID));
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
		case IDENTIFIER:
			return ext_argument_post_type(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_argument_type_final_name, token_ID);
		}
	}
	protected int ext_argument_post_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_argument_post_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case IDENTIFIER:
				return ext_argument_variable(Lexer.next_token());
			case DOT:
				token_ID = accept(IDENTIFIER, Lexer.next_token());
				continue;
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case LESS:
				token_ID = ext_common.ext_parameters(Lexer.next_token());
				continue;
			case ELLIPSIS:
				return ext_argument_pre_variable(Lexer.next_token());
			case INVALID:
			default:
				return parse_error(ext_argument_post_type_name, token_ID);
			}
		}
	}
	protected int ext_argument_pre_variable(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_argument_pre_variable_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_argument_variable(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_argument_pre_variable_name, token_ID);
		}
	}
	protected int ext_argument_variable(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_argument_variable_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case COMMA:
				return ext_argument_type(Lexer.next_token());
			case LSQUAREBRACKET:
				token_ID = accept(RSQUAREBRACKET, Lexer.next_token());
				continue;
			case RPAREN:
				return Lexer.next_token();
			case INVALID:
			default:
				return parse_error(ext_argument_variable_name, token_ID);
			}
		}
	}
}
