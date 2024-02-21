package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_2105_디저트카페_서울_20반_우경찬 {

	static int N, maxPath, maxKind;
	static int[][] map;
	static int[] di = { 1, 1, -1, -1 }, dj = { -1, 1, 1, -1 };
	static boolean[] visitedCafe;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d9_2105.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			maxPath = -1;
			maxKind = 0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					maxKind = Math.max(maxKind, map[i][j]);
				}
			}

			for (int i = 0; i < N - 2; i++) {
				for (int j = 1; j < N - 1; j++) {
					searchCafe(i, j);
				}
			}
			sb.append("#").append(tc).append(" ").append(maxPath).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static void searchCafe(int i, int j) {
		int maxLeft = j; // 최대로 왼쪽 방향으로 갈 수 있는 수
		int maxRight = N - j - 1; // 최대로 오른쪽 방향으로 갈 수 있는 수

		int[][] sizes = new int[maxLeft * maxRight][2];
		int idx = 0;
		for (int ii = 0; ii < maxLeft; ii++) {
			for(int jj = 0; jj < maxRight; jj++) {
				sizes[idx][0] = maxLeft - ii;
				sizes[idx][1] = maxRight - jj;
				idx++;
			}
		}
		Arrays.sort(sizes, Comparator.comparingInt((int[] s)-> -(s[0] + s[1])));
		for (int s = 0; s < sizes.length; s++) {
			visitedCafe = new boolean[maxKind + 1];
			int tempLeft = sizes[s][0];
			int tempRight = sizes[s][1];
			int pi = i;
			int pj = j;
			boolean unValid = false;
			end: for (int d = 0; d < 4; d++) {
				if (d % 2 == 0) {
					for (int k = 0; k < tempLeft; k++) {
						int ni = pi + di[d];
						int nj = pj + dj[d];
						if (ni >= N || visitedCafe[map[ni][nj]]) {
							unValid = true;
							break end;
						}
						visitedCafe[map[ni][nj]] = true;
						pi = ni;
						pj = nj;
					}
				} else {
					for (int k = 0; k < tempRight; k++) {
						int ni = pi + di[d];
						int nj = pj + dj[d];
						if (ni >= N || visitedCafe[map[ni][nj]]) {
							unValid = true;
							break end;
						}
						visitedCafe[map[ni][nj]] = true;
						pi = ni;
						pj = nj;
					}
				}
			}
			if (!unValid) {
				maxPath = Math.max(maxPath, 2 * (tempLeft + tempRight));
				break;
			}
		}
	}

}
