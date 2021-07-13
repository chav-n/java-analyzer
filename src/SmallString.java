import	java.io.Writer;
import	java.io.IOException;

public		final	class	SmallString
{
	private		int			offset;
	private		int			length;
	private		char[]		source;
	public		SmallString ( char[] a, int b, int c )
	{
		source		= a;
		offset		= b;
		length		= c;
	}
	public		int		length ( )
	{
		return	length;
	}
	public		SmallString  substring ( int newOffset, int newLength )
	{
		newOffset += offset;
		return	new SmallString ( source, newOffset, newLength );
	}
	public		SmallString  substring ( int newOffset )
	{
		return	new SmallString (
		  source, offset + newOffset, length - newOffset );
	}
	public		void	write ( Writer out )
		throws	IOException
	{
		out . write ( source, offset, length - offset );
	}
}
