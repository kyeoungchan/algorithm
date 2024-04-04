package baekjoon.termproject;

import java.util.*;
import java.io.*;

/**
 * 프로젝트 팀원 수에는 제한이 엇다.
 * 모든 학생들이 동일하게 한 팀에만 구성할 수도 있다.
 * 모든 학생들은 함께 하고 싶은 학생을 1명 골라야한다.(자기를 선택할 수도 있다.)
 * 모두가 한 팀이 되려면 1명밖에 없고 자기 자신을 고르거나, 서로가 서로를 고르는 순환 구조면 성립한다.
 * <p>
 * 어느 팀에도 속하지 않는 학생들의 수를 계산하는 프로그램
 * 일단 간선 정보를 다 받고, DFS로 자신이 오는 애면 해당 사이클은 모두 한 팀이다.
 * 그러면 visited를 int형으로 받고, 2가 되면 하나의 사이클로 인정 => boolean true
 * visited가 0인 애들은 다시 dfs로 돌기 1인 친구들의 갯수 구하기
 */
public class Solution_bj_9466_텀프로젝트 {
    static int answer;
    static boolean[] done, visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[] want = new int[N + 1];
            done = new boolean[N + 1];
            visited = new boolean[N + 1];
            st = new StringTokenizer(br.readLine(), " ");
            answer = N;
            for (int i = 1; i < N + 1; i++) {
                want[i] = Integer.parseInt(st.nextToken());
                if (want[i] == i) {
                    done[i] = true;
                    answer--;
                }
            }
            for (int i = 1; i < N + 1; i++) {
                if (done[i]) continue;
                dfs(i, want);
            }
            sb.append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void dfs(int studentNumber, int[] want) {
        if (done[studentNumber]) {
            return;
        }
        if (visited[studentNumber]) {
            done[studentNumber] = true;
            answer--;
        }
        visited[studentNumber] = true;
        dfs(want[studentNumber], want);
        done[studentNumber] = true;
        visited[studentNumber] = false;
    }

}
