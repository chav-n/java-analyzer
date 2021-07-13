public class ExtensionHead
	extends Parser
{
	protected static String ext_package_prologue_name = "ExtensionHead: Package Prologue";
	protected static String ext_import_prologue_name = "ExtensionHead: Import Prologue";
	protected static String ext_type_prologue_name = "ExtensionHead: Type Prologue";
	protected static String ext_package_name = "ExtensionHead: Package";
	protected static String ext_post_package_name = "ExtensionHead: Post Package";
	protected static String ext_import_name = "ExtensionHead: Import";
	protected static String ext_static_import_name = "ExtensionHead: Static Import";
	protected static String ext_post_import_name = "ExtensionHead: Post Import";
	protected static String ext_import_branch_name = "ExtensionHead: Import Branch";
	protected static String ext_import_end_name = "ExtensionHead: Import End";
	protected static String ext_pre_type_name = "ExtensionHead: Pre Type";
	protected static String ext_pre_interface_name = "ExtensionHead: Pre Interface";
	protected int ext_package_prologue(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_package_prologue_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case PACKAGE:
			return ext_import_prologue(ext_package(Lexer.next_token()));
		case IMPORT:
			return ext_import_prologue(ext_import(Lexer.next_token()));
		case CLASS:
		case INTERFACE:
			return ext_type_prologue(ext_class.ext_definition(Lexer.next_token()));
		case ENUM:
			return ext_type_prologue(ext_enum.ext_prologue(Lexer.next_token()));
		case PUBLIC:
		case ABSTRACT:
		case FINAL:
		case STRICTFP:
			return ext_type_prologue(ext_pre_type(Lexer.next_token()));
		case AT:
			return ext_annotation.ext_package_prologue(ext_annotation.ext_type(Lexer.next_token()));
		default:
			return token_ID;
		case INVALID:
			return parse_error(ext_package_prologue_name, token_ID);
		}
	}
	protected int ext_import_prologue(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_import_prologue_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case IMPORT:
				token_ID = ext_import(Lexer.next_token());
				continue;
			case CLASS:
			case INTERFACE:
				return ext_type_prologue(ext_class.ext_definition(Lexer.next_token()));
			case ENUM:
				return ext_type_prologue(ext_enum.ext_prologue(Lexer.next_token()));
			case PUBLIC:
			case ABSTRACT:
			case FINAL:
			case STRICTFP:
				return ext_type_prologue(ext_pre_type(Lexer.next_token()));
			case AT:
				return ext_annotation.ext_type_prologue(ext_annotation.ext_type(Lexer.next_token()));
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_import_prologue_name, token_ID);
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
			case CLASS:
			case INTERFACE:
				token_ID = ext_class.ext_definition(Lexer.next_token());
				continue;
			case ENUM:
				token_ID = ext_enum.ext_prologue(Lexer.next_token());
				continue;
			case PUBLIC:
			case ABSTRACT:
			case FINAL:
			case STRICTFP:
				token_ID = ext_pre_type(Lexer.next_token());
				continue;
			case AT:
				return ext_annotation.ext_type_prologue(ext_annotation.ext_type(Lexer.next_token()));
			case SEMICOLON:
				token_ID = Lexer.next_token();
				continue;
			default:
				return token_ID;
			case INVALID:
				return parse_error(ext_type_prologue_name, token_ID);
			}
		}
	}
	protected int ext_package(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_package_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_package(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_package_name, token_ID);
		}
	}
	protected int ext_post_package(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_package_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case DOT:
			return ext_package(Lexer.next_token());
		case SEMICOLON:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_post_package_name, token_ID);
		}
	}
	protected int ext_import(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_import_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_import(Lexer.next_token());
		case STATIC:
			return ext_static_import(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_import_name, token_ID);
		}
	}
	protected int ext_static_import(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_static_import_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_import(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_static_import_name, token_ID);
		}
	}
	protected int ext_post_import(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_post_import_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case SEMICOLON:
			return Lexer.next_token();
		case DOT:
			return ext_import_branch(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_post_import_name, token_ID);
		}
	}
	protected int ext_import_branch(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_import_branch_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case IDENTIFIER:
			return ext_post_import(Lexer.next_token());
		case MULTIPLY:
			return ext_import_end(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_import_branch_name, token_ID);
		}
	}
	protected int ext_import_end(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_import_end_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case SEMICOLON:
			return Lexer.next_token();
		case INVALID:
		default:
			return parse_error(ext_import_end_name, token_ID);
		}
	}
	protected int ext_pre_type(int token_ID)
	{
		for(;;)
		{
			if	(debug_out != null) debug_out.println(ext_pre_type_name+": "+Lexer.get_token_name(token_ID));
			switch(token_ID)
			{
			case CLASS:
			case INTERFACE:
				return ext_class.ext_definition(Lexer.next_token());
			case ENUM:
				return ext_enum.ext_prologue(Lexer.next_token());
			case AT:
				return ext_pre_interface(Lexer.next_token());
			case PUBLIC:
			case ABSTRACT:
			case FINAL:
			case STRICTFP:
				token_ID = Lexer.next_token();
				continue;
			case INVALID:
			default:
				return parse_error(ext_pre_type_name, token_ID);
			}
		}
	}
	protected int ext_pre_interface(int token_ID)
	{
		if	(debug_out != null) debug_out.println(ext_pre_interface_name+": "+Lexer.get_token_name(token_ID));
		switch(token_ID)
		{
		case INTERFACE:
			return ext_class.ext_definition(Lexer.next_token());
		case INVALID:
		default:
			return parse_error(ext_pre_interface_name, token_ID);
		}
	}
}
