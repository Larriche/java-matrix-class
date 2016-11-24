public class MatrixClient
{
	public static void main(String[] args)
	{
		double dataA[][] = {{1 , 1 , 2},
		                     {0 , 3 , 2},
		                     {1 , 3 , 9}
	                        };

	    Matrix a = new Matrix(dataA);

		System.out.print(a.getDeterminant());
		System.out.print(a.getInverse());
	   
	}
}