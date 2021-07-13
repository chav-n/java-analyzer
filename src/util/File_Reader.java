package	util;

import	java.io.FileReader;
import	java.io.BufferedReader;
import	java.io.IOException;
import	java.io.FileNotFoundException;

public		class	File_Reader
{
	private		BufferedReader		reader;
	public		String				line;
	public		int					line_count;
	public		void	load_file ( String file_path )
		throws	FileNotFoundException
	{
		reader      = new BufferedReader ( new FileReader (
		  file_path ) );
		line_count  =  0;
	}
	public		String	read_line ( )
		throws	IOException
	{
		line  = reader . readLine ( );
		line_count++;
		return	line;
	}
	public		void	close ( )
		throws	IOException
	{
		reader . close ( );
	}
}

