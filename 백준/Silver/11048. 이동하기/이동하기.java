import java.util.*;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int[][] dp = new int[N][M];
		dp[0][0] = map[0][0];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				int up = 0;
				int diagonal = 0;
				int left = 0;
				if (i > 0) up = dp[i-1][j];
				if (i > 0 && j > 0) diagonal = dp[i-1][j-1];
				if (j > 0) left = dp[i][j-1];
				dp[i][j] = map[i][j] + Math.max(Math.max(up, diagonal), left);
			}
		}
		System.out.println(dp[N-1][M-1]);
		br.close();
	}

}