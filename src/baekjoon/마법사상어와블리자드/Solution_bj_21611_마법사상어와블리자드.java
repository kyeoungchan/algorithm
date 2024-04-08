package baekjoon.마법사상어와블리자드;

import java.util.*;
import java.io.*;

public class Solution_bj_21611_마법사상어와블리자드 {
	
	static int N, si, sj, endIdx;
	static int[] di = {-1, 1, 0, 0}, dj = {0, 0, -1, 1};
	static int[][] grid, numToGrid, gridToNum;
	static long[] bombCnt;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken()); // 격자의 크기
		si = N/2;
		sj = N/2;
		int M = Integer.parseInt(st.nextToken()); // 상어가 마법을 부린 횟수
		setGrids();
		grid = new int[N][N];
		endIdx = 0;
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int c = 0; c < N; c++) {
				grid[r][c] = Integer.parseInt(st.nextToken());
				if (grid[r][c] != 0 && gridToNum[r][c] > endIdx) endIdx = gridToNum[r][c]; 
			}
		}
		bombCnt = new long[4]; // 1~3까지 각각 폭발 횟수 카운트
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int d = Integer.parseInt(st.nextToken()) - 1; // 0, 1, 2, 3
			int s = Integer.parseInt(st.nextToken()); // 본인으로부터 거리. 2라면 본인을 제외하고 2칸 파괴
			spell(d, s);
			do {
				moveBeads();
			} while (bomb());
			changeBeads();
		}
		long result = 0;
		for (int i = 1; i < 4; i++) {
			result += i * bombCnt[i];
		}
		System.out.println(result);
		br.close();
	}
	
	static void changeBeads() {
//		System.out.println("change!");
		if (endIdx == 0) return;
		int[][] newGrid = new int[N][N];
		int newEndIdx = 0;
		int cnt = 1;
		for (int num = 1; num < endIdx; num++) {
			if (getBead(num) == getBead(num + 1)) {
				cnt++;
			} else {
				updateBead(++newEndIdx, cnt, newGrid);
				if (newEndIdx == N * N - 1) break;
				updateBead(++newEndIdx, getBead(num), newGrid);
				if (newEndIdx == N * N - 1) break;
				cnt = 1;
			}
		}
		if (newEndIdx < N * N - 1) {
			updateBead(++newEndIdx, cnt, newGrid);
			if (newEndIdx < N * N - 1)
				updateBead(++newEndIdx, getBead(endIdx), newGrid);
		}
		grid = newGrid;
		endIdx = newEndIdx;
//		debug();
	}

	static boolean bomb() {
		if (endIdx == 0) return false;
		int cnt = 1;
		boolean bombed = false;
		for (int num = 1; num < endIdx; num++) {
			if (getBead(num) == getBead(num + 1)) {
				cnt++;
			} else {
				if (cnt > 3) {
					bombed = true;
					bombCnt[getBead(num)] += cnt;
					for (int i = 0; i < cnt; i++) {
						updateBead(num - i, 0);
					}
				}
				cnt = 1;
			}
		}
		if (cnt > 3) {
			bombed = true;
			bombCnt[getBead(endIdx)] += cnt;
			for (int i = 0; i < cnt; i++) {
				updateBead(endIdx - i, 0);
			}
		}
//		debug();
		return bombed;
	}
	
	static void updateBead(int num, int bead, int[][] newGrid) {
		int r = numToGrid[num][0];
		int c = numToGrid[num][1];
		newGrid[r][c] = bead;
	}
	
	static void updateBead(int num, int bead) {
		int r = numToGrid[num][0];
		int c = numToGrid[num][1];
		grid[r][c] = bead;
	}
	
	static int getBead(int num) {
		int r = numToGrid[num][0];
		int c = numToGrid[num][1];
		return grid[r][c];
	}
	
	static void moveBeads() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		for (int num = 1; num <= endIdx; num++) {
			int i = numToGrid[num][0];
			int j = numToGrid[num][1];
			if (grid[i][j] == 0) {
				q.offer(new int[] {i, j});
			}
			else if (!q.isEmpty()){
				int[] empty = q.poll();
				int ei = empty[0];
				int ej = empty[1];
				grid[ei][ej] = grid[i][j]; // 빈 칸에 구슬을 넣고
				grid[i][j] = 0;
				q.offer(new int[] {i, j}); // 원래 구슬이 있던 자리는 이제 빈 칸이 되었으므로 큐에 넣는다.
			}
		}
		// 마지막 번호 업데이트
		if (!q.isEmpty()) {
			int[] empty = q.poll();
			int firstEmptyNum = gridToNum[empty[0]][empty[1]];
			endIdx = firstEmptyNum - 1;
		}
//		debug();
	}
	
	static void spell(int d, int s) {
		for (int k = 1; k < s + 1; k++) {
			int i = si + di[d] * k;
			int j = sj + dj[d] * k;
			// 폭발한 구슬의 개수지, 파괴된 구슬의 개수는 세지 않는다!
			grid[i][j] = 0;
		}
//		debug();
	}
	
	static void debug() {
		System.out.println("endIdx=" + endIdx);
		System.out.println("Grid");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	static void setGrids() {
		numToGrid = new int[N * N][2]; // 번호로 격자의 좌표 조회 정보
		gridToNum = new int[N][N];
		
		int idx = 0;
		numToGrid[idx][0] = si;
		numToGrid[idx][1] = sj;
		gridToNum[si][sj] = 0;
		
		for (int i = 0; i < N/2; i++) { // 1
			int r = N/2-1 - i; // 0
			int c = N/2-1 - i; // 0
			for (int j = 0; j < 2 * i + 2; j++) { // 2번 반복
				idx++;
				numToGrid[idx][0] = ++r;
				numToGrid[idx][1] = c; // [1] = 1,0 / [2] = 2,0
				gridToNum[r][c] = idx;
			}
			for (int j = 0; j < 2 * i + 2; j++) {
				idx++;
				numToGrid[idx][0] = r;
				numToGrid[idx][1] = ++c; // [3] = 2,1 / [4] = 2,2
				gridToNum[r][c] = idx;
			}
			for (int j = 0; j < 2 * i + 2; j++) {
				idx++;
				numToGrid[idx][0] = --r;
				numToGrid[idx][1] = c; // [5] =  1,2 / [6] = 0,2
				gridToNum[r][c] = idx;
			}
			for (int j = 0; j < 2 * i + 2; j++) {
				idx++;
				numToGrid[idx][0] = r; // [7] = 0,1 / [8] = 0,0
				numToGrid[idx][1] = --c;
				gridToNum[r][c] = idx;
			}
		}
	}
}
