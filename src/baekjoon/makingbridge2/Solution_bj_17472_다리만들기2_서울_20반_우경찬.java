package baekjoon.makingbridge2;

import java.util.*;
import java.io.*;

public class Solution_bj_17472_다리만들기2_서울_20반_우경찬 {

	static int N, M, INF = Integer.MAX_VALUE;
	static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1}, parents;
	static int[][] map, g;
	static List<int[]> islandPos, edges;
	static boolean[][] v;
	
	static void make() {
		parents = new int[islandPos.size() + 1];
		for (int i = 1; i < parents.length; i++)
			parents[i] = i;
	}
	
	static int find(int a) {
		if (a == parents[a]) return a;
		return parents[a] = find(parents[a]);
	}
	
	static boolean union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		
		if (aRoot == bRoot) return false;
		
		if (aRoot < bRoot) parents[bRoot] = aRoot;
		else parents[aRoot] = bRoot;
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				int v = Integer.parseInt(st.nextToken());
				if (v != 0) {
					v -= 2;
				}
				map[i][j] = v;
			}
		}
		
		islandPos = new ArrayList<>();
		boolean[][] checkIsland = new boolean[N][M];
		int islandId = 1;
		ArrayDeque<int[]> q;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 0 || checkIsland[i][j]) continue;
				q = new ArrayDeque<>();
				islandPos.add(new int[] {i, j});
				map[i][j] = islandId;
				checkIsland[i][j] = true;
				q.offer(new int[] {i, j});

				while (!q.isEmpty()) {
					int[] cur = q.poll();
					for (int d = 0; d < 4; d++) {
						int ni = cur[0] + di[d];
						int nj = cur[1] + dj[d];
						if (ni < 0 || ni >= N || nj < 0 || nj >= M || map[ni][nj] == 0 || checkIsland[ni][nj]) continue;
						map[ni][nj] = islandId;
						checkIsland[ni][nj] = true;
						q.offer(new int[] {ni, nj});
					}
				}
				islandId++;
			}
		}

		int islandCnt = islandId - 1;
		// 섬이 하나면 그 섬의 islandId = 1, 최종 islandId = 2인 상태로 반복문 탈출
		makeGraph(islandCnt);
		make();
		int result = 0;
		Collections.sort(edges, Comparator.comparingInt(edges -> edges[2]));
		for (int[] edge : edges) {
			if (union(edge[0], edge[1])) {
				result += edge[2];
			}
		}
		if (result == 0 || notAllIslandConnected()) result = -1;
		System.out.println(result);
		br.close();
	}

	static boolean notAllIslandConnected() {
		for (int i = 1; i < parents.length; i++) {
			if (find(i) != 1) {
				return true;
			}
		}
		return false;
	}

	private static void makeGraph(int islandCnt) {
		ArrayDeque<int[]> q;
		g = new int[islandCnt + 1][islandCnt + 1];
		for (int[] islandArr : g) {
			Arrays.fill(islandArr, INF);
		}

		for (int i = 0; i < islandCnt; i++) {
			// i == id - 1
			v = new boolean[N][M];
			q = new ArrayDeque<>();
			int startI = islandPos.get(i)[0];
			int startJ = islandPos.get(i)[1];
			int id = i + 1;
			v[startI][startJ] = true;

			for (int d = 0; d < 4; d++) {
				int ni = startI + di[d];
				int nj = startJ + dj[d];
				if (ni < 0 || ni >= N || nj < 0 || nj >= M) continue;
				q.offer(new int[] {ni, nj, 0, d}); // 좌표들, 0의 개수, 바다일 경우 방향 인덱스
			}

			while (!q.isEmpty()) {
				int[] cur = q.poll();
				int ci = cur[0];
				int cj = cur[1];
				if (!v[ci][cj]) {
					if (map[ci][cj] == id) {
						v[ci][cj] = true;
						for (int d = 0; d < 4; d++) {
							int ni = ci + di[d];
							int nj = cj + dj[d];
							if (ni < 0 || ni >= N || nj < 0 || nj >= M || v[ni][nj]) continue;
							q.offer(new int[] {ni, nj, 0, d});
						}
					} else if (map[ci][cj] == 0) {
						int d = cur[3];
						int ni = ci + di[d];
						int nj = cj + dj[d];
						if (ni < 0 || ni >= N || nj < 0 || nj >= M) continue;
						// 바다에서는 visited를 신경쓰지 않는다. 오히려 다른 경로를 방해할 수 있고, 사방 탐색이 아니라 일자 탐색이기 때문에 무한 루프가 생기지 않는다.
						q.offer(new int[]{ni, nj, cur[2] + 1, d});
					} else {
						int cost = cur[2];
						if (cost > 1) {
							g[i + 1][map[ci][cj]] = Math.min(cost, g[i + 1][map[ci][cj]]);
							g[map[ci][cj]][i + 1] = Math.min(cost, g[map[ci][cj]][i + 1]);
						}
					}
				}
			}
		}
		edges = new ArrayList<>();
		for (int ii = 1; ii < islandCnt + 1; ii++) {
			for (int j = ii + 1; j < islandCnt + 1; j++) {
				if (g[ii][j] != INF) {
					edges.add(new int[]{ii, j, g[ii][j]});
				}
			}
		}
	}

}
