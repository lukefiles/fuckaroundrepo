package linkedMatrix;
import java.util.NoSuchElementException;



/**
 * 
 */

/**
 * @author Kalen Wood-Wardlow
 *
 */
public class MyLinkedMatrix 
{

	/*
	 * @var current the current node
	 * @var start the top left node
	 * @var first the start node of a certain row
	 * @var prev previous top node for initializing down
	 * @var rowX the number of rows in the matrix
	 * @var colY the number of columns in the matrix
	 */
	public Node start;
	public Node current;
	public Node first;
	public Node prev;
	public int rowX;
	public int colY;
	
	/*
	 * Node the "links" for the matrix containing data
	 */
	public class Node
	{
		/*
		 * @var item the data contained inside the node
		 * @var right refers to its node on the right of the current node
		 * @var down refers to its node right below the current node
		 */
		public int item;
		public Node right;
		public Node down;
		
		 /*
		  * initializes the node
		  * @parm value sets the nodes initial item
		  */
		public Node(int value)
		{
			this.item = value;
			this.right = null;
			this.down = null;
		}
		
	}
	
	/*
	 * init(int, int) intitializes the linked matrix
	 * @param rows the amount of rows to be in the linked matrix
	 * @param cols the amounmt of columns to be in the linked matrix
	 * @return true if matrix initialized, false if input is not a valid number
	 */
	public boolean init(int rows, int cols) {
		if(rows > 0 && cols > 0)
		{
			rowX = rows;
			colY = cols;
			for(int i = 1; i <= rows; i++)
			{
				if(start == null){start = new Node(0);} //creates the first node
				else{first = new Node((i-1)*10);} //creates the first node of that row
				if(prev == null)
				{
					makeRow(start, cols); //makes row
					prev = first; //saves the row to set set down values
				}
				else
				{
					makeRow(first, cols); 
					for(int n = 1; n < cols; n++) //sets the prev row down values to the new rows
					{
						prev.down = current;
						prev = prev.right;
						current = current.right;
					}
					prev = first;
				}
			}
			
			return true;
		}
		
		else{return false;}
	}

	/*
	 * makeRow creats a new row in the linked matrix
	 * @param start the first node in the row
	 * @param col the length of the row
	 */
	public void makeRow(Node start, int col)
	{
		first = start;
		current = first;
		
		for(int j = 1; j < col; j++)
		{
			Node next = new Node(first.item);
			current.right = next;
			current = next;
			current.item += 1*j;
		}
		current = first;
	}
	
	
	/*
	 * setValue() sets a specific nodes item
	 * @param value the new item value
	 * @param row the position in the rows
	 * @param col the position in the columns
	 */
	public boolean setValue(int value, int row, int col) {
		if(row > rowX || col > colY)
		{
			return false;
		}
		if(row <= 0 || col <= 0)
		{
			return false;
		}
		else
		{
			current = start;
			for(int j = 1; j < row; j++)
			{
				downIterator();
			}
			for(int i = 1; i < col; i++)
			{
				rightIterator();
			}
			current.item = value;
			
			return true;
		}
	}

	/*
	 * fill(int) changes all nodes value to a single value
	 * @param value the integer to change all nodes items
	 */
	public void fill(int value) 
	{
		current = start;
		for(int i = 1; i <= rowX; i++)
		{
			first = current;
			for(int j = 1; j <= colY; j++)
			{
				current.item = value;
				rightIterator();
			}
			current = first.down;
		}

	}

	/*
	 *sumRow(int) adds the rows values together
	 *@param row the row you which to sum
	 */
	public int sumRow(int row) throws NoSuchElementException {
		if(row <= 0 || row > rowX)
		{
			throw new NoSuchElementException("Invalid entry.");
		}
		else
		{
			current = start;
			int sum = 0;
			for(int i = 1; i < row; i++)
			{
				downIterator();
			}
			for(int k = 1; k <= colY; k++)
			{
				sum += current.item;
				current = current.right;
			}
			return sum;
		}
	}

	/*
	 * sumCol(int) adds the columns values together
	 * @param col the column you which to sum
	 */
	public int sumCol(int col) throws NoSuchElementException {
		if(col <= 0 || col > colY)
		{
			throw new NoSuchElementException("Invalid entry.");
		}
		else
		{
			current = start;
			int sum = 0;
			for(int i = 1; i < col; i++)
			{
				rightIterator();
			}
			for(int k = 1; k <= rowX; k++)
			{
				sum += current.item;
				downIterator();
			}
			return sum;
		}
	}

	/*
	 * getValue() gets the value at the specified position in the matrix
	 * @param row the row position the value will be accessed
	 * @param col the column position the value will be accessed
	 */
	public int getValue(int row, int col) throws NoSuchElementException 
	{
		if(row > rowX || col > colY)
		{
			throw new NoSuchElementException("Row or Column input is to large");
		}
		if(row <= 0 || col <= 0)
		{
			throw new NoSuchElementException("Row or Column input is a negative number");
		}
		else
		{
			current = start;
			for(int j = 1; j < row; j++)
			{
				downIterator();
			}
			for(int i = 1; i < col; i++)
			{
				rightIterator();
			}
			
			return current.item;
		}
	}
	/*
	 * display() displays the matrix to the console
	 */
	public void display()
	{
		current = start;
		System.out.print("Row|");
		for(int z = 1; z <= colY; z++){System.out.print(" " + z + "| ");}
		System.out.println("");
		System.out.println("---|------------------");
		for(int w = 1; w <= rowX; w++)
		{
			first = current;
			System.out.print("  "+w+ "|");
			for(int m = 1; m <= colY; m++)
			{
				if(w == 1){System.out.print(" ");}
				System.out.print(current.item + "| ");
				current = current.right;
			}
			System.out.println("  ");
			current = first.down;
		}
	}
		
	/*
	 * rightIterator() moves the current node to the node right of it
	 */
	public void rightIterator()
	{
		if(current == null)
		{
			current = start;
		}
		current = current.right;
		
	}
	
	/*
	 * downIterator() moves the current node to the node below it
	 */
	public void downIterator()
	{
		if(current == null)
		{
			current = start;
		}
		current = current.down;

	}
}
