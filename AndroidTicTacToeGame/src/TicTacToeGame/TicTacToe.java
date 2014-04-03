package TicTacToeGame;

import java.util.Random;

import android.widget.Toast;



public class TicTacToe {
	public static final int DIFFICULTY_EASY = 1;
	public static final int DIFFICULTY_MEDIUM = 2;
	public static final int DIFFICULTY_HARD = 3;
	private char[] board = new char[9];			//
	private boolean playersFirstMove = true;	//Used for Center Strategy of computer
	private boolean useCenterStrategy = false;
	private int lastComputerMove;
	private int lastPlayerMove;
	private boolean playerVsPlayer = false;
	private boolean player1HasNextMove = true;
	private int difficulty = DIFFICULTY_EASY;
	
	//Constructor: Creates new gameInitializes Board
	public TicTacToe(int difficulty) {
		this.difficulty = difficulty;
		playerVsPlayer = false;
		board[0] = ' ';
		board[1] = ' ';
		board[2] = ' ';
		board[3] = ' ';
		board[4] = ' ';
		board[5] = ' ';
		board[6] = ' ';
		board[7] = ' ';
		board[8] = ' ';
	}
	public TicTacToe() {
		playerVsPlayer = true;
		board[0] = ' ';
		board[1] = ' ';
		board[2] = ' ';
		board[3] = ' ';
		board[4] = ' ';
		board[5] = ' ';
		board[6] = ' ';
		board[7] = ' ';
		board[8] = ' ';
	}
	
	//Inputs players Move if game is still 
	public void inputMove(int selection) {
		if(!isGameOver()) {
			//If this game is player vs computer
			if(!playerVsPlayer) {
				lastPlayerMove = selection;
				board[selection] = 'X';
				if(playersFirstMove == true && selection == 4) {
					useCenterStrategy = true;
				}
				playersFirstMove = false;
				if(!isGameOver())
					computerMove();
				lastPlayerMove = selection;
			}
			//If this game is player vs player
			else {
				if(player1HasNextMove) {
					board[selection] = 'X';
					player1HasNextMove = false;
				}
				else {
					board[selection] = 'O';
					player1HasNextMove = true;
				}
			}
		}
		else {
			System.out.println("ERROR entered move when game already over");
			System.exit(0);
		}
	}
	//Checks if game is over
	public boolean isGameOver() {
		if(isPlayerWinner() || isCompWinner() || isTie()) {
			return true;
		}
		else
			return false;
	}
	//Returns winner of game, 
	//if game not over returns error and exits app
	public char getWinner() {
		if(isPlayerWinner())
			return 'X';
		else if (isCompWinner())
			return 'O';
		else if(isTie()) {
			return 'T';
		}
		else {
			System.out.println("ERROR getWinner, when game not over");
			System.exit(0);
			return ' ';
		}
	}
	//Returns the move at a location on the board
	public char getBoardSpot(int i) {
		if(i >= 0 && i <= 8) 
			return board[i];
		else
			System.out.println("ERROR wanted to get baord spot that is not possible");
			System.exit(0);
			return ' ';
	}
	//Returns the last computers move
	public int getLastComputerMove() {
		return lastComputerMove;
	}
	
