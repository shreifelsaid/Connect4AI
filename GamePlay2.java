import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GamePlay2 {
    //minimax with alpha beta pruning, and variable depth which uses a heuristic function to 
	//estimate the utility on non-terminal state.
	static int  difficulty = 5;
	public static int apminimax(char [][] board, int player, char p1button, char pcbutton, int alpha, int beta,int depth) {
		if(depth == 0) {
			return heuristic(board,pcbutton);
		}
		//if terminalTest is triggered, that means someone has won.
		int whoWon = terminalTestTxT(board);
		if(whoWon!=0) {
			//returns 1 if computer wins, -1 if it looses, 0 in case of draw.
			return utility( pcbutton,  p1button,  whoWon);
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
				int score = apminimax(pMoves.get(i),1, p1button, pcbutton,alpha,beta,depth-1);
				max = Math.max(score,max);
				alpha = Math.max(alpha, score);
				if(beta <= alpha) {
					//no need to continue
					break;
				}
			}

			return max;
			
		}
		else {
			//we make a list of all possible moves
			List <char[][]>pMoves = allPossibleMoves(board, p1button);
			//we iterate over all the possible moves, minimaxing them and changing the player
			for(int i = 0; i < pMoves.size(); i++) {
				int score = apminimax(pMoves.get(i),0,p1button, pcbutton,alpha,beta,depth -1);
				min = Math.min(score,min);
				beta = Math.min(beta, score);
				if(beta <= alpha) {
					//no need to continue
					break;
				}
			}
			return min;
			
		}
	}

	public static int terminalTestTxT(char [][] board ) {
		//winning arrays
		String a = "XXXX";
		String b = "OOOO";
		int aWins = 1;
		int bWins = 2;
		int noWins = 0;
		//check horizontal, could be improved
		for(int row = 0; row < 6 ; row++) {
			String test = new String(board[row]);
			if(test.contains(a)) {
				return aWins;
			}
			if(test.contains(b)) {
				return bWins;
			}
		}
		
		for(int col = 0; col < 7; col++) {
			String construction = "";
			for(int row = 0; row < 6; row++) {
				
				construction = construction + board[row][col];
			}
			if(construction.contains(a)) {
				return aWins;
			}
			if(construction.contains(b)) {
				return bWins;
			}
		}
		//diagonal checks
		for(int row = 0; row < 6; row++) {
			for(int col = 0; col< 7; col++) {
				String construction = "";
				if(!((row>2)||(col>3))) {
					construction ="" + board[row][col] + board[row+1][col+1] + board[row+2][col+2] + board[row+3][col+3];

					if(construction.equals(a)) {
						return aWins;
					}
					if(construction.equals(b)) {
						return bWins;
					}
				}
				String construction2 = "";
				if(!((col < 3) || (row > 2))) {
					construction2 ="" + board[row][col] + board[row+1][col-1] + board[row+2][col-2] + board[row+3][col-3];
					if(construction2.equals(a)) {
						return aWins;
					}
					if(construction2.equals(b)) {
						return bWins;
					}
				}
			}
		}


		return noWins;
	}
	//this heuristic works by identifying potential threat patterns and rewarding them with lower scores,
	//and potential benefit scienarios and awarding them with high scores.
	public static int heuristic (char[][] board, char pcbutton) {
		int score = 0;
		//threat strings
		String a = "-XXX";
		String a1 = "X-XX";
		String a2 = "XX-X";
		String a3 = "XXX-";
		String b = "-OOO";
		String b1 = "O-OO";
		String b2 = "OO-O";
		String b3 = "OOO-";
		if(pcbutton == 'O') {
			 b = "-XXX";
			 b1 = "X-XX";
			 b2 = "XX-X";
			 b3 = "XXX-";
			 a = "-OOO";
			 a1 = "O-OO";
			 a2 = "OO-O";
			 a3 = "OOO-";
		}
		
		//the hueristic is based on the idea that it is good to have 3-in a row, and bad if your 
		//opponent has 3-in a row, because that means they are more likely to win.
		//horizontal check
		for(int row = 0; row < 6 ; row++) {
			String test = new String(board[row]);

			if((test.contains(a))||(test.contains(a1))||(test.contains(a2))||(test.contains(a3))) {
				score = score + 40;
			}
			if((test.contains(b))||(test.contains(b1))||(test.contains(b2))||(test.contains(b3))) {
				score = score - 40;
			}
		}
		
		//vertical check
		for(int col = 0; col < 7; col++) {
			String construction = "";
			for(int row = 0; row < 6; row++) {
				
				construction = construction + board[row][col];
			}
			if(construction.contains(a)) {
				score = score + 40;
			}
			if(construction.contains(b)) {
				return -100;
			}
		}
		
		//diagonal check
		for(int row = 0; row < 6; row++) {
			for(int col = 0; col< 7; col++) {
				String construction = "";
				if(!((row>2)||(col>3))) {
					construction ="" + board[row][col] + board[row+1][col+1] + board[row+2][col+2] + board[row+3][col+3];

					if((construction.equals(a))||(construction.equals(a1))||(construction.equals(a2))||(construction.equals(a3))) {
						score = score + 40;
					}
					if((construction.equals(b))||(construction.equals(b1))||(construction.equals(b2))||(construction.equals(b3))) {
						score = score - 40;
					}
				}
				String construction2 = "";
				if(!((col < 3) || (row > 2))) {
					construction2 ="" + board[row][col] + board[row+1][col-1] + board[row+2][col-2] + board[row+3][col-3];
					if((construction2.equals(a))||(construction2.equals(a1))||(construction2.equals(a2))||(construction2.equals(a3))) {
						score = score + 40;
					}
					if((construction2.equals(b))||(construction2.equals(b1))||(construction2.equals(b2))||(construction2.equals(b3))) {
						score = score - 40;
					}
				}
			}
		}
		
		return score;
	}
	
	public static List<char[][]> allPossibleMoves(char [][] board, char button) {
		char[][] save = {
			    {'-', '-', '-','-','-','-','-'}, 
			    {'-', '-', '-','-','-','-','-'}, 
			    {'-', '-', '-','-','-','-','-'}, 
			    {'-', '-', '-','-','-','-','-'},
			    {'-', '-', '-','-','-','-','-'}, 
			    {'-', '-', '-','-','-','-','-'}, 

			};
		save = deepCopy(board, save);


		List<char[][]> boardList = new ArrayList<char[][]>();
		//we wanna populate the boardList with different char[][] objects.
		for(int col = 0; col < 7; col++) {
			char[][] temp = {
				    {'-', '-', '-','-','-','-','-'}, 
				    {'-', '-', '-','-','-','-','-'}, 
				    {'-', '-', '-','-','-','-','-'}, 
				    {'-', '-', '-','-','-','-','-'},
				    {'-', '-', '-','-','-','-','-'}, 
				    {'-', '-', '-','-','-','-','-'}, 

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
	//copies one char array into the other, implements "deep"copy.
	public static char[][] deepCopy(char [][] original, char [][] copy){
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	//this is formally defined as the action function, it takes a board, a column, and a 'button' either x or y, and updates.
	public static char[][] dropAButton(char[][] board, int column, char button) {
		for(int i = 5; i >= 0; i--) {
			if(board[i][column]=='-') {
				board[i][column]=button;
				return board;
			}
		}
		return board;
	}
	public static boolean fullBoard(char [][] board) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if(board[i][j] == '-') {
					return false;
				}
			}
		}
		return true;
	}
	public static int utility(char pcbutton, char p1button, int whoWon) {
		if(whoWon == 1) {
			//XX wins
			if(pcbutton == 'X') {
				return 100;
			}
			else {
				return -100;
			}
			
		}else {
			if(pcbutton == 'O') {
				return 100;
			}
			else {
				return -100;
			}
			
		}
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
	
	public static void game() {
		//The computer chooses the move with highest minimax value,
		//plays that move, 
		//the user responds, 
		//computer reevaluates minimax, till someone wins.
		System.out.println("HELLO! I'm ARTURO, The SUPER DUPER ADVANCED KING OF CONNECT 4!!!");
		
		System.out.println("WELCOME TO CONNECT 4");
		System.out.println("Please choose your difficulty 1-Beginner 2-Intermidate 3-Expert");
		System.out.println("Enter [1 or 2 or 3]:");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		if(choice == 1) {
			difficulty = 3;
		}
		if(choice == 2) {
			difficulty = 5;
		}
		if(choice == 3) {
			difficulty = 8;
		}
		
		System.out.println("PLEASE CHOOSE YOUR COLOR, enter 'X' for 'X' and 'O' for 'O'-'X' goes first.");
		char[][] board = {
			    {'-', '-', '-','-','-','-','-'}, 
			    {'-', '-', '-','-','-','-','-'}, 
			    {'-', '-', '-','-','-','-','-'}, 
			    {'-', '-', '-','-','-','-','-'},
			    {'-', '-', '-','-','-','-','-'}, 
			    {'-', '-', '-','-','-','-','-'}, 

			};
		char playerButton = scan.next().charAt(0);
		char computerButton = 'X';
		if(playerButton == 'X') {
			while(true) {
				//add a while(true) loop here.
				computerButton = 'O';
				//player starts, computer plays
				System.out.println("Now its your turn, type in the number of column [0 - 1 - 2 -3 -4 -5 -6] :");
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
	      			System.exit(0);
	      		}
	      		if(fullBoard(board)) {
	      			System.out.println("Its a draw!");
	      			System.exit(0);
	      		}
				//computers turn
				System.out.println("The PC plays");
				System.out.println("I'm thinking.....");
	      		drawBoard(dropAButton(board,PCmove(board,1,computerButton),computerButton));
	      		if(PlayerWin(computerButton, board)) {
	      			System.out.println("Sorry, you've lost!");
	      			System.exit(0);
	      		}
	      		if(fullBoard(board)) {
	      			System.out.println("Its a draw!");
	      			System.exit(0);
	      		}
			}

		}
		else {
			//computer starts
			while(true) {
			System.out.println("The PC plays");
			System.out.println("I'm thinking.....");

			drawBoard(dropAButton(board,PCmove(board,1,computerButton),computerButton));
      		if(PlayerWin(computerButton, board)) {
      			System.out.println("Sorry, you've lost!");
      			System.exit(0);
      		}
      		if(fullBoard(board)) {
      			System.out.println("Its a draw!");
      			System.exit(0);
      		}
			System.out.println("Now its your turn, type in the number of column [0 - 1 - 2 -3 -4 -5 -6] :");
      		int playerCol = scan.nextInt();
      		while(!(possiblePlay(playerCol,board))) {
      			System.out.println("This column is full, please try another one!");
      			playerCol = scan.nextInt();
      		}
			dropAButton(board,playerCol,playerButton);
			drawBoard(board);
      		if(PlayerWin(playerButton, board)) {
      			System.out.println("Congrats, you've won!");
      			System.exit(0);
      		}
      		if(fullBoard(board)) {
      			System.out.println("Its a draw!");
      			System.exit(0);
      		}
			}
			
		}

		
	}
	
	public static boolean possiblePlay(int col, char [][] board) {
		return (board[0][col] == '-') ;
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
			miniMaxList.add(apminimax(boards.get(i),player,playerButton,button,Integer.MIN_VALUE,Integer.MAX_VALUE,difficulty));
			


		}
		//return the column in which it is best to play
		char [][] bestBoard = boards.get(miniMaxList.indexOf(Collections.max(miniMaxList)));
		//compare to find the difference
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if(bestBoard[i][j]!=board[i][j]) {
					return j;
				}
			}
		}
		return 0;
		
	}
	public static boolean PlayerWin(char button, char [][] board) {
		
		//winning arrays
		String a = "XXXX";
		if(button =='O') {
			a = "OOOO";

		}

		//check horizontal, could be improved
		for(int row = 0; row < 6 ; row++) {
			String test = new String(board[row]);
			if(test.contains(a)) {
				return true;
			}

		}
		
		for(int col = 0; col < 7; col++) {
			String construction = "";
			for(int row = 0; row < 6; row++) {
				
				construction = construction + board[row][col];
			}
			if(construction.contains(a)) {
				return true;
			}

		}
		//diagonal checks
		for(int row = 0; row < 6; row++) {
			for(int col = 0; col< 7; col++) {
				String construction = "";
				if(!((row>2)||(col>3))) {
					construction ="" + board[row][col] + board[row+1][col+1] + board[row+2][col+2] + board[row+3][col+3];

					if(construction.equals(a)) {
						return true;
					}

				}
				String construction2 = "";
				if(!((col < 3) || (row > 2))) {
					construction2 ="" + board[row][col] + board[row+1][col-1] + board[row+2][col-2] + board[row+3][col-3];
					if(construction2.equals(a)) {
						return true;
					}

				}
			}
		}


		return false;

	}
	

	
}
