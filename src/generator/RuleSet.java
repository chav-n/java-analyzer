package	generator;

import	java.util.Vector;

public		final	class	RuleSet
{
	public		String		name;
	public		Vector <Rule>  rules;
	public		RuleSet ( String n )
	{
		name		= n;
		rules		= new Vector <Rule> ( );
	}
	public		void	add_rule ( Rule rule )
	{
		rules . add ( rule );
	}
}
