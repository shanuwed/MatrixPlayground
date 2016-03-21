import org.apache.spark.mllib.linalg.{DenseMatrix, SparseMatrix}
import spire.syntax.rng

import java.util.Random

/**
  * Created by sujin on 3/19/16.
  */
object MyClass {
  def main(args: Array[String]): Unit = {

    //test1()

    //test2()

    //test3()

    //test4()

    test5()
  }

  def test5(): Unit ={
    //12500 causes OutOfMemoryError
    val numRows = 1250
    val numCols = 1250

    val m1 = identityMatrix(numRows)

    val rng = new MyRand()

    val m2 = DenseMatrix.rand(numRows, numCols, rng)

    val result = m1.multiply(m2)

    print(result.toString())
  }

  // Allocate an identity matrix - a square matrix with main diagonal values of one
  // Example: 2x2 identity matrix
  // 1 0
  // 0 1
  def identityMatrix(numSize : Int) : DenseMatrix = {
    require(numSize.toLong * numSize <= Int.MaxValue,
      s"$numSize * $numSize is too large to allocate")

    // Initialized to all zero
    val vec = new Array[Double](numSize * numSize)
    var index = 0
    var seg = 0
    var offset = 0
    val max = numSize.toLong * numSize
    while(index < max){
      if(index % numSize == 0) {
        vec(index + offset) = 1
        offset += 1
      }
      index += 1
    }
    new DenseMatrix(numSize, numSize, vec)
  }

  def test4() : Unit = {

    val numRows = 2
    val numCols = 2
    val values = Array[Double](1.0, 0.0, 0.0, 1.0)

    val m1_test = new DenseMatrix(numRows, numCols, values)

    val m1 = identityMatrix(2) // 2 x 2

    //val rng = new Random()
    val rng = new MyRand()

    val m2 = DenseMatrix.rand(numRows, numCols, rng)

    val result = m1.multiply(m2)

    print(result.toString())
  }

  class MyRand extends Random
  {
    override def nextDouble() : Double ={
      this.nextInt(1234)
    }
  }

  // SparseMatrix test
  def test3() : Int = {
    /**
      * Column-major sparse matrix.
      * The entry values are stored in Compressed Sparse Column (CSC) format.
      * For example, the following matrix
      * {{{
      *   1.0 0.0 4.0
      *   0.0 3.0 5.0
      *   2.0 0.0 6.0
      * }}}
      * is stored as `values: [1.0, 2.0, 3.0, 4.0, 5.0, 6.0]`,
      * `rowIndices=[0, 2, 1, 0, 1, 2]`, `colPointers=[0, 2, 3, 6]`.
      *
      * @param numRows number of rows
      * @param numCols number of columns
      * @param colPtrs the index corresponding to the start of a new column (if not transposed)
      * @param rowIndices the row index of the entry (if not transposed). They must be in strictly
      *                   increasing order for each column
      * @param values nonzero matrix entries in column major (if not transposed)
      * @param isTransposed whether the matrix is transposed. If true, the matrix can be considered
      *                     Compressed Sparse Row (CSR) format, where `colPtrs` behaves as rowPtrs,
      *                     and `rowIndices` behave as colIndices, and `values` are stored in row major.
      For more info, see https://github.com/apache/spark/blob/master/mllib/src/main/scala/org/apache/spark/mllib/linalg/Matrices.scala

     */

    val numRows = 3
    val numCols = 3
    val colPtrs = Array[Int](0, 2, 3, 6)
    val rowIndices = Array[Int](0, 2, 1, 0, 1, 2)
    val values = Array[Double] (1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
    val m = new SparseMatrix(numRows, numCols, colPtrs, rowIndices, values)

    return 0

  }

  def test2() : Int ={

    // column-major dense matrix
    val numRows = 3
    val numCols = 2
    val values = Array[Double](1.1,2.2,3.3,4.4,5.5,6.6)
    val m = new DenseMatrix(numRows, numCols, values)

    // m is now
    // 1.1  4.4
    // 2.2  5.5
    // 3.3  6.6

    val m2 = DenseMatrix
    val rng = new Random()
    val denseM = m2.rand(numRows, numCols, rng)

    return 0
  }

  def test1(){
    //SparseMatrix
    println("Hello from MyClass")

    val numRows = 3
    val numCols = 2
    val colPtrs = Array[Int](1,2,4)
    val rowIndices = Array[Int](1,2,3,9)
    val values = Array[Double](1.1,2.2,3.3,4.4)
    val m = new SparseMatrix(numRows, numCols, colPtrs, rowIndices, values)

    println(m.isTransposed)
    //println(m.toString())
  }
}
