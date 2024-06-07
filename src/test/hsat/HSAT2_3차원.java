package test.hsat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HSAT2_3차원 {
	static int N, T, ans;
	static int[][] grid, tmp;
	static int[][][] fromStart;
	
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
		grid = new int[N][N];fromStart = new int[2][N][N]; tmp = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		fromStart[0][0][0] = grid[0][0];
		for(int i = 1; i < N; i++)
			fromStart[0][i][0] = fromStart[0][i - 1][0] + grid[i][0];
		for(int j = 1; j < N; j++)
			fromStart[0][0][j] = grid[0][j] + fromStart[0][0][j - 1];
		for(int i = 1; i < N; i++)
			for(int j = 1; j < N; j++)
				fromStart[0][i][j] = Math.max(fromStart[0][i - 1][j], fromStart[0][i][j - 1]) + grid[i][j];
		
		label:for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				tmp[r][c] = 0;
				for(int  i = r + 1; i <= r + T; i++)
					if(isIn(i , c))
						tmp[i][c] = grid[i][c] + tmp[i - 1][c];
				for(int j = c + 1; j <= c + T; j++)
					if(isIn(r, j))
						tmp[r][j] = grid[r][j] + tmp[r][j - 1];
				
				for(int i = r + 1; i <= r + T; i++) 
					for(int j = c + 1; j <= c + T; j++) 
						if(isIn(i, j) && dist(r, c, i , j) <= T) 
							tmp[i][j] = grid[i][j] + Math.max(tmp[i - 1][j], tmp[i][j - 1]);
				
				int tmpMax = Integer.MIN_VALUE;
				for(int d = 0; d <= T; d++) {
					int arriveR = r + d;
					int arriveC = c + T - d;
					if(isIn(arriveR, arriveC))
						tmpMax = Math.max(tmpMax, tmp[arriveR][arriveC]);
				}
				int curAns = Integer.MIN_VALUE;
				
				//점화식
				if(tmpMax != Integer.MIN_VALUE) {
					curAns = fromStart[0][r][c] + tmpMax + grid[r][c];
				}
				
				if(isIn(r - 1, c))
					curAns = Math.max(curAns, fromStart[1][r - 1][c] + grid[r][c]);
				
				if(isIn(r, c - 1))
					curAns = Math.max(curAns, fromStart[1][r][c - 1] + grid[r][c]);
				
				//리프가 완전 불가능 할때는 리프를 고려하지 안는다.
				if(r == 0 && c == 0 && curAns == Integer.MIN_VALUE) {
					fromStart[1][N - 1][N - 1] = Integer.MIN_VALUE;
					break label;
				}
					
				
				fromStart[1][r][c] = curAns;
							
			}
		}
		
		
		System.out.println(Math.max(fromStart[0][N - 1][N - 1], fromStart[1][N - 1][N - 1]));
		br.close();
	}

}
