package swexpertacademy.보급로;

import java.util.*;
import java.io.*;

/**
 * 결국 S에서 G까지 가는 경로 중에서 가장 각 원소의 합들이 최소가 되는 것을 구하는 문제다.
 * 다익스트라로 풀 수도 있겠지만 일단은 DFS로 접근을 먼저 해보자.
 * 지도의 크기가 최대 100x100이므로 DFS는 시간초과가 날 수 있다. 메모이제이션 기법을 써보면 어떨까?
 * 각 위치마다 도달하는 데 걸리는 최소 거리를 계속 계산하는 것이다.
 */
public class Solution_d4_1249_보급로_서울_20반_우경찬2 {
	
	static int N, map[][], dp[][], di[] = {-1, 0, 1, 0}, dj[] = {0, 1, 0, -1};
	static boolean[][][] v;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d4_1249.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			dp = new int[N][N];
			for (int i = 0; i < N; i++) {
				String s = br.readLine();
				for (int j = 0; j < N; j++) {
					map[i][j] = s.charAt(j) - '0';
					dp[i][j] = Integer.MAX_VALUE;
				}
			}
		
			v = new boolean[N][N][4];
			for(int i = 0; i < 4; i++) v[0][0][i] = true;
			dp[0][0] = 0;
			ArrayDeque<int[]> q = new ArrayDeque<>();
			q.offer(new int[] {0, 0});
			
			while (!q.isEmpty()) {
				int[] cur = q.poll();
				int ci = cur[0];
				int cj = cur[1];
				for (int d = 0; d < 4; d++) {
					int ni = ci + di[d];
					int nj = cj + dj[d];
					if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1 || v[ni][nj][d]) continue;
					v[ni][nj][d] = true;
					if (map[ni][nj] + dp[ci][cj] < dp[ni][nj]) {
						for (int nd = 0; nd < 4; nd++) {
							if ((d + 2) % 4 == nd) continue;
							int nni = ni + di[nd];
							int nnj = nj + dj[nd];
							if (nni < 0 || nni > N - 1 || nnj < 0 || nnj > N - 1) continue;
							v[nni][nnj][nd] = false;
						}
						dp[ni][nj] = map[ni][nj] + dp[ci][cj];
					}
					q.offer(new int[] {ni, nj});
				}
			}
			sb.append("#").append(tc).append(" ").append(dp[N-1][N-1]).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}
