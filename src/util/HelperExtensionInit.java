package	util;

public		class	HelperExtensionInit
{
	private		static	final	int		ASSIGNMENT_BASE_TAB  = 20;
	private		static	final	int		ASSIGNMENT_MIN_TAB   =  2;
	private		static	String	find_tabulation ( String class_name )
	{
		String	tabulation  = "";
		if	( class_name . length ( ) <= ASSIGNMENT_BASE_TAB -
			  ASSIGNMENT_MIN_TAB )
		{
			while	( class_name . length ( ) <= ASSIGNMENT_BASE_TAB )
			{
				tabulation += "\t";
			}
			return	tabulation;
		}
		else
		{
			return	"  ";
		}
	}
	private		static	String	find_variable_name ( String class_name )
	{
		String	var_name;
		char	current_char;
		var_name  = String . valueOf (
		  class_name . charAt (  0 ) + 32 );
		for	( int i  =  0; i  < var_name . length ( ); i++ )
		{
			current_char  = class_name . charAt ( i );
			if	( current_char  < 97 )
			{
				var_name += "_"+(char) ( current_char - 32 );
			}
			else
			{
				var_name += String . valueOf ( current_char );
			}
		}
		return	var_name;
	}
	/* INCOMPLETE */
	private		static	String	find_readable_name ( String class_name )
	{
		String	readable_name;
		int		start_index;
		int		end_index;
		readable_name  = "";
		if	( class_name . indexOf ( "Pre"  ) !=  -1 ||
			  class_name . indexOf ( "Post" ) !=  -1    )
		{
			start_index  =  0;
			for	( end_index  = start_index + 1;
				  class_name . charAt ( end_index ) >= 97;
				  end_index++ );
		}
		return	readable_name;
	}
}
