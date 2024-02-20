package baekjoon.babyshark;

import java.util.*;
import java.io.*;

public class Solution_bj_16236_아기상어_서울_20반_우경찬 {
	static int N, time, sharkSize, ateFishCnt, totalFishCnt;
	static int[][] map;
	static List<int[]> fishInfo;
	static int[] sharkPos, di = { -1, 0, 0, 1 }, dj = { 0, -1, 1, 0 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		sharkPos = new int[2];
		fishInfo = new ArrayList<>();
		fishInfo.add(null);
		sharkSize = 2;
		ateFishCnt = 0;
		int idx = 1;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				int value = Integer.parseInt(st.nextToken());
//				map[i][j] = value;
				if (value == 9) {
					sharkPos[0] = i;
					sharkPos[1] = j;
//					map[i][j] = 0;
				} else if (value != 0) {
					fishInfo.add(new int[] { i, j, value });
					map[i][j] = idx++; // 물고기의 식별자를 map에다가 저장
					totalFishCnt++;
				}
			}
		}

		ArrayDeque<int[]> q;
		while (totalFishCnt > 0) {
//			System.out.println(totalFishCnt);
			q = new ArrayDeque<>();
			q.offer(new int[] { sharkPos[0], sharkPos[1], sharkSize, ateFishCnt, time }); // 마지막 인자는 먹을 물고기의 수

			boolean check = false;
			boolean[][] visited = new boolean[N][N];
			visited[sharkPos[0]][sharkPos[1]] = true;
			end: while (!q.isEmpty()) {
				int[] poll = q.poll();
				int pi = poll[0];
				int pj = poll[1];
				int size = poll[2];
				int eatFishCnt = poll[3];
				int pTime = poll[4];

				for (int d = 0; d < 4; d++) {
					int ni = pi + di[d];
					int nj = pj + dj[d];

					if (0 <= ni && ni < N && 0 <= nj && nj < N && !visited[ni][nj]) {
						visited[ni][nj] = true;
						if (map[ni][nj] == 0) {
							q.offer(new int[] { ni, nj, size, eatFishCnt, pTime + 1 });
						} else if (fishInfo.get(map[ni][nj])[2] > sharkSize) {
							continue;
						} else if (fishInfo.get(map[ni][nj])[2] == sharkSize) {
							q.offer(new int[] { ni, nj, size, eatFishCnt, pTime + 1 });
						} else {
							map[ni][nj] = 0;
							ateFishCnt++;
							sharkPos[0] = ni;
							sharkPos[1] = nj;
							time = pTime + 1;
							totalFishCnt--;
							if (ateFishCnt == sharkSize) {
								ateFishCnt = 0;
								sharkSize++;
							}
							check = true;
							break end;
						}
					}
				}
			}
			if (!check)
				break;
		}
		System.out.println(time);
		br.close();
	}

}
