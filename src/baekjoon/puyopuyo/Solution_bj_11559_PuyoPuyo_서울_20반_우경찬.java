package baekjoon.puyopuyo;

import java.util.*;
import java.io.*;

public class Solution_bj_11559_PuyoPuyo_서울_20반_우경찬 {
	
	static int[] di = new int[] {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
	static char[][] map;
	static boolean hasPuyo;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		map = new char[12][6];
		for (int i = 0; i < 12; i++) {
			String s = br.readLine();
			for (int j = 0; j < 6; j++) {
				map[i][j] = s.charAt(j);
			}
		}
		int result = 0;
		hasPuyo = true;
		while (hasPuyo) {
			hasPuyo = false;
			ArrayDeque<int[]> q = new ArrayDeque<>();
			boolean[][] v = new boolean[12][6];
			List<int[]> puyoPos = new ArrayList<>();
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 6; j++) {
					if (map[i][j] == '.' || v[i][j]) continue;
					v[i][j] = true;
					q.offer(new int[] {i, j});
					int cnt = 1;
					char c = map[i][j];
					while (!q.isEmpty()) {
						int[] cur = q.poll();
						for (int d = 0; d < 4; d++) {
							int ni = cur[0] + di[d];
							int nj = cur[1] + dj[d];
							if (ni < 0 || ni > 11 || nj < 0 || nj > 5 || map[ni][nj] != c || v[ni][nj]) continue;
							v[ni][nj] = true;
							q.offer(new int[] {ni, nj});
							cnt++;
						}
					}
					if (cnt > 3)  {
						if (!hasPuyo)
							result++;
						hasPuyo = true;
						puyoPos.add(new int[] {i, j});
					}
				}
			}
			q.clear();
			for (int[] pos : puyoPos) {
				char c = map[pos[0]][pos[1]];
				map[pos[0]][pos[1]] = '.';
				q.offer(new int[] {pos[0], pos[1]});
				while(!q.isEmpty()) {
					int[] cur = q.poll();
					for (int d = 0; d < 4; d++) {
						int ni = cur[0] + di[d];
						int nj = cur[1] + dj[d];
						if (ni < 0 || ni > 11 || nj < 0 || nj > 5 || map[ni][nj] != c) continue;
						q.offer(new int[] {ni, nj});
						map[ni][nj] = '.';
					}
				}
			}
			drop();
		}
		System.out.println(result);
		br.close();
	}
	
	static void drop() {
		for (int j = 0; j < 6; j++) {
			for (int i = 11; i > 0; i--) {
				if (map[i][j] == '.' && map[i-1][j] != '.') {
					char temp = map[i][j];
					map[i][j] = map[i-1][j];
					map[i-1][j] = temp;
					i = 12;
				}
			}
		}
	}
	
	
}
