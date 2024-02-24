package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_bj_17472_다리만들기2_서울_20반_우경찬 {

	static int N, M;
	static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1}, parents;
	static int[][] map, g;
	static List<int[]> islandPos;
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
		
		if (a < b) parents[bRoot] = aRoot;
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
		g = new int[islandId-1][islandId-1];
		for (int i = 0; i < islandId - 1; i++) {
			v = new boolean[N][M];
			q = new ArrayDeque<>();
			int startI = islandPos.get(i)[0];
			int startJ = islandPos.get(i)[1];
			int id = i + 1;
			v[startI][startJ] = true;
		
			for (int d = 0; d < 4; d++) {
				int ni = startI + di[d];
				int nj = startJ + dj[d];
				if (ni < 0 || ni >= N || nj < 0 || nj >= M || v[ni][nj]) continue;
				v[ni][nj] = true;
				q.offer(new int[] {ni, nj, 0, d}); // 좌표들, 0의 개수, 바다일 경우 방향 인덱스
			}

			while (!q.isEmpty()) {
				int[] cur = q.poll();
				int ci = cur[0];
				int cj = cur[1];
				if (!v[ci][cj]) {
					v[ci][cj] = true;
					if (map[ci][cj] == id) {
						for (int d = 0; d < 4; d++) {
							int ni = ci + di[d];
							int nj = cj + dj[d];
							if (ni < 0 || ni >= N || nj < 0 || nj >= M || v[ni][nj]) continue;
							v[ni][nj] = true;
							q.offer(new int[] {ni, nj, 0, d});
						}
					} else if (map[ci][cj] == 0) {
						int d = cur[3];
						int ni = ci + di[d];
						int nj = cj + dj[d];
						
					} else {
						
					}
				}
			}
		}
		br.close();
	}

}
