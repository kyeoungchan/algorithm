package study;

import java.util.*;
import java.io.*;

/**
 * 1~p까지 산타들이 크리스마스 이브를 준비하는 중 루돌프가 반란을 일으킴. 루돌프를 잡아야한다.
 * 게임: N x N 격자
 * 최상단: 1,1
 * 게임은 M개의 턴
 * 루돌프와 산타들이 한 번씩 움직인다.
 * 루돌프가 먼저 움직이고, 1번부터 p번까지의 산타들이 순서대로 움직인다.
 * 산타들이 움직일 수 없는 조건
 * 1. 기절
 * 2. 격자밖으로 빠져나간 경우 => 게임에서 탈락함
 * 거리: 제곱수로 표현
 * 
 * 루돌프의 움직임: 가장 가까운 산타를 향해 1칸 돌진한다.(탈락하지 않은 산타로 선택)
 * 거리가 같은 산타가 여러명인 경우, r이 큰 산타가 먼저, 그다음 c가 큰 산타가 먼저
 * 루돌프는 8방향으로 돌진가능하다.
 * 
 * 산타의 움직임
 * 탈락한 산타는 움직일 수 없다.
 * - 루돌프에게 거리가 가장 가까워지는 방향으로 1칸 이동한다.
 * - 다른 산타가 있는 칸이나 게임판 밖으로는 움직이지 않는다.
 * - 움직일 수 있는 칸이 없다면 산타는 움직이지 않는다.
 * - 움직일 수 있는 칸이 있더라도 루돌프랑 가까워지지 않는다면 산타는 움직이지 않는다.
 * - 산타는 4방향으로 움직이고, 방향 우선순위는 상우하좌다.
 * 
 * 충돌
 * - 루돌프가 움직여서 충돌이 일어난 경우: C만큼 점수
 * 	- 루돌프가 이동한 방향으로 C칸만큼 밀려난다.
 * - 산타가 움직여서 충돌이 일어난 경우: D만큼 점수
 * 	- 자신이 이동한 반대 방향으로 D만큼 밀려난다. 
 * - 게임판 밖으로 나가면 산타는 게임에서 탈락
 * - 다른 산타를 만나면 상호작용
 * 
 * 상호작용
 * - 다른 산타가 있다면 그 산타는 1칸 해당 방향으로 밀려난다.
 * - 연쇄적으로 상호작용 가능
 * 
 * 기절
 * - 현재 k -> k+1은 기절 상태
 * - 기절한 산타는 움직일 수 없고, 기절 도중 충돌이나 상호작용으로 인해 밀려날 수는 있다.
 * - 기절한 산타도 돌진 대상이 될 수 있다.
 * 
 * 게임 종료
 * - M번의 턴이 모두 거치거나, P명의 산타가 모두 탈락하면 게임 종료
 * - 매턴 이후 아직 탈락하지 않은 산타들에게 1점씩 추가로 부여
 */
public class Solution_ct_루돌프의반란 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken()); // 1 ~ N
		int M = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int D = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine(), " ");
		int rr = Integer.parseInt(st.nextToken());
		int rc = Integer.parseInt(st.nextToken());
		List<Integer> santas = new ArrayList<>();
		int[][] map = new int[N + 1][N + 1];
		for (int i = 0; i < P; i++ ) {
			st = new StringTokenizer(br.readLine(), " ");
			int number = Integer.parseInt(st.nextToken()); // 산타 번호
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			map[r][c] = number;
			santas.add(r * N + c);
		}
		
		br.close();
	}

}
