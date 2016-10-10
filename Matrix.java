import java.lang.Math;

public class Matrix
{
    /**
     * This class represents a matrix and has implementations of common methods
     * associated with matrices
     *
     * @author Richman Larry Clifford
     */


    /**
     * The number of rows in a matrix
     *
     * @var int
     */
    int rows;

    /**
     * The number of columns in a matrix
     *
     * @var int
     */
    int columns;

    /**
     * The matrix's elements
     *
     * @var array
     */
    private double[][] data;

    /**
     * Create a new matrix by passing a multidimensional array of data
     *
     * @param array data the elements of the matrix
     * @return Matrix
     */
    public Matrix(double[][] data)
    {
       this.rows = data.length;
       this.columns = data[0].length;
       this.data = new double[rows][columns];

       for(int i = 0;i < rows;i++){
          for(int j = 0;j < columns;j++){
              this.data[i][j] = data[i][j];
          }
       }
    }
    
    /**
     * Create a new empty matrix with given rows and columns
     *
     * @param int rows the number of rows in the matrix
     * @param int columns the number of columns in the matrix
     * @return Matrix
     */
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = new double[rows][columns];
    }

    /**
     * Copy constructor for our matrix
     */
    private Matrix(Matrix A){ this(A.data);}
    
    /**
     * Display the matrix
     *
     * @return string output the string representation of the matrix
     */
    public String toString()
    {
        String output = "";
        for(int i = 0;i < this.rows;i++){
            output += "[ ";

            for(int j = 0;j < this.columns;j++){
                output += this.data[i][j] + "  ";
            }

            output += "]\n";
        }

        return output;
    }
    
    /**
     * Determine whether the matrix is a square matrix
     *
     * @param void
     * @return boolean value determining whether the matrix is square or not
     */
    public boolean isSquareMatrix()
    {
        if(this.rows == this.columns){
            // check whether the matrix has the same number of rows as columns
            return true;
        }

        return false;
    }

    /**
     * Determine whether this matrix has same number of rows and columns as a given matrix
     *
     * @param Matrix B the matrix to compare to
     * @return boolean the truth value of the test
     */
    public boolean isEqualTo(Matrix B)
    {
        if(this.rows != B.rows || this.columns != B.columns){
            return false;
        }

        return true;
    }

    /**
     * Determine if a given matrix has exactly the same elements as this matrix
     *
     * @param Matrix B the matrix to compare against
     * @return boolean the truth value of the test
     */
    public boolean isEquivalentTo(Matrix B)
    {
        if(!this.isEqualTo(B)){
            return false;
        }

        for(int i = 0;i < this.rows;i++){
            for(int j = 0;j < this.columns){
                if(this.data[i][j] != B.data[i][j]){
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
     * Determine whether a given row contains zero elements throughout
     *
     * @param array the row
     * @return boolean the truth statement of the test
     */
    public static boolean isZeroRow(double[] row)
    {
        for(int i = 0;i < row.length;i++){
            if(row[i] != 0){
                return false;
            }
        }

        return true;
    }

    /**
     * Get the first non zero element in a given row
     * 
     * @param array the row
     * @return double the first non-zero element
     */ 
    public static double getFirstNonZeroOfRow(double[] row)
    {
        for(int i = 0;i < row.length;i++){
            if(row[i] != 0){
                return row[i];
            }
        }
        
        // this method is meant to be called with a row that has been tested 
        // to be a non zero row so the method won't get to run this
        return 0;
    }
    
    /**
     * Get the position of the first non zero element of a given row
     *
     * @param array the row
     * @return int the position of the first non-zero element of the row
     */
    public static int getPosOfFirstNonZeroOfRow(double[] row)
    {
        for(int i = 0;i < row.length;i++){
            if(row[i] != 0){
                return i;
            }
        }
        
        // this method is meant to be called with a row that has been tested 
        // to be a non zero row so the method won't get to run this
        return -1;
    }

    /**
     * Determine the feasibility of a multplication between this matrix and another matrix B
     *
     * @param Matrix B the matrix to compare against
     * @return boolean the truth statement of this test
     */
    public boolean canMultiply(Matrix B)
    {
        if(this.columns == B.rows){
            return true;
        }

        return false;
    }

    /**
     * Determine whether the matrix is in row-echelon form
     *
     * @param void
     * @return boolean the truth value of the test
     */
    public boolean isInRowEchelonForm()
    {
        for(int i = 0;i < this.rows;i++){
            if(Matrix.isZeroRow(this.data[i])){
                for(int j = i + 1;j < this.rows;j++){
                    if(!Matrix.isZeroRow(this.data[j])){
                        return false;
                    }
                }
            }
            else{
                if(Matrix.getFirstNonZeroOfRow(this.data[i]) != 1){
                    return false;
                }
                else{
                    // check whether the position of the first non zero element
                    // is farther to the right than that of the previous row

                    // pos of the first 1 in the row
                    int pos = Matrix.getPosOfFirstNonZeroOfRow(this.data[i]);

                    if(i > 0){
                        if( pos < Matrix.getPosOfFirstNonZeroOfRow(this.data[i - 1])){
                          return false;
                        }
                    }

                    for(int j = i + 1;j < this.rows;j++){
                        // check that all elements under the first 1 in this row
                        // are 0s
                        if(this.data[j][pos] != 0){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Determine whether the matrix is in reduced row-echelon form
     *
     * @param void
     * @return boolean the truth value of the test
     */
    public boolean isInReducedRowEchelonForm()
    {
        if(!this.isInRowEchelonForm()){
            // for the matrix to be in reduced row echelon-form , it should be in
            // row-echelon form as well
            return false;
        }

        for(int i = 0;i < this.rows;i++){
            // the extra requirement that this matrix has to pass to be
            // regarded as being in reduced row-echelon form is to
            // have both positions above and below the position of the first
            // 1 in each row to have 0 as an element
            int pos = Matrix.getPosOfFirstNonZeroOfRow(this.data[i]);

            for(int j = 0;j < this.rows;j++){
                if(j != i){
                    if(this.data[j][pos] != 0){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Get the transpose of the matrix
     *
     * @param void
     * @return Matrix  A the transpose of this matrix
     */
    public Matrix transpose()
    {
        Matrix A = new Matrix(columns , rows);

        for(int i = 0;i < rows;i++){
            for(int j = 0;j < columns;j++){
                A.data[j][i] = this.data[i][j];
            }
        }

        return A;
    }

    /**
     * Add this matrix to a given matrix and return the sum matrix
     *
     * @param Matrix B the matrix to add to this matrix
     * @return Matrix sum the sum matrix
     */
    public Matrix add(Matrix B)
    {
        Matrix A = this;
        
        if(!A.isEqualTo(B)){
            throw new RuntimeException("Matrix dimensions are not equal");
        }

        Matrix sum = new Matrix(rows , columns);

        for(int i = 0;i < rows;i++){
            for(int j = 0;j < columns;j++){
                sum.data[i][j] = A.data[i][j] + B.data[i][j];
            }
        }

        return sum;
    }

    /**
     * Subtract a given matrix from this matrix
     *
     * @param Matrix B the matrix to subtract
     * @return Matrix res the resultant matrix
     */
    public Matrix subtract(Matrix B)
    {
        Matrix A = this;

        if(B.rows != A.rows || B.columns != A.columns){
            throw new RuntimeException("Matrix dimensions are not equal");
        }

        Matrix res = new Matrix(rows , columns);

        for(int i = 0;i < A.rows;i++){
            for(int j = 0;j < A.columns;j++){
                res.data[i][j] = A.data[i][j] - B.data[i][j];
            }
        }

        return res;
    }

    /**
     * Multiply this matrix by a given matrix B and return the product as a matrix
     *
     * @param Matrix B the matrix to multiply this matrix by 
     * @return Matrix product the product matrix
     */
    public Matrix multiply(Matrix B)
    {
        Matrix A = this;

        if(!A.canMultiply(B)){
            throw new RuntimeException("The rows of one matrix must be equal to the columns of the other");
        }

        Matrix product  = new Matrix(columns , rows);

        for(int i = 0; i < A.rows;i++){
            for(int j = 0;j < B.columns;j++){
                product.data[i][j] = 0;

                for(int k = 0;k < A.columns;k++){
                    product.data[i][j] += A.data[i][k] * B.data[k][j];
                }
            }
        }

        return product;
    }

    /**
     * Get the resulting matrix from multiplying this matrix by a scalar
     *
     * @param double scalar the scalar
     * @return Matrix res the resulting matrix
     */
    public Matrix scalarMultiply(double scalar)
    {
        System.out.println("Scalar: " + scalar);
        Matrix res = new Matrix(this.rows , this.columns);

        for(int i = 0;i < this.rows;i++){
            for(int j = 0;j < this.columns;j++){
                res.data[i][j] = this.data[i][j] * scalar;
            }
        }

        return res;
    }

    /**
     * Get the determinant of this matrix
     * 
     * @param void
     * @return void
     */
    public double getDeterminant()
    {
        double determinant = 0;

        if(this.rows < 2 || this.columns < 2){
            throw new RuntimeException("You can't find determinant of such a matrix");
        }

        if(!this.isSquareMatrix()){
            throw new RuntimeException("You can only find the determinants of square matrixes");
        }

        if(this.rows == 2){
            determinant = ((this.data[0][0] * this.data[1][1])) - ((this.data[0][1] * this.data[1][0]));
        }
        else{
            for(int k = 0;k < columns;k++){
                 Matrix reduced = this.getSubMatrix(0 , k);
      
                 determinant += Math.pow( -1 , (k + 2)) * this.data[0][k] * reduced.getDeterminant();
            }
        }

        return determinant;
    }
    
    /**
     * Get the matrix cofactors of this matrix
     *
     * @param void
     * @return Matrix cofactor the matrix cofactors of this matrix
     */
    public Matrix getCofactors()
    {
        Matrix cofactors = new Matrix(this.rows , this.columns);     
        
        for(int i = 0;i < this.rows;i++){
            for(int j = 0;j < this.columns;j++){
                Matrix sub = this.getSubMatrix(i , j);
                cofactors.data[i][j] = Math.pow(-1 , ((i + 1) + (j + 1)));  

                if(sub.rows == 1 && sub.columns == 1){
                    cofactors.data[i][j] *= sub.data[0][0];
                }
                else{
                    cofactors.data[i][j] *= sub.getDeterminant();
                }
            }
        }
        
        return cofactors;
    }

    /**
     * Generate the matrix formed by cancelling out the elements 
     * at the given row and column
     * 
     * @param int row the row
     * @param int column the column
     * @return Matrix sub the resulting matrix
     */
    public Matrix getSubMatrix(int row,int column)
    {
        Matrix sub = new Matrix(this.rows - 1, this.columns - 1);

        int currRow = 0 , currColumn = 0;

        for(int i = 0;i < this.rows;i++){
          currColumn = 0;
           for(int j = 0;j < this.columns;j++){
              if(i != row && j != column){
                  sub.data[currRow][currColumn] = this.data[i][j];
                  currColumn++;
              }
           }
           
           if(i != row){
              currRow++;
           }
        }

        return sub;
    }
    
    /**
     * Get the adjoint of this matrix
     * 
     * @param void
     * @return Matrix 
     */
    public Matrix getAdjoint()
    {
        Matrix cofactors = this.getCofactors();
        return cofactors.transpose();
    }

    /**
     * Get the inverse matrix of this matrix
     *
     * @param void
     * @return Matrix inverse the inverse of this matrix
     */
    public Matrix getInverse()
    {
        Matrix adj = this.getAdjoint();

        return adj.scalarMultiply(1.0 / this.getDeterminant());
    }
}