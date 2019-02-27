import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class board {
	char [][] board;
	int player;
	public board(char [][] board, int min, int max) {
		this.board = board;

	}
	public static void drawBoard(char [][] board) {
		System.out.println("-------------------------------");
		for(int i = 0; i < board.length ; i++) {
			for(int j = 0; j < board[0].length; j++) {
				System.out.print("|"+ board[i][j]+"|");
			}
			System.out.println("-------------------------------");
		}
	}


//		List<char[][]> boardList = new ArrayList<char[][]>();
//		for (int row = 0; row < 3; row++) {
//			emptyBoard[row][1] = 'b';
//			boardList.add(row,emptyBoard);
//		}
//		for(int i = 0; i <boardList.size(); i++) {
//			System.out.println(i);
//			drawBoard(boardList.get(i));
//		}
	
	//what data structure would I use??
	
	//navigate the whole tree
	
	//minimax algorithm
	
	//alpha beta pruning
	
	//h-minimax
	
	
}
