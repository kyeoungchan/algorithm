package swexpertacademy.brickbroking;

import java.util.*;
import java.io.*;

public class Solution_d9_5656_벽돌깨기_서울_20반_우경찬2 {
	
	static int N, W, H, map[][], tempMap[][], ANS, dropPos[], di[] = {-1, 0, 1, 0}, dj[] = {0, 1, 0, -1}, total, tempANS, top[];
	static boolean[][] v;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d9_5656.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			map = new int[H][W];
			ANS = Integer.MAX_VALUE;
			total = 0;
			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < W; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if (map[i][j] != 0) total++;
				}
			}
			dropPos = new int[N];
			prim(0);
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static void prim(int cnt) {
		if (cnt == N) {
			play();
			return;
		}
		
		for (int i = 0; i < W; i++) {
			dropPos[cnt] = i;
			prim(cnt + 1);
		}
	}
	
	static void play() {
		tempMap = new int[H][W];
		tempANS = total;
		top = new int[W];
		boolean[] hasTop = new boolean[W];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				tempMap[i][j] = map[i][j];
				if (tempMap[i][j] != 0 && !hasTop[j]) {
					top[j] = i;
					hasTop[j] = true;
				}
			}
		}
		for (int dp = 0; dp < N; dp++) {
			int j = dropPos[dp];
			int i = top[j];
			if (tempMap[i][j] == 0) continue;
			v = new boolean[H][W];
			v[i][j] = true;
			
//			pop(i, j, true);
			popByQ(i, j);
			drop();
		}
		ANS = Math.min(tempANS, ANS);
	}

	static void popByQ(int i, int j) {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		q.offer(new int[]{i, j, tempMap[i][j], 0});
		tempMap[i][j] = 0;
		tempANS--;
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int dStart;
			if (cur[3] == 0) dStart = 1;
			else dStart = 0;
			for (int d = dStart; d < 4; d++) {
				for (int k = 1; k < cur[2]; k++) {
					int ni = cur[0] + di[d] * k;
					int nj = cur[1] + dj[d] * k;
					if (ni < 0 || ni > H - 1 || nj < 0 || nj > W - 1 || tempMap[ni][nj] == 0/* || v[ni][nj]*/) continue;
					int v = tempMap[ni][nj];
					tempMap[ni][nj] = 0;
					tempANS--;
					q.offer(new int[]{ni, nj, v, 1});
				}
			}
		}
	}

	static void drop() {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		for (int j = 0; j < W; j++) {
			for (int i = H - 1; i >= top[j]; i--) {
				if (tempMap[i][j] != 0) {
					q.offer(tempMap[i][j]);
					tempMap[i][j] = 0;
				}
			}
			int idx = H - 1;
			while (!q.isEmpty()) {
				int cur = q.poll();
//				if (cur == 0) continue;
				top[j] = idx;
				tempMap[idx--][j] = cur;
			}
			if (tempMap[top[j]][j] == 0) {
				top[j] = H - 1;
			}
		}
	}
	
/*
	static void pop(int i, int j, boolean start) {
		int dStart;
		if (start) dStart = 1;
		else dStart = 0;
		for (int d = dStart; d < 4; d++) {
			for (int k = 1; k < tempMap[i][j]; k++) {
				int ni = i + di[d] * k;
				int nj = j + dj[d] * k;
				if (ni < 0 || ni > H - 1 || nj < 0 || nj > W - 1 || tempMap[ni][nj] == 0 || v[ni][nj]) continue;
				v[ni][nj] = true;
				pop(ni, nj, false);
			}
		}
		tempMap[i][j] = 0;
		tempANS--;
	}
*/
}
 