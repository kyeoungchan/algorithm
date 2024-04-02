import java.util.*;
import java.io.*;

public class Solution {
	
	static int D, W, K, film[][], tempFilm[][], ANS;

	public static void main(String[] args) throws Exception {
		//System.setIn(new FileInputStream("res/input_d9_2112.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			film = new int[D][W];
			for (int i = 0; i < D; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < W; j++) {
					film[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			ANS = -1;
			for (int total = 0; total < D; total++) {
				int[] dropRow = new int[total];
				select(0, 0, total, dropRow);
				if (ANS != -1) {
					break;
				}
			}
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static void select(int cnt, int start, int total, int[] dropRow) {
		if (ANS != -1) return;
		if (cnt == total) {
			boolean[] isA = new boolean[total];
			dropAOrB(0, dropRow, isA, total);
			return;
		}
		
		for (int i = start; i < D; i++) {
			dropRow[cnt] = i;
			select(cnt + 1, i + 1, total, dropRow);
		}
	}

	static void dropAOrB(int cnt, int[] dropRow, boolean[] isA, int total) {
		if (ANS != -1) return;
		if (cnt == total) {
			tempFilm = new int[D][W];
			for (int i = 0; i < D; i++) {
				for (int j = 0; j < W; j++) {
					tempFilm[i][j] = film[i][j];
				}
			}
			
			for (int i = 0; i < total; i++) {
				int row = dropRow[i];
				if (isA[i]) {
					for (int j = 0; j < W; j++) {
						tempFilm[row][j] = 0;
					}
				} else {
					for (int j = 0; j < W; j++) {
						tempFilm[row][j] = 1;
					}
				}
			}
			
			if (passed()) ANS = total;
			return;
		}
		
		isA[cnt] = true;
		dropAOrB(cnt + 1, dropRow, isA, total);
		isA[cnt] = false;
		dropAOrB(cnt + 1, dropRow, isA, total);
	}
	
	static boolean passed() {
		for (int j = 0; j < W; j++) {
			boolean onePass = false;
			check: for (int i = 0; i < D - K + 1; i++) {
				int value = tempFilm[i][j];
//				System.out.println("i: "+i + " j:"+j+" start!");
				for (int k = i + 1; k < i + K; k++) {
					if (tempFilm[k][j] != value) continue check;
				}
				onePass = true;
//				System.out.println("i:"+ i + " j:"+ j +" pass!");
//				System.out.println();
				break;
			}
			if (!onePass) return false;
		}
		return true;
	}
	
}