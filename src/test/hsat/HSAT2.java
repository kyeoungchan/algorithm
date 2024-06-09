package test.hsat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HSAT2 {
	static int N, T, ans;
	static int[][] grid,fromStart, toDest, tmp;
	
	static boolean isIn(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
	
	static int dist(int r1, int c1, int r2, int c2) {
		return Math.abs(r1 - r2) + Math.abs(c1 - c2);
	}
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/_240327/HSAT.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken()); T = Integer.parseInt(st.nextToken());
		grid = new int[N][N];fromStart = new int[N][N]; toDest = new int[N][N]; tmp = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		fromStart[0][0] = grid[0][0];
		for(int i = 1; i < N; i++)
			fromStart[i][0] = fromStart[i - 1][0] + grid[i][0];
		for(int j = 1; j < N; j++)
			fromStart[0][j] = grid[0][j] + fromStart[0][j - 1];
		for(int i = 1; i < N; i++)
			for(int j = 1; j < N; j++)
				fromStart[i][j] = Math.max(fromStart[i - 1][j], fromStart[i][j - 1]) + grid[i][j];
		
		ans = fromStart[N - 1][N - 1];
		
		toDest[N - 1][N - 1] = grid[N - 1][N - 1];
		for(int i = N - 2; i >= 0; i--)
			toDest[i][N - 1] = grid[i][N - 1] + toDest[i + 1][N - 1];
		for(int j = N - 2; j >= 0; j--)
			toDest[N - 1][j] = grid[N - 1][j] + toDest[N - 1][j + 1];
		for(int i = N - 2; i >= 0; i--)
			for(int j = N - 2; j >= 0; j--)
				toDest[i][j] = Math.max(toDest[i + 1][j], toDest[i][j + 1]) + grid[i][j];
		
		for(int r = 0; r < N; r++) {
			for(int c = 0;  c< N; c++) {
				//r, c가 리프의 도착점
				tmp[r][c] = 0;
				for(int i = r + 1; i <= r + T; i++)
					if(isIn(i, c))
						tmp[i][c] = grid[i][c] + tmp[i - 1][c];
				for(int j = c + 1; j <= c + T; j++)
					if(isIn(r, j))
						tmp[r][j] = grid[r][j] + tmp[r][j - 1];
				
				for(int i = r + 1; i <= r + T; i++) {
					for(int j = c + 1; j <= c + T; j++) {
						if(isIn(i, j) && dist(r, c, i, j) <= T) {
							tmp[i][j] = grid[i][j] + Math.max(tmp[i - 1][j], tmp[i][j - 1]);
						}
					}
				}
				
				int tmpMax = Integer.MIN_VALUE;
				for(int d = 0; d <= T; d++) {
					int arriveR = r + d;
					int arriveC = c + T - d;
					if(isIn(arriveR, arriveC)) {
						tmpMax = Math.max(tmpMax, tmp[arriveR][arriveC]);
					}
				}
				if(tmpMax == Integer.MIN_VALUE)
					continue;
				int tmpAns = fromStart[r][c] + tmpMax + toDest[r][c];
				ans = Math.max(ans, tmpAns);
			}
		}
		
		System.out.println(ans);
		br.close();
	}

}
