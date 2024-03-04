package baekjoon.laddermaipulate;

import java.util.*;
import java.io.*;

public class Solution_bj_15684_사다리조작_서울_20반_우경찬 {

	static int result, R, N, M;
	static boolean[][] graph;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		graph = new boolean[N + 1][M + 1]; // 각 점마다 오른쪽의 선과 연결되어있으면 true
		for (int i = 0; i < H; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph[a][b] = true;
		}

		result = -1;
		for (int i = 0; i < 4; i++) {
			R = i;
			comb(0);
			if (result != -1) {
				break;
			}
		}
		System.out.println(result);
		br.close();
	}

	static void comb(int cnt) {
		if (cnt == R) {
			if (allNumberAll()) {
				result = R;
			}
			return;
		}

		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j < M; j++) {
				if (canSet(i, j)) {
					graph[i][j] = true;
					comb(cnt + 1);
					if (result == R)
						return;
					graph[i][j] = false;
				}
			}
		}
	}

	static boolean allNumberAll() {
		for (int j = 1; j < M + 1; j++) {
			int nj = j;
			for (int i = 1; i < N + 1; i++) {
				if (graph[i][nj]) {
					nj++;
				} else if (graph[i][nj - 1]) {
					nj--;
				}
				if (i == N && nj != j) {
					// 마지막 행까지 왔는데 본연의 열이랑 다르면 false 반환
					return false;
				}
			}
		}
		return true;
	}

	static boolean canSet(int i, int j) {
		// 현재 점을 기준으로 오른쪽으로 가로선을 놓을 수 있는지 판별하는 메서드
		if (j == M)
			return false;
		return !graph[i][j] && !graph[i][j - 1];
	}

}
