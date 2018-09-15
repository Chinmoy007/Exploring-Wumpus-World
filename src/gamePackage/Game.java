package gamePackage;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Game {
	private int boardRow;
	private int boardCol;

	private int sourceI;
	private int sourceJ;

	int fx[] = { 1, -1, 0, 0 };
	int fy[] = { 0, 0, 1, -1 };

	private String[][] board;
	private int[][] visited;

	public Game() {
		boardRow = GameSettings.BOARD_ROW;
		boardCol = GameSettings.BOARD_COL;

		this.sourceI = GameSettings.sourceI;
		this.sourceJ = GameSettings.sourceJ;
	}

	public void printBoard(String board[][]) {
		for (int i = 0; i < boardRow; i++)
			System.out.print("|-----");
		System.out.println("|");

		for (int i = 0; i < boardRow; i++) {
			System.out.print("|  ");
			for (int j = 0; j < boardCol; j++) {
				System.out.print(board[i][j]);
				if (j != boardCol - 1)
					System.out.print("  |  ");
			}
			System.out.println("  |  ");

			for (int k = 0; k < boardRow; k++)
				System.out.print("|-----");
			System.out.println("|");
		}

	}

	public String[][] initialiseBoard() {

		Position tempPos;
		board = new String[boardRow][boardCol];

		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardCol; j++) {
				board[i][j] = "E";
			}
		}

		board[sourceI][sourceJ] = "S";
		board[boardRow / 2][boardCol / 2] = "G";

		for (int pitN = 0; pitN < 2; pitN++) {
			tempPos = getElementsPosition(board);
			board[tempPos.row][tempPos.col] = "P";
		}
		for (int wumN = 0; wumN < 1; wumN++) {
			tempPos = getElementsPosition(board);
			board[tempPos.row][tempPos.col] = "W";
		}

		int rn = new Random().nextInt(2);

		if (rn == 0) {
			board[boardRow - 1 - 2][0] = "P";
			board[boardRow - 1][2] = "W";
		} else {
			board[boardRow - 1 - 2][0] = "W";
			board[boardRow - 1][2] = "P";
		}
		printBoard(board);

		board = setBGS(board);

		printBoard(board);

		return board;
	}

	private String[][] setBGS(String board[][]) {
		for (int ii = 0; ii < boardRow; ii++) {
			for (int jj = 0; jj < boardRow; jj++) {
				String cellCH = "N";
				if (board[ii][jj].equals("P")) {
					cellCH = "B";
				} else if (board[ii][jj].equals("W")) {
					cellCH = "S";
				} 
				for (int k = 0; k < 4; k++) {
					int tx = ii + fx[k];
					int ty = jj + fy[k];

					if (isValidCell(tx, ty) && !cellCH.equals("N")
							&& !(board[tx][ty].equals("G") || board[tx][ty].equals("W") || board[tx][ty].equals("P"))) {

						if (board[tx][ty].equals("E"))
							board[tx][ty] = "";
						if(!board[tx][ty].contains(cellCH))
							board[tx][ty] += cellCH;
					}
				}
			}
		}
		return board;
	}

	private Position getElementsPosition(String board[][]) {
		while (true) {
			int i = new Random().nextInt(boardRow);
			int j = new Random().nextInt(boardCol);

			if (!board[i][j].equals("E"))
				continue;
			if (i == sourceI && j == sourceJ)
				continue;
			if (i == sourceI - 1 && j == sourceJ)
				continue;
			if (i == sourceI && j == sourceJ + 1)
				continue;
			if (i == boardRow - 1 && j == boardRow - 1 - 2)
				continue;
			if (i == boardRow - 1 - 2 && j == 0)
				continue;
			if (i == boardRow - 2 && j == 1)
				continue;

			return new Position(i, j);
		}
	}

	private boolean isValidCell(int tx, int ty) {
		if (tx >= boardRow || tx < 0)
			return false;
		if (ty >= boardCol || ty < 0)
			return false;
		return true;
	}

}
