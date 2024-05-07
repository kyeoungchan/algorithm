import java.io.*;
import java.util.*;

/**
 * 보관 후 하루가 지나면 익은 토마토들의 인접한 곳에 익지 않은 토마토들은 영향을 받는다.
 * 인접: 위 아래, 왼쪽, 오른쪽 앞, 뒤
 * 대각선 방향은 영향 X
 * 스스로 익는 경우도 X
 * 토마토들이 며칠이 지나면 다 익는지 최소 일수를 알고 싶다.
 * 상자의 일부 칸에는 토마토가 들어있지 않을 수 있다.
 */
public class Main {
	
	static int[] dh = {-1, 0, 0, 0, 0, 1}, dr= {0, -1, 0, 1, 0, 0}, dc = {0, 0, 1, 0, -1, 0};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());
		int[][][] board = new int[H][N][M];
		ArrayDeque<int[]> q = new ArrayDeque<>();
		
		int unrippen = 0;
		for (int h = 0; h < H; h++) {
			for (int r = 0; r < N; r++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int c = 0; c < M; c++) {
					board[h][r][c] = Integer.parseInt(st.nextToken());
					//  정수 1은 익은 토마토, 정수 0 은 익지 않은 토마토, 정수 -1은 토마토가 들어있지 않은 칸
					if (board[h][r][c] == 1) q.offer(new int[] {h,r,c});
					else if (board[h][r][c] == 0) unrippen++;
				}
			}
		}
		
		if (q.isEmpty()) {
			// 익은 토마토가 없는 경우. 안 익은 토마토들로만 구성돼있으므로 토마토가 모두 익지 못하는 상황이다.
			System.out.println(-1);
		} else if (unrippen == 0) {
			// 안 익은 토마토가 없는 경우. 모든 토마토들이 이미 다 익은 상태다.
			System.out.println(0);
		} else {
			int day = 0;
//			boolean v[][][] = new boolean[H][N][M];
			while (!q.isEmpty() && unrippen > 0) {
				day++;
				int qSize = q.size();
				for (int i = 0; i < qSize; i++) {
					int[] cur = q.poll();
//					v[cur[0]][cur[1]][cur[2]] = true;
					for (int d = 0; d < 6; d++) {
						int nh = cur[0] + dh[d];
						int nr = cur[1] + dr[d];
						int nc = cur[2] + dc[d];
						if (nh < 0 || nh > H - 1 || nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || board[nh][nr][nc] != 0) continue;
						board[nh][nr][nc] = 1;
						unrippen--;
						q.offer(new int[] {nh, nr, nc});
					}
				}
			}
			if (unrippen == 0) System.out.println(day);
			else System.out.println(-1);
		}
		br.close();
	}

}