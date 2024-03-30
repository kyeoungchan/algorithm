package swexpertacademy;

import java.util.*;
import java.io.*;

/**
 * 지도가 변하는 상황인데 bfs로 풀 수 있으려나
 * 아 시간에 따라서 지도를 갱신시켜주자.
 */
public class Solution_d5_7793_오나의여신님_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d5_7793.txt"));
		int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			char[][] map = new char[N][M];
			int sI = 0;
			int sJ = 0;
			for (int i = 0; i < N; i++) {
				String s = br.readLine();
				for (int j = 0; j < M; j++) {
					map[i][j] = s.charAt(j);
					if (map[i][j] == 'S') {
						sI = i;
						sJ = j;
					}
				}
			}
			ArrayDeque<int[]> q = new ArrayDeque<>();
			q.offer(new int[] {sI, sJ});
			int time = 0;
			boolean[][] v = new boolean[N][M];
			boolean[][] newDevil;
			boolean hasReached = false;
			end: while (!q.isEmpty()) {
				time++;
				
				newDevil = new boolean[N][M];
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < M; j++) {
						if (map[i][j] == '*' && !newDevil[i][j]) {
							for (int d = 0; d < 4; d++) {
								int ni = i + di[d];
								int nj = j + dj[d];
								if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1 || map[ni][nj] == '*' || map[ni][nj] == 'X' || map[ni][nj] == 'D') {
									continue;
								}
								map[ni][nj] = '*';
								newDevil[ni][nj] = true;
							}
						}
					}
				}

				int qSize = q.size();
				for (int i = 0; i < qSize; i++) {
					int[] cur = q.poll();
					for (int d = 0; d < 4; d++) {
						int ni = cur[0] + di[d];
						int nj = cur[1] + dj[d];
						if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1 || map[ni][nj] == 'X' || map[ni][nj] == '*' || v[ni][nj]) continue;
						v[ni][nj] = true;
						if (map[ni][nj] == 'D') {
							hasReached = true;
							break end;
						}
						q.offer(new int[] {ni, nj});
					}
				}
			}
			sb.append("#").append(tc).append(" ");
			if (!hasReached) sb.append("GAME OVER");
			else sb.append(time);
			sb.append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}
