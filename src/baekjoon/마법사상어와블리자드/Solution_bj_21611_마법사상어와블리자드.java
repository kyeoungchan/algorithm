package baekjoon.마법사상어와블리자드;

import java.util.*;
import java.io.*;

/**
 * 격자 크기: N x N
 * - N: 홀수
 * - 격자의 범위: 1,1 ~ N,N
 * 마법사 상어의 위치: (N+1)/2, (N+1)/2 => N이 5라면 (3,3)
 * 일부 칸과 칸 사이에는 벽이 세워져있다.
 * 연속하는 구슬이란: 같은 번호를 가진 구슬이 번호가 연속하는 칸에 있는 경우의 구슬
 * 상어가 마법을 시전 -> d방향으로 거리가 2인 구슬ㄷ르 파괴
 * - d: 1,2,3,4(상 하 좌 우)
 * - 거리가 2라면 두 칸의 구슬들을 파괴한다.
 * - A의 번호보다 하나 작은 칸이 빈칸 이라면, A에 있는 구슬은 그 빈 칸으로 이동한다.
 * 폭발하는 구슬
 * - 4개 이상 연속하는 구슬이 있을 때 발생한다.
 * - 더이상 폭발하는 구슬이 없을 때까지 반복된다.
 * 변화하는 구슬
 * - 연속하는 구슬은 하나의 그룹이 된다.
 * - 하나의 그룹은 두 개의 구슬이 되는데, 구슬의 개수를 담는 구슬 A와 그룹을 이루던 번호를 담는 구슬 B로 변화한다.
 * - 그렇게 2개의 구슬이 칸에 차례대로 들어간다.
 * - 구슬이 칸의 수보다 많아 칸에 들어가지 못하는 경우, 그러한 구슬은 사라진다.
 * 출력 정보: 폭발한 1번 구슬의 개수 + 2 * 폭발한 2번 구슬의 개수 + 3 * 폭발한 3번 구슬의 개수
 * <p>
 * 일단 각 번호별로 좌표 정보를 구해야한다.
 * - N/2만큼 양 옆에 깊이가 있다.
 * - 처음에는 1이나 9, 25 등을 찍고
 * - 처음 포함N-1만큼 아래로 내려간다.
 * - 왼쪽으로 방향 꺾고 N-1만큼,(오른쪽)
 * - 왼쪽으로 방향 꺾고 N-1만큼,(위쪽)
 * - 왼쪽으로 방향 꺾고 N-1만큼(왼쪽)
 * - N^2 크기의 이차원 배열을 만들어서 각 번호가 어떤 좌표에 있는지를 담는다.
 * 그리고 좌표에서 해당 번호의 구슬을 지울 수도 있게 번호 정보를 갖는 지도도 만든다.
 * 빈칸 미뤄지는 거: 큐 사용 -> 빈 칸 정보를 큐에 담았다가 구슬이 나오면 큐에서 나오는 빈칸에 구슬을 넣고, 원래 구슬이 있던 자리는 다시 빈칸으로 만들기
 * - 빈칸 미루면서 연속하는 길이 계속 세다가 4개 이상이면 시작 번호와 개수를 담은 데이터를 다른 큐에 담아두기
 * - 빈칸 미루는 작업이 끝나면 다른 큐에서 꺼내면서 폭발
 *  - 그리고 빈칸 미뤄지는 작업 수행
 * 제일 마지막 구슬이 있는 번호 관리하기
 * 변화하는 구슬
 * - 그리고 다시 처음부터 연속하는 구슬의 수를 세면서 큐에 이번에는 개수와 번호를 담아두기
 * - 세아리면서 구슬 지도는 지워주고
 * - 끝나면 큐에서 꺼내면서 구슬 지도에 변화한 구슬을 담아준다.
 * - 여기에서 NxN-1 번호를 넘어가는지 잘 체크해줘야한다.
 */
public class Solution_bj_21611_마법사상어와블리자드 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] beads = new int[N + 1][N + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j < N + 1; j++) {
                beads[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int[][] spell = new int[M][2];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            spell[i][0] = Integer.parseInt(st.nextToken());
            spell[i][1] = Integer.parseInt(st.nextToken());
        }
        int si = (N + 1) / 2;
        int sj = (N + 1) / 2;

        br.close();
    }

}
