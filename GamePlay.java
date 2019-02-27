import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class GamePlay {
	
	public static int minimax(char [][] board, int player, char p1button, char pcbutton) {
		//if terminalTest is triggered, that means someone has won.
		if(terminalTestTxT(board)) {
			//returns 1 if computer wins, -1 if it looses, 0 in case of draw.
			
			return utility(board,pcbutton);
		}
		//if terminalTest wasn't triggered, and the board is full, that means we've a draw, we return 0;
		if(fullBoard(board)) {

			return 0;
		}
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		//0 is the maximizing player
		if(player == 0) {
			//we make a list of all possible moves
			List <char[][]>pMoves = allPossibleMoves(board, pcbutton);
			//we iterate over all the possible moves, minimaxing them and changing the player
			
			for(int i = 0; i < pMoves.size(); i++) {
				int score = minimax(pMoves.get(i),1, p1button, pcbutton);
				max = Math.max(score,max);
			}

			return max;
			
		}
		else {
			//we make a list of all possible moves
			List <char[][]>pMoves = allPossibleMoves(board, p1button);
			//we iterate over all the possible moves, minimaxing them and changing the player
			for(int i = 0; i < pMoves.size(); i++) {
				int score = minimax(pMoves.get(i),0,p1button, pcbutton);
				min = Math.min(score,min);
			}
			return min;
			
		}

		
	}
	public static char[][] deepCopy(char [][] original, char [][] copy){
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	//this function expands the frontier 
	
	public static List<char[][]> allPossibleMoves(char [][] board, char button) {
		char[][] save = {
			    {'-', '-', '-'}, 
			    {'-', '-', '-'}, 
			    {'-', '-', '-'} 
			};
		save = deepCopy(board, save);


		List<char[][]> boardList = new ArrayList<char[][]>();
		//we wanna populate the boardList with different char[][] objects.
		for(int col = 0; col < 3; col++) {
			char[][] temp = {
				    {'-', '-', '-'}, 
				    {'-', '-', '-'}, 
				    {'-', '-', '-'} 
				};
			temp = deepCopy(dropAButton(board,col,button),temp);
			//reset board to what it used to be.

			board = deepCopy(save,board);
			if(!(Arrays.deepEquals(board, temp))) {
				boardList.add(temp);
			}
			
		}

		return boardList;
		
	}
	
	//this is formally defined as the action function, it takes a board, a column, and a 'button' either x or y, and updates.
	public static char[][] dropAButton(char[][] board, int column, char button) {
		for(int i = 2; i >= 0; i--) {
			if(board[i][column]=='-') {
				board[i][column]=button;
				return board;
			}
		}
		return board;
	}
	public static boolean fullBoard(char [][] board) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == '-') {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean terminalTestTxT(char [][] board ) {
		//winning arrays
		char a[]= {'X','X','X'};
		char b[]= {'O','O','O'};
		//check horizontal
		for(int i = 0; i < 3 ; i++) {
			if(Arrays.equals(a, board[i]) || Arrays.equals(b, board[i])) {
				return true;
			}
		}
		//vertical check
		char c[]= {board[0][0],board[1][0],board[2][0]};
		char d[]= {board[0][1],board[1][1],board[2][1]};
		char e[]= {board[0][2],board[1][2],board[2][2]};
		if(Arrays.equals(a, c) || Arrays.equals(b, c)|| Arrays.equals(a, d)|| Arrays.equals(b, d)|| Arrays.equals(a, e)|| Arrays.equals(b, e)) {
			return true;
		}
		//diagonal check
		char f[]= {board[0][0],board[1][1],board[2][2]};
		char g[]= {board[0][2],board[1][1],board[2][0]};
		if(Arrays.equals(a, f) || Arrays.equals(b, f)|| Arrays.equals(a, g)|| Arrays.equals(b, g)) {
			return true;
		}
		
		return false;
	}
	//returns 1 if x has won, 0 if O has won
	public static int utility(char [][] board, char button) {
		char a[]= {'X','X','X'};

		if(button == 'O') {
			for(int i = 0; i <3; i++) {
				a[i]= 'O';
			}
		}
		
		//check horizontal
		for(int i = 0; i < 3 ; i++) {
			if(Arrays.equals(a, board[i])) {
				return 1;
			}
		}
		//vertical check
		char c[]= {board[0][0],board[1][0],board[2][0]};
		char d[]= {board[0][1],board[1][1],board[2][1]};
		char e[]= {board[0][2],board[1][2],board[2][2]};
		if(Arrays.equals(a, c) || Arrays.equals(a, d)|| Arrays.equals(a, e)) {
			return 1;
		}
		//diagonal check
		char f[]= {board[0][0],board[1][1],board[2][2]};
		char g[]= {board[0][2],board[1][1],board[2][0]};
		if(Arrays.equals(a, f) || Arrays.equals(a, g)) {
			return 1;
		}
		
		return -1;

	}
	public static void drawBoard(char [][] board) {
		System.out.println("-------------------------------");

		for(int i = 0; i < board.length ; i++) {
			for(int j = 0; j < board[0].length; j++) {
				System.out.print("|"+ board[i][j]+"|");
			}
			System.out.println();
		}
		System.out.println("-------------------------------");

	}
	//function that uses minimax scores to determine the best possible move for the PC
	public static int PCmove(char[][] board, int player,char button) {
		char playerButton = 'X';
		if(button == 'X') {
			playerButton = 'O';
		}
		//create a list of possible moves, give each of them a score, choose the board with the highest score
		//return an integer, which is the number of column to play in that move
		List<Integer> miniMaxList = new ArrayList<>();
		List <char[][]> boards = allPossibleMoves(board,button);
		for(int i = 0; i < boards.size(); i++) {
			//keep track of which board has the highest score
			miniMaxList.add(minimax(boards.get(i),player,playerButton,button));
		}
		//return the column in which it is best to play
		char [][] bestBoard = boards.get(miniMaxList.indexOf(Collections.max(miniMaxList)));
		//compare to find the difference
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(bestBoard[i][j]!=board[i][j]) {
					return j;
				}
			}
		}
		return 0;
		
	}
	public static void game() {
		//The computer chooses the move with highest minimax value,
		//plays that move, 
		//the user responds, 
		//computer reevaluates minimax, till someone wins.
		System.out.println("HELLO! I'm BRUNO, ARTURO's younger but mightier brother, it is rumored I cannot lose, wanna bet?");
		System.out.println("WELCOME TO CONNECT 4");
		
		System.out.println("PLEASE CHOOSE YOUR COLOR, enter 'X' for 'X' and 'O' for 'O'- 'X' goes first.");
		char[][] board = {
			    {'-', '-', '-'}, 
			    {'-', '-', '-'}, 
			    {'-', '-', '-'} 
			};
		
		Scanner scan = new Scanner(System.in);
		char playerButton = scan.next().charAt(0);
		char computerButton = 'X';
		if(playerButton == 'X') {
			while(true) {
				//add a while(true) loop here.
				computerButton = 'O';
				//player starts, computer plays
				System.out.println("Now its your turn, type in the number of column [0 - 1 - 2] :");
	      		int playerCol = scan.nextInt();
	      		while(!(possiblePlay(playerCol,board))) {
	      			System.out.println("This column is full, please try another one!");
	      			playerCol = scan.nextInt();
	      		}
	      		dropAButton(board,playerCol,playerButton);
	      		//check for win and report who's won
	      		drawBoard(board);
	      		if(PlayerWin(playerButton, board)) {
	      			System.out.println("Congrats, you've won!");
	      			return;
	      		}
	      		if(fullBoard(board)) {
	      			System.out.println("Its a draw!");
	      			return;
	      		}
				//computers turn
	      		drawBoard(dropAButton(board,PCmove(board,1,computerButton),computerButton));
	      		if(PlayerWin(computerButton, board)) {
	      			System.out.println("Sorry, you've lost!");
	      			return;
	      		}
	      		if(fullBoard(board)) {
	      			System.out.println("Its a draw!");
	      			return;
	      		}
			}

		}
		else {
			//computer starts
			while(true) {
			System.out.println("The PC plays");
			drawBoard(dropAButton(board,PCmove(board,1,computerButton),computerButton));
      		if(PlayerWin(computerButton, board)) {
      			System.out.println("Sorry, you've lost!");
      			return;
      		}
      		if(fullBoard(board)) {
      			System.out.println("Its a draw!");
      			return;
      		}
			System.out.println("Now its your turn, type in the number of column [0 - 1 - 2] :");
      		int playerCol = scan.nextInt();
      		while(!(possiblePlay(playerCol,board))) {
      			System.out.println("This column is full, please try another one!");
      			playerCol = scan.nextInt();
      		}
			dropAButton(board,playerCol,playerButton);
			drawBoard(board);
      		if(PlayerWin(playerButton, board)) {
      			System.out.println("Congrats, you've won!");
      			return;
      		}
      		if(fullBoard(board)) {
      			System.out.println("Its a draw!");
      			return;
      		}
			}
			
		}

		
	}
	public static boolean possiblePlay(int col, char [][] board) {
		return (board[0][col] == '-') ;
	}
	public static boolean PlayerWin(char button, char [][] board) {
		
		//winning arrays
		char a[]= {'X','X','X'};
		if( button == 'O') {
			for(int i = 0; i < 3; i++) {
				a[i] = 'O';
			}
		}
		//check horizontal
		for(int i = 0; i < 3 ; i++) {
			if(Arrays.equals(a, board[i])) {
				return true;
			}
		}
		//vertical check
		char c[]= {board[0][0],board[1][0],board[2][0]};
		char d[]= {board[0][1],board[1][1],board[2][1]};
		char e[]= {board[0][2],board[1][2],board[2][2]};
		if(Arrays.equals(a, c) || Arrays.equals(a, d)||  Arrays.equals(a, e)) {
			return true;
		}
		//diagonal check
		char f[]= {board[0][0],board[1][1],board[2][2]};
		char g[]= {board[0][2],board[1][1],board[2][0]};
		if(Arrays.equals(a, f) || Arrays.equals(a, g)) {
			return true;
		}
		
		return false;
	}
	public static void play() {
		System.out.println("Hello and Welcome to PROJECT1- CONNECT4");
		System.out.println("There are two parts to this project");
		while(true) {
			System.out.println("1- Connect-Three uses MINIMAX");
			System.out.println("2- Connect-Four uses fixed depth HUERISTIC MINIMAX with alpha/beta pruning");
			System.out.println("Please make your choice [enter 1 or 2 to choose- 0 to quit]:");
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			if(choice == 1) {
				GamePlay.game();
				System.out.println("Would you like to Move on?");
			}
			if(choice == 2) {
				GamePlay2.game();
			}
			if(choice == 0) {
				System.exit(0);
			}
		}


	}

}
