package	util;

public		class	StartExtension
{
	public		static	void	main ( String[] args )
	{
		if	( args.length ==  0 )
		{
			System.err . println ( "No arguments given." );
			System . exit (  1 );
		}
		String	class_name  = args[ 0];
		System.out . printf ( "\tprotected\tclass\tExtension%s\n", class_name );
		System.out . printf ( "\t\textends\tExtension\n" );
		System.out . printf ( "\t{\n" );
		System.out . printf ( "\t\tprotected\tExtension%s ( String n )\n",
		  class_name );
		System.out . printf ( "\t\t{\n" );
		System.out . printf ( "\t\t\tsuper ( n );\n" );
		System.out . printf ( "\t\t}\n" );
		System.out . printf ( "\t\tprotected\tvoid\ttoken_branch ( )\n" );
		System.out . printf ( "\t\t{\n" );
		System.out . printf ( "\t\t}\n" );
		System.out . printf (
		  "\t\tprotected\tvoid\tdelimiter_branch ( )\n" );
		System.out . printf ( "\t\t{\n" );
		System.out . printf ( "\t\t}\n" );
		System.out . printf ( "\t}\n" );
	}
}
