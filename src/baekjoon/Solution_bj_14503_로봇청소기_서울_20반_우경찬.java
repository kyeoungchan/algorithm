package baekjoon;

import java.util.*;
import java.io.*;

public class Solution_bj_14503_로봇청소기_서울_20반_우경찬 {

	static int N, M, R, C, D, map[][], di[] = { -1, 0, 1, 0 }, dj[] = { 0, 1, 0, -1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int cnt = 0;
		clean: while (true) {
			if (map[R][C] == 0) {
				cnt++;
				map[R][C]--; // 청소한 구역은 -1로 표현
			} else {
				for (int d = 3; d > -1; d--) {
					int nd = (D + d) % 4;
					int nr = R + di[nd];
					int nc = C + dj[nd];
					if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || map[nr][nc] != 0) continue;
					D = nd;
					R = nr;
					C = nc;
					continue clean; // 청소가 가능하다면 해당 좌표와 방향으로 움직이고 다시 while문으로 간다.
				}
				// 여기에 왔다는 것은 청소에 실패했다는 뜻이다.
				int reverseD = (D + 2) % 4;
				int nr = R + di[reverseD];
				int nc = C + dj[reverseD];
				if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || map[nr][nc] == 1) break;
				R = nr;
				C = nc;
			}
		}
		System.out.println(cnt);
		br.close();
	}
	
}
