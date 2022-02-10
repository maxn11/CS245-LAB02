# CS245-LAB02
RUNTIME:
  - For this program, the time complexity is O(9^m).
  It's 9 because we know the size of the sudoku grid, if we didn't, it would be O(n^m).
  And m is the amount of zeros or '.' or unknown numbers in the unfilled grid.
  The program runs the for loop 9 times and m is how many times the if(element == 0) runs within the nested for loops.
  
SPACE COMPLEXTIY:
  - For this program, the space complexity is O(1).
  Since the size of the sudoku grid is a constant 81 cells, it is constant 1.
  There is also a significant amount of overhead from the recursive process.
