package baekjoon;

import java.util.*;
import java.io.*;

public class Solution_bj_1600_말이되고픈원숭이_서울_20반_우경찬 {

	static int K, W, H, ANS;
	static int[][] map;
	static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1}, diH = {-2, -2, -1, 1, 2, 2, 1, -1}, djH = {-1, 1, 2, 2, 1, -1, -2, -2};
	static boolean[][][] v;
	
	// monkey 정보: 좌표, 말을 흉내낸 횟수, 움직인 횟수
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		K = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine(), " ");
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		map = new int[H][W];
		for (int i = 0; i < H; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < W; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		ANS = Integer.MAX_VALUE;
		v = new boolean[H][W][K + 1];
		v[0][0][0] = true;
		bfs();
		
		
		if (ANS == Integer.MAX_VALUE) ANS = -1;
		System.out.println(ANS);
		
		br.close();
	}
	
	static void bfs() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {0, 0, 0, 0});
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int mi = cur[0];
			int mj = cur[1];
			int copyHCnt = cur[2];
			int moveCnt = cur[3];
			if (mi == H - 1 && mj == W - 1) {
				ANS = Math.min(ANS, moveCnt);
				continue;
			}
			
			if (copyHCnt + 1 <= K) {
				for (int d = 0; d < 8; d++) {
					int ni = mi + diH[d];
					int nj = mj + djH[d];
					if (ni < 0 || ni >= H || nj < 0 || nj >= W || map[ni][nj] == 1 || v[ni][nj][copyHCnt + 1]) continue;
					v[ni][nj][copyHCnt + 1] = true;
					q.offer(new int[] {ni, nj, copyHCnt + 1, moveCnt + 1});
				}
			}
			
			for (int d = 0; d < 4; d++) {
				int ni = mi + di[d];
				int nj = mj + dj[d];
				if (ni < 0 || ni >= H || nj < 0 || nj >= W || map[ni][nj] == 1 || v[ni][nj][copyHCnt]) continue;
				v[ni][nj][copyHCnt] = true;
				q.offer(new int[] {ni, nj, copyHCnt, moveCnt + 1});
			}
			
		}
	}
	
}
