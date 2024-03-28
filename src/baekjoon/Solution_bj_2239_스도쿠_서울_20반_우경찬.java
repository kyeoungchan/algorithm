package baekjoon;

import java.util.*;
import java.io.*;

public class Solution_bj_2239_스도쿠_서울_20반_우경찬 {

	static int[][] board;
	static List<int[]> zeroPos;
	static boolean completed;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		board = new int[9][9];
		zeroPos = new ArrayList<>();
		
		for (int i = 0; i < 9; i++) {
			String s = br.readLine();
			for (int j = 0; j < 9; j++) {
				board[i][j] = s.charAt(j) - '0';
				if (board[i][j] == 0) zeroPos.add(new int[] {i, j});
			}
		}
		
		completed = false;
		backtracking(0);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sb.append(String.valueOf(board[i][j]));
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static void backtracking(int cnt) {
		if (cnt == zeroPos.size()) {
			completed = true;
			return;
		}
		int[] cur = zeroPos.get(cnt);
		int i = cur[0];
		int j = cur[1];
		for (int num = 1; num < 10; num++) {
			board[i][j] = num;
			if (checkRow(i, j) && checkCol(i, j) && checkGroup(i, j)) {
				backtracking(cnt + 1);
			}
			if (completed) {
				return;
			}
		}
		board[i][j] = 0;
	}

	static boolean checkGroup(int i, int j) {
		int startI = i / 3 * 3;
		int startJ = j / 3 * 3;
		for (int ii = startI; ii < startI + 3; ii++) {
			for (int jj = startJ; jj < startJ + 3; jj++) {
				if (ii == i && jj == j) continue;
				if (board[ii][jj] == board[i][j]) return false;
			}
		}
		return true;
	}

	static boolean checkCol(int i, int j) {
		for (int ii = 0; ii < 9; ii++) {
			if (ii == i) continue;
			if (board[i][j] == board[ii][j]) return false;
		}
		return true;
	}

	static boolean checkRow(int i, int j) {
		for (int jj = 0; jj < 9; jj++) {
			if (jj == j) continue;
			if (board[i][j] == board[i][jj]) return false;
		}
		return true;
	}
	
	

}
