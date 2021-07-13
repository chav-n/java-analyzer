public		class	SyntaxException
	extends	Exception
{
	private		String				message;
	public		SyntaxException	( String m )
	{
		message				= m;
	}
	public		SyntaxException ( String message, int line_number,
	  String section_name )
	{
		this ( String . format (
		  "Wrong syntax in the source code.\n\n"+
		  "Error details:\n"+
		  "%s\n"+
		  "Line: %d\n"+
		  "Section: %s\n",
		  message, line_number, section_name ) );
	}
	public		String	getMessage ( )
	{
		return	message;
	}
}