	//Computer IA:
	//If players first move was in center: place at random open corner spot
	//else if winning move is possible: place move at winning spot
	//else if it can block player from winning, place at blocking spot
	//else choose random open spot
	public void computerMove() {
		//Strategies (As specified by wiki):
		//Center
		//Block
		//Else... Random Empty Spot
		if(difficulty == DIFFICULTY_EASY) {
			int move = -1;
			//Center Strategy: If palyers first move is center computer picks 
			//random corner as first move
			if(useCenterStrategy == true) { //If player first move is center, random corner move
				move = getRandomCornerSpot();
			}
			else if((move = getBlockMove('X')) >= 0) {	//If Blocking move possible, move there
				//move assigned in else if already
			}
			else {									//Choose random open spot
				move = getRandomEmptySpot();
			}
			if(move >= 0)
				board[move] = 'O'	;
			else {
				System.out.println("ERROR: computer move returned -1, no open move");
				System.exit(0);
			}
			lastComputerMove = move;
			useCenterStrategy = false;
		}
		//Strategies (As specified by wiki):
		//Center
		//Win
		//Block
		//Else.. Random empty spot
		else if(difficulty == DIFFICULTY_MEDIUM) {
			int move = -1;
			//Center Strategy: If palyers first move is center computer picks 
			//random corner as first move
			if(useCenterStrategy == true) { //If player first move is center, random corner move
				move = getRandomCornerSpot();
			}
			else if((move = getWinningMove('O')) >= 0) {	//If winning Move Possible, move there
				//move assigned in else if already
			}
			else if((move = getBlockMove('X')) >= 0) {	//If Blocking move possible, move there
				//move assigned in else if already
			}
			else {									//Choose random open spot
				move = getRandomEmptySpot();
			}
			if(move >= 0)
				board[move] = 'O'	;
			else {
				System.out.println("ERROR: computer move returned -1, no open move");
				System.exit(0);
			}
			lastComputerMove = move;
			useCenterStrategy = false;
		}
		//Strategies:
		//Center
		//Win
		//Block
		//Opposite Corner
		//Empty Corner
		//Empty Side
		//Else Random Empty Spot
		else if(difficulty == DIFFICULTY_HARD) {
			int move = -1;
			//Center Strategy: If palyers first move is center computer picks 
			//random corner as first move
			if(useCenterStrategy == true) { //If player first move is center, random corner move
				move = getRandomCornerSpot();
			}
			else if((move = getWinningMove('O')) >= 0) {	//If winning Move Possible, move there
				//move assigned in else if already
			}
			else if((move = getBlockMove('X')) >= 0) {	//If Blocking move possible, move there
				//move assigned in else if already
			}
			else if((move = getOppositeCornerMove()) >= 0) {
				
			}
			else if((move = getEmptyCornerMove()) >= 0) {
				
			}
			else if((move = getEmptySideMove()) >= 0) {
				
			}
			else{									//Choose random open spot
				move = getRandomEmptySpot();
			}
			if(move >= 0)
				board[move] = 'O'	;
			else {
				System.out.println("ERROR: computer move returned -1, no open move");
				System.exit(0);
			}
			lastComputerMove = move;
			useCenterStrategy = false;
		}
	}
	public char getWhoWentLast() {
		if(!player1HasNextMove)
			return 'X';
		else 
			return 'O';
	}
	
	private int getRandomCornerSpot() {
		Random gen = new Random();
		int rand = gen.nextInt(4);
		if(rand == 0) {
			return 0;
		}
		else if(rand == 1) {
			return 2;
		}
		else if(rand == 2) {
			return 6;
		}
		
		else if(rand == 3) {
			return 8;
		}
		else {
			System.out.println("ERROR: in getRandomCornerSpot, getting random 0-3 failed");
			System.exit(0);
			return -1;
		}
	}
	
