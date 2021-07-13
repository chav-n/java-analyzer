import	java.io.FileWriter;
import	java.io.BufferedWriter;
import	java.io.IOException;
import	java.io.IOException;

public		class	File_Writer
{
	private		BufferedWriter		writer;
	public		String				line;
	public		int					line_count;
	public		void	load_file ( String file_path )
		throws	IOException
	{
		writer      = new BufferedWriter ( new FileWriter (
		  file_path ) );
		line_count  =  0;
	}
	public		void	write ( String text )
		throws	IOException
	{
		writer . write ( text );
	}
	public		void	writeln ( )
		throws	IOException
	{
		writer . newLine ( );
	}
	public		void	writeln ( String text )
		throws	IOException
	{
		writer . write ( text );
		writer . newLine ( );
	}
	public		void	close ( )
		throws	IOException
	{
		writer . close ( );
	}
}

