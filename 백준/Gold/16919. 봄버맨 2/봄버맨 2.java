import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		StringBuilder sb = new StringBuilder();
		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		ArrayDeque<int[]> emptyPos = new ArrayDeque<>();
		int[][] statusMap = new int[R][C];
		for (int i = 0; i < R; i++) {
			String s = br.readLine();
			for (int j = 0; j < C; j++) {
				if (s.charAt(j) == '.') {
					// 빈 칸인 경우
					statusMap[i][j] = 0;
					emptyPos.add(new int[] {i, j});
				} else {
					// 폭탄인 경우
					statusMap[i][j] = 2;
				}
			}
		}
		if (N >= 2) {
			N %= 4;
			if (N < 2) N += 4;
		}
		int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
		boolean[][] v;
		for (int time = 2; time < N + 1; time++) {
			
			v = new boolean[R][C];
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < C; j++) {
					v[i][j] = true;
					if (statusMap[i][j] != 0) {
						statusMap[i][j]--;
						if (statusMap[i][j] == 0) {
							emptyPos.offer(new int[] {i, j});
							for (int d = 0; d < 4; d++) {
								int ni = i + di[d];
								int nj = j + dj[d];
								if (ni < 0 || ni > R - 1 || nj < 0 || nj > C - 1 || statusMap[ni][nj] == 0 || (!v[ni][nj] && statusMap[ni][nj] == 1)) continue;
								statusMap[ni][nj] = 0;
								emptyPos.offer(new int[] {ni, nj});
							}
						}
					}
				}
			}
			if (time % 2 == 0) {
				while (!emptyPos.isEmpty()) {
					int[] cur = emptyPos.poll();
					statusMap[cur[0]][cur[1]] = 3;
				}
			}
		}
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (statusMap[i][j] == 0) sb.append(".");
				else sb.append("O");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}