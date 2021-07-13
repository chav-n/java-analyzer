package	generator;

public		class	SyntaxException
	extends	Exception
{
	private		String				details;
	public	SyntaxException ( String file_path, int ln, int li )
	{
		details  = String . format ( "Syntax error in %s(%d:%d).\n",
		  file_path, ln, li );
	}
	public	SyntaxException ( String file_path, int ln, int li,
	  String token )
	{
		this ( file_path, ln, li );
		details += String . format ( "Not expecting '%s'.\n", token );
	}
	public		String	getMessage ( )
	{
		return	details;
	}
}
