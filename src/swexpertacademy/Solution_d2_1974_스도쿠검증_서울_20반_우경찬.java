package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d2_1974_스도쿠검증_서울_20반_우경찬 {

	static int board[][];
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d4_1974.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			board = new int[9][9];
			for (int i = 0; i < 9; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < 9; j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			boolean validate = true;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (!(checkRow(i, j) && checkCol(i, j) && checkGroup(i, j))) {
						validate = false;
						break;
					}
				}
			}
			int ANS = 1;
			if (!validate) ANS = 0;
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static boolean checkGroup(int i, int j) {
		int startI = i / 3 * 3;
		int startJ = j / 3 * 3;
		for (int ii = startI; ii < startI + 3; ii++) {
			for (int jj = startJ; jj < startJ + 3; jj++) {
				if (i == ii && j == jj) continue;
				if (board[i][j] == board[ii][jj]) return false;
			}
		}
		return true;
	}

	static boolean checkCol(int i, int j) {
		for (int ii = 0; ii < 9; ii++) {
			if (ii == i) continue;
			if (board[ii][j] == board[i][j]) return false;
		}
		return true;
	}

	static boolean checkRow(int i, int j) {
		for (int jj = 0; jj < 9; jj++) {
			if (jj == j) continue;
			if (board[i][jj] == board[i][j]) return false;
		}
		return true;
	}

}
