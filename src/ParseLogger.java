import	java.io.File;
import	java.io.PrintStream;
import	java.io.IOException;

public		abstract	class	ParseLogger
{
	protected	long				file_size;
	protected	long				token_count;
	protected	double				parse_time;
	protected	double				read_time;
	public		abstract	void	parse ( String file_path )
		throws	SyntaxException,
				IOException;
	public		abstract	void	debug ( String file_path,
	  PrintStream out )
		throws	SyntaxException,
				IOException;
}
