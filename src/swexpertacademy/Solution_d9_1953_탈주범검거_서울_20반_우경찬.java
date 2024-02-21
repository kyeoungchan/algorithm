package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_1953_탈주범검거_서울_20반_우경찬 {

	static int N, M, R, C, L;
	static int[] di = { -1, 0, 1, 0 }, dj = { 0, 1, 0, -1 };
	static int[][] map;
	static boolean[][] visited;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d9_1953.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			map = new int[N][M];
			visited = new boolean[N][M];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			int ans = countPos();
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static int countPos() {
		int cnt = 0;
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visited[R][C] = true;
		q.offer(new int[] { R, C, 1 });
		cnt++;

		while (!q.isEmpty()) {
			int[] p = q.poll();
			if (map[p[0]][p[1]] == 1) {
				for (int d = 0; d < 4; d++) {
					int ni = p[0] + di[d];
					int nj = p[1] + dj[d];
					int nTime = p[2] + 1;
					if (ni < 0 || ni >= N || nj < 0 || nj >= M || visited[ni][nj] || !convertable(d, ni, nj))
						continue;
					if (nTime <= L) {
						visited[ni][nj] = true;
						cnt++;
						if (nTime < L)
							q.offer(new int[] { ni, nj, nTime });
					}
				}
			} else if (map[p[0]][p[1]] == 2) {
				for (int d : new int[] {0, 2}) {
					int ni = p[0] + di[d];
					int nj = p[1] + dj[d];
					int nTime = p[2] + 1;
					if (ni < 0 || ni >= N || nj < 0 || nj >= M || visited[ni][nj] || !convertable(d, ni, nj))
						continue;
					visited[ni][nj] = true;
					if (nTime <= L) {
						cnt++;
						if (nTime < L)
							q.offer(new int[] { ni, nj, nTime });
					}
				}
			} else if (map[p[0]][p[1]] == 3) {
				for (int d : new int[] {1, 3}) {
					int ni = p[0] + di[d];
					int nj = p[1] + dj[d];
					int nTime = p[2] + 1;
					if (ni < 0 || ni >= N || nj < 0 || nj >= M || visited[ni][nj] || !convertable(d, ni, nj))
						continue;
					visited[ni][nj] = true;
					if (nTime <= L) {
						cnt++;
						if (nTime < L)
							q.offer(new int[] { ni, nj, nTime });
					}
				}
			} else if (map[p[0]][p[1]] == 4) {
				for (int d: new int[] {0, 1}) {
					int ni = p[0] + di[d];
					int nj = p[1] + dj[d];
					int nTime = p[2] + 1;
					if (ni < 0 || ni >= N || nj < 0 || nj >= M || visited[ni][nj] || !convertable(d, ni, nj))
						continue;
					visited[ni][nj] = true;
					if (nTime <= L) {
						cnt++;
						if (nTime < L)
							q.offer(new int[] { ni, nj, nTime });
					}
				}

			} else if (map[p[0]][p[1]] == 5) {
				for (int d : new int[] {1, 2}) {
					int ni = p[0] + di[d];
					int nj = p[1] + dj[d];
					int nTime = p[2] + 1;
					if (ni < 0 || ni >= N || nj < 0 || nj >= M || visited[ni][nj] || !convertable(d, ni, nj))
						continue;
					visited[ni][nj] = true;
					if (nTime <= L) {
						cnt++;
						if (nTime < L)
							q.offer(new int[] { ni, nj, nTime });
					}
				}

			} else if (map[p[0]][p[1]] == 6) {
				for (int d : new int[] {2, 3}) {
					int ni = p[0] + di[d];
					int nj = p[1] + dj[d];
					int nTime = p[2] + 1;
					if (ni < 0 || ni >= N || nj < 0 || nj >= M || visited[ni][nj] || !convertable(d, ni, nj))
						continue;
					visited[ni][nj] = true;
					if (nTime <= L) {
						cnt++;
						if (nTime < L)
							q.offer(new int[] { ni, nj, nTime });
					}
				}
			} else if (map[p[0]][p[1]] == 7) {
				for (int d : new int[] {0, 3}) {
					int ni = p[0] + di[d];
					int nj = p[1] + dj[d];
					int nTime = p[2] + 1;
					if (ni < 0 || ni >= N || nj < 0 || nj >= M || visited[ni][nj] || !convertable(d, ni, nj))
						continue;
					visited[ni][nj] = true;
					if (nTime <= L) {
						cnt++;
						if (nTime < L)
							q.offer(new int[] { ni, nj, nTime });
					}
				}
			}

		}

		return cnt;
	}

	static boolean convertable(int d, int ni, int nj) {
		int tunnel = map[ni][nj];
		if (d == 0) { // 상
			return tunnel == 1 || tunnel == 2 || tunnel == 5 || tunnel == 6;
		} else if (d == 1) { // 우
			return tunnel == 1 || tunnel == 3 || tunnel == 6 || tunnel == 7;
		} else if (d == 2) { // 하
			return tunnel == 1 || tunnel == 2 || tunnel == 4 || tunnel == 7;
		} else { // 좌
			return tunnel == 1 || tunnel == 3 || tunnel == 4 || tunnel == 5;
		}
	}

}
