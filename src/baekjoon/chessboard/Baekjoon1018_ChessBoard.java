package baekjoon.chessboard;

import java.util.*;
import java.io.*;

public class Baekjoon1018_ChessBoard {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		char[][] initBoard = new char[n][m];
		for (int i = 0; i < n; i++) {
			String rowStr = br.readLine();
			for (int j = 0; j < m; j++) {
				initBoard[i][j] = rowStr.charAt(j);
			}
		}
		char[][] startB = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					startB[i][j] = 'B';
				} else {
					startB[i][j] = 'W';
				}
			}
		}
		int result = n * m; // 전체를 바꿔야하는 최악의 경우
		for (int i = 0; i < n - 8 + 1; i++) {
			for (int j = 0; j < m - 8 + 1; j++) {
				int count = countPaint(i, j, initBoard, startB);
				result = count < result ? count : result;
			}
		}
		System.out.println(result);
		br.close();
	}
	
		static int countPaint(int startI, int startJ, char[][] initBoard, char[][] startB) {
		
		int resultB = 0;
		for (int i = startI; i < startI + 8; i++) {
			for (int j = startJ; j < startJ + 8; j++) {
				int ni = i - startI;
				int nj = j - startJ;
				if (startB[ni][nj] != initBoard[i][j])
					resultB++;
			}
		}
//		return resultB < resultW ? resultB : resultW;
		return resultB < 32 ? resultB : 64 - resultB;
	}

}
