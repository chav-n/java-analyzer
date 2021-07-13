package	generator;

public		final	class	RuleSymbol
{
	public		String		name;
	public		int			type_ID;
	public		int			relation_ID;
	public		RuleSymbol ( String n, int t_ID, int r_ID )
	{
		name		= n;
		type_ID		= t_ID;
		relation_ID  = r_ID;
	}
}
