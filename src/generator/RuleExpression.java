package	generator;

import	java.util.Vector;
import	java.util.Iterator;

public		final	class	RuleExpression
{
	public		Vector <RuleSymbol>  symbols;
	public		Vector <String>  comments;
	public		String			target;
	public		RuleExpression ( String t, Vector <String> c )
	{
		symbols		= new Vector <RuleSymbol> ( );
		target		= t;
		comments	= c;
	}
	public		void	add_symbol ( RuleSymbol symbol )
	{
		symbols . add ( symbol );
	}
	public		void	add_comment ( String comment )
	{
		comments . add ( comment );
	}
}
