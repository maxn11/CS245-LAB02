//Maximus Nie
//2/10/2022
//CS245 Lab 2: Recursive Backtracking Sudoku Algorithm
import java.util.Arrays;
import java.util.Scanner;

public class SudokuSolver {
	
	private int[][] board;
	
	/**
	 * Default constructor -- construct an empty Sudoku puzzle
	 */
	public SudokuSolver () {
		board = new int[9][9];
	}
	
	//filled constructor
	public SudokuSolver(int board[][]) {
		this.board = board;
	}

	/**
	 * Input an initial starting board for Sudoku. Consider using one online, such as
	 * https://www.sudokuoftheday.com/
	 * @param board
	 * @throws Exception
	 */
	public void inputBoard() throws Exception {

		//CONVERT INPUT STRING TO CHAR ARRAY AND INSERT INTO INT BOARD
		Scanner scan = new Scanner(System.in);
		System.out.println("You may use either 0 or '.' to represent unfilled numbers.");
		System.out.println("Example: X0X000XX0 or X.X...XX.");
		for(int i = 0; i < 9; i++) {
			//user input
			System.out.println("Enter 9 numbers for row " + (i + 1) + ":");
			String temp = scan.nextLine();
			//if the user input isn't 9, close scanner and shoot an error
			if(temp.length() != 9) {
				scan.close();
				throw new Exception();
			}
			//separate string into chars
			char[] chArr = temp.toCharArray(); 
			
			//add to board
			for(int j = 0; j < 9; j++) {
				//check if the user used periods to represent zeros ('.') 46 ASCII
				if(chArr[j] == 46) {
					//change to zero
					chArr[j] = 48;
				}
				//add to board minus ASCII representation
				board[i][j] = (int)chArr[j] - 48;
			}
		}
		scan.close();
		
		/*
		EXAMPLE SUDOKU BOARD
		{{8,3,0,5,0,2,0,9,0}, 
		 {0,9,0,0,8,0,5,0,0},
		 {0,7,1,4,0,0,2,0,6},
		 {0,0,0,0,0,0,8,0,9},
		 {0,8,5,9,0,3,6,7,0},
		 {7,0,9,0,0,0,0,0,0},
		 {1,0,3,0,0,8,9,6,0},
		 {0,0,7,0,6,0,0,1,0},
		 {0,5,0,1,0,4,0,2,3}};
		*/
		
		//double check array size or throw an error
		if(board.length != 9 || board[0].length != 9) {
			throw new Exception();
		}
		
	}
	
	//check contents on sudoku board for following helper functions
	//CHECK HORIZONTALLY
	public boolean rowCheck(int rowPos, int currNum) {
		//for loop traverses rows
		for(int i = 0; i < 9; i++) {
			if(board[rowPos][i] == currNum) {
				return true;
			}
		}
		return false;
	}
	
	//CHECK VERTICALLY
	public boolean columnCheck(int columnPos, int currNum) {
		//for loop traverses columns
		for(int i = 0; i < 9; i++) {
			if(board[i][columnPos] == currNum) {
				return true;
			}
		}
		return false;
	}
	
	//CHECK 3x3 GRID
	public boolean subGridCheck(int rowPos, int columnPos, int currNum) {
		//GOAL: determine sub grid by separating into positions 0,3,6 then 
		//adding 0-2 to traverse sub grid and compare
		int subgridRowPos = rowPos - rowPos % 3; //will either be 0,3,6
		int subgridColumnPos = columnPos - columnPos % 3; //will either be 0,3,6
		for(int i = subgridRowPos; i < (subgridRowPos + 3); i++) {
			for(int j = subgridColumnPos; j < (subgridColumnPos + 3); j++) {
				//compare and if it matches, then it's already in the sub grid
				if(board[i][j] == currNum) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Check if the position returns true for all of the checkpoints
	public boolean isValid(int rowPos, int columnPos, int currNum) {
		//if any of them are true, then the current number is not valid
		if(subGridCheck(rowPos, columnPos , currNum) || columnCheck(columnPos, currNum) || rowCheck(rowPos, currNum)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Solves the Sudoku puzzle from the starting position, if possible.
	 * @return true if the puzzle is solved; false if it cannot be solved.
	 */
	
	public boolean solveSudoku () {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				//check if the position is empty
				if(board[i][j] == 0) {
					//run through 1-9 in current spot
					for(int test = 1; test < 10; test++) {
						if(isValid(i, j, test)) {
							//set in current spot if it passes all of the tests
							board[i][j] = test;
							//recursive statement
							//if it reaches the end and the last position works, return true
							if(solveSudoku()) {
								return true;
							} else {
								//set position back to 0 and backtrack by returning false
								board[i][j] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Prints the Sudoku board to the console.
	 */
	public void printBoard() {
		for(int i = 0; i < board.length; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
	}
	
	public static void main(String[] args) {
		SudokuSolver sudoku = new SudokuSolver();
		try {
			sudoku.inputBoard();
			if(!sudoku.solveSudoku()) {
				//statement if the board can't be solved and doesn't print the board
				System.out.println("Cannot solve this board.");
			} else {
				sudoku.printBoard();
			}
		} catch (Exception e) {
			//print the problem
			System.out.println("Invalid Board Size");
		}
	}
	
}
