public	class	SourcePrinter
{
	public	SourcePrinter ( int t )
	{
		tabulation	=  t;
	}
	public		int		tabulation;
	public		void	open_block ( )
	{
		println ( "{" );
		tabulation++;
	}
	public		void	close_block ( )
	{
		tabulation--;
		println ( "}" );
	}
	public		void	println ( String line )
	{
		for	( int i  =  0; i  < tabulation; i++ )
			System.out . print ( '\t' );
		System.out . println ( line );
	}
}