	private int getOppositeCornerMove() {
		if(lastPlayerMove == 0 && board[8] != 'O' && board[8] != 'X') {
			return 8;
		}
		else if(lastPlayerMove == 2 && board[6] != 'O' && board[6] != 'X') {
			return 6;
		}
		else if(lastPlayerMove == 6 && board[2] != 'O' && board[2] != 'X') {
			return 2;
		}
		else if(lastPlayerMove == 8 && board[0] != 'O' && board[0] != 'X') {
			return 0;
		}
		else
			return -1;
	}
	//Some error in here
	private int getEmptyCornerMove() {
		int numBlankCorners = 0;
		if(board[0] == ' ')
			numBlankCorners++;
		if(board[2] == ' ')
			numBlankCorners++;
		if(board[6] == ' ')
			numBlankCorners++;
		if(board[8] == ' ')
			numBlankCorners++;
		Random gen = new Random();
		int rand = gen.nextInt(numBlankCorners);
		if(rand == 0 && board[0] != 'X' && board[0] != 'O')
			return 0;
		else if(rand == 1 && board[2] != 'X' && board[2] != 'O')
			return 2;
		else if(rand == 2 && board[6] != 'X' && board[6] != 'O')
			return 6;
		else if(rand == 3 && board[8] != 'X' && board[8] != 'O')
			return 8;
		else
			return -1;
	}
	//Some error in here
	private int getEmptySideMove() {
		int numBlankSides = 0;
		if(board[1] == ' ')
			numBlankSides++;
		if(board[3] == ' ')
			numBlankSides++;
		if(board[5] == ' ')
			numBlankSides++;
		if(board[7] == ' ')
			numBlankSides++;
		Random gen = new Random();
		int rand = gen.nextInt(numBlankSides);
		if(rand == 0 && board[1] != 'X' && board[1] != 'O')
			return 1;
		else if(rand == 1 && board[3] != 'X' && board[3] != 'O')
			return 3;
		else if(rand == 2 && board[5] != 'X' && board[5] != 'O')
			return 5;
		else if(rand == 3 && board[7] != 'X' && board[7] != 'O')
			return 7;
		else
			return -1;
	}
	
	//getRandomEmptySpot returns a random open spot on the grid to be used by computer IA
	//If fails to find spot, returns -1;
	private int getRandomEmptySpot() {
		int numBlanks = 0;
		//Get number of blanks left
		for(int i = 0; i < 9; i++) {
			if(board[i] == ' ')
				numBlanks++;
		}
		Random gen = new Random();
		int rand = gen.nextInt(numBlanks);
		int blankIteration = 0;
		//Select random blank
		for(int i = 0; i < 9; i++) {
			if(board[i] == ' ' && blankIteration == rand) {
				return i;
			}
			else if (board[i] == ' ') {
				blankIteration++;
			}
		}
		return -1;
	}
	
	//Inputs a char representing the desired player to block
	//Returns a move that will block the player
	private int getBlockMove(char blockedPlayer) {
		return getWinningMove(blockedPlayer); //Returns the potential winning move of the opponent
	}
	
