import	java.io.PrintStream;

public		abstract	class	Logger
{
	protected	ParseLogger			parse_logger;
	protected	long				file_size;
	protected	long				token_count;
	protected	double				parse_time;
	protected	double				read_time;
	protected	PrintStream			out;
	public		abstract	void	log ( )
		throws	Exception;
	public		abstract	double	get_file_size ( );
	public		abstract	double	get_parse_time ( );
	public		abstract	double	get_read_time ( );
	public		double	get_parse_rate ( )
	{
		return	( file_size / (double) ( 1024 * 1024 ) ) / ( parse_time / 1E9 );
	}
	public		void	print_complexity_info ( )
	{
		System.err . printf ( "Symbol ratio: %.2f\n",
		  (double) file_size / token_count );
	}
	public		void	print ( )
	{
		print_complexity_info ( );
		System.err . printf ( "%.2f, %.3f, %.2f, %.2f\n",
		  get_file_size ( ),
		  get_parse_time ( ),
		  get_parse_rate ( ),
		  get_read_time ( ) );
	}
}
