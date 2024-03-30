package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d6_1263_사람네트워크2_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d6_1263.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int[][] dist = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int temp = Integer.parseInt(st.nextToken());
					if (temp == 1) dist[i][j] = 1;
					else if (i == j) dist[i][j] = 0;
					else dist[i][j] = (int)Math.pow(10, 6);
				}
			}
			int min = Integer.MAX_VALUE;
			for (int k = 0; k < N; k++) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
					}
				}
			}
			for (int i = 0; i < N; i++) {
				int temp = 0;
				for (int j = 0; j < N; j++) {
					temp += dist[i][j];
				}
				min = Math.min(min, temp);
			}
			sb.append("#").append(tc).append(" ").append(min).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}