	//Returns position of winning move for either player
	//If no winning move, returns -1
	private int getWinningMove(char player) {
		char player2;
		if(player == 'X') {
			player2 = 'O';
		}
		else
			player2 = 'X';
		//Iterates over each spot occupied by the computer,
		//and checks to see if a winning move can be found using that spot
		//If no winning move can be found returns -1;
		for(int i = 0; i < 9; i++) {
			if(board[i] == player) {
				if(i < 3) { //If spot in 1st row, check columns
					if(board[i+3] == player && board[i+6] != player2)
						return i+6;
					else if(board[i+6] == player && board[i+3] != player2)
						return i+3;
				}
				if(i > 5) { //If spot in 3rd row, check columns
					if(board[i-3] == player && board[i-6] != player2)
						return i-6;
					else if(board[i-6] == player && board[i-3] != player2)
						return i-3;
				}
				if(i > 2 && i < 6) { //If spot in 2nd row, check columns
					if(board[i-3] == player && board[i+3] != player2)
						return i+3;
					else if(board[i+3] == player && board[i-3] != player2)
						return i-3;
				}
				if(i%3 == 0) { //If spot in 1st column, check rows
					if(board[i+1] == player && board[i+2] != player2)
						return i+2;
					else if(board[i+2] == player && board[i+1] != player2)
						return i+1;
				}
				if(i%3 == 1) { //If spot in 2nd column, check rows
					if(board[i+1] == player && board[i-1] != player2)
						return i-1;
					else if(board[i-1] == player && board[i+1] != player2)
						return i+1;
				}
				if(i%3 == 2) { //If spot in 3rd column, check rows
					if(board[i-1] == player && board[i-2] != player2)
						return i-2;
					else if(board[i-2] == player && board[i-1] != player2)
						return i-1;
				}
				if(i == 8) { //If spot is at 8, check diagonal
					if(board[i - 4] == player && board[i-8] != player2)
						return i-8;
					else if(board[i-8] == player && board[i-4] != player2)
						return i-4;
				}
				if(i == 0) { //If spot at 0, check diagonal
					if(board[i+4] == player && board[i+8] != player2)
						return i+8;
					else if(board[i+8] == player && board[i+4] != player2)
						return i+4;
				}
				if(i == 6) { //If spot at 6, check diagonal
					if(board[i-2] == player && board[i-4] != player2)
						return i-4;
					else if(board[i-4] == player && board[i-2] != player2)
						return i-2;
				}
				if(i == 2) { //If spot at 2, check diagonal
					if(board[i+2] == player && board[i+4] != player2)
						return i+4;
					else if(board[i+4] == player && board[i+2] != player2)
						return i+2;
				}
				if(i == 4) { //If spot at 4, check diagonals
					if(board[i-4] == player && board[i+4] != player2)
						return i+4;
					else if(board[i+4] == player && board[i-4] != player2)
						return i-4;
					else if(board[i-2] == player && board[i+2] != player2)
						return i+2;
					else if(board[i+2] == player && board[i-2] != player2)
						return i-2;
				}
			}
		}
		return -1;
	}
	//Checks to see if computerWon 
	//Returns boolean
	private boolean isCompWinner() {
		if(board[0] == 'O' && board[1] == 'O' && board[2] == 'O')
			return true;
		else if(board[3] == 'O' && board[4] == 'O' && board[5] == 'O')
			return true;
		else if(board[6] == 'O' && board[7] == 'O' && board[8] == 'O')
			return true;
		else if(board[0] == 'O' && board[3] == 'O' && board[6] == 'O')
			return true;
		else if(board[1] == 'O' && board[4] == 'O' && board[7] == 'O')
			return true;
		else if(board[2] == 'O' && board[5] == 'O' && board[8] == 'O')
			return true;
		else if(board[0] == 'O' && board[4] == 'O' && board[8] == 'O')
			return true;
		else if(board[2] == 'O' && board[4] == 'O' && board[6] == 'O')
			return true;
		else 
			return false;
	}
	//Checks to see if plater won
	//Returns boolean
	private boolean isPlayerWinner() {
		if(board[0] == 'X' && board[1] == 'X' && board[2] == 'X')
			return true;
		else if(board[3] == 'X' && board[4] == 'X' && board[5] == 'X')
			return true;
		else if(board[6] == 'X' && board[7] == 'X' && board[8] == 'X')
			return true;
		else if(board[0] == 'X' && board[3] == 'X' && board[6] == 'X')
			return true;
		else if(board[1] == 'X' && board[4] == 'X' && board[7] == 'X')
			return true;
		else if(board[2] == 'X' && board[5] == 'X' && board[8] == 'X')
			return true;
		else if(board[0] == 'X' && board[4] == 'X' && board[8] == 'X')
			return true;
		else if(board[2] == 'X' && board[4] == 'X' && board[6] == 'X')
			return true;
		else 
			return false;
		
	}
	//Returns true if game is over and is tie
	private boolean isTie() {
		if(board[0] != ' ' && board[1] != ' ' && board[2] != ' ' 
		&& board[3] != ' ' && board[4] != ' ' && board[5] != ' ' 
		&& board[6] != ' ' && board[7] != ' ' && board[8] != ' ') {
			return true;
		}
		else
			return false;
	}
	//Prints the board to System.out
	//Not used in GUI Version
	public String toString() {
		return(" "+board[0]+" | "+board[1]+" | "+board[2]+" \n"+
				"---+---+---\n"+
				" "+board[3]+" | "+board[4]+" | "+board[5]+" \n"+
				"---+---+---\n"+
				" "+board[6]+" | "+board[7]+" | "+board[8]+" ");
	}
	
}
