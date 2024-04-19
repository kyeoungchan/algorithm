package codetree.코드트리메신저;

import java.util.*;
import java.io.*;

/**
 * 이진트리 => 모든 노드의 자식이 2개 이하
 * 사내 메신저의 채팅방: 0 ~ N번까지 총 N+1개의 채팅방
 *  - 회사의 메인 채팅방(0번)을 제외 각 채팅방은 부모 채팅방 존재
 *  - parents는 길이가 N이고, 0~N-1의 인덱스는 인덱스+1번 방의 부모를 의미한다.
 *  채팅방의 권한
 *  - c번 채팅방에서 메세지를 보내면 권한의 값만큼만 위로 올라가며 위로 보낸다.
 * 알림망 설정
 * - c번 채팅방의 On이면 off 반대면 반대로 바꿔준다.
 * - 알림방 설정이 off면 해당 번호 위로 올라가지 않는다.
 * - 아마 본인까지는 알림이 오기는 하나보다.
 * 권한세기: 변경가능하다.
 * 부모 채팅방 교환
 * 알림을 받을 수 있는 채팅방 수 조회
 * - c번 채팅방까지 알림이 도달할 수 있는 서로 다른 채팅방의 수를 출력한다.
 * - dfs를 통해 visited를 활용해서 cnt를 세어가며 0이 되기까지 target 채팅방에 도달하는지 판단한다.
 * - 도달하면 1 return
 * - 그리고 본인 거를 세면서 부모의 권한을 통해 부모도 같이 더해서 return
 */
public class Solution_ct_코드트리메신저 {

    static int[] parents, authority;
    static int[][] children;
    static boolean[] isOff;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        parents = new int[N + 1];
        children = new int[N + 1][2];
        authority = new int[N + 1];
        isOff = new boolean[N + 1];
        for (int cm = 0; cm < Q; cm++) {
            st = new StringTokenizer(br.readLine(), " ");
            int c = -1;
            int command = Integer.parseInt(st.nextToken());
            switch (command) {
                case 100:
                    for (int i = 1; i < N + 1; i++) {
                        int parent = Integer.parseInt(st.nextToken());
                        parents[i] = parent;
                        if (children[parent][0] == 0) {
                            children[parent][0] = i;
                        } else {
                            children[parent][1] = i;
                        }
                    }
                    for (int i = 1; i < N + 1; i++) {
                        authority[i] = Integer.parseInt(st.nextToken());
                    }
                    break;
                case 200:
                    c = Integer.parseInt(st.nextToken());
                    isOff[c] = !isOff[c];
                    break;
                case 300:
                    c = Integer.parseInt(st.nextToken());
                    int power = Integer.parseInt(st.nextToken());
                    authority[c] = power;
                    break;
                case 400:
                    int c1 = Integer.parseInt(st.nextToken());
                    int c2 = Integer.parseInt(st.nextToken());
                    int p1 = parents[c1];
                    int p2 = parents[c2];

                    int temp = parents[c1];
                    parents[c1] = parents[c2];
                    parents[c2] = temp;

                    if (children[p1][0] == c1) {
                        children[p1][0] = c2;
                    } else {
                        children[p1][1] = c2;
                    }

                    if (children[p2][0] == c2) {
                        children[p2][0] = c1;
                    } else {
                        children[p2][1] = c1;
                    }
                    break;
                case 500:
                    c = Integer.parseInt(st.nextToken());
                    int result = 0;
                    result += dfs(c, 0);
                    sb.append(result).append("\n");
                    break;
            }
//            debug(command, c);
        }
        System.out.println(sb.toString());
        br.close();
    }

    static int dfs(int num, int level) {
        int result = 0;
        for (int i = 0; i < 2; i++) {
            int c = children[num][i];
            if (c != 0 && !isOff[c]) {
                if (authority[c] > level) result++;
                result += dfs(c, level + 1);
            }
        }
        return result;
    }

    static void debug(int command, int c) {
        System.out.println(command + ", " + c + " 명령 결과");
        System.out.println("parents");
        System.out.println(Arrays.toString(parents));
        System.out.println("authority");
        System.out.println(Arrays.toString(authority));
        System.out.println("ifOff");
        System.out.println(Arrays.toString(isOff));
        System.out.println("children");
        for (int i = 1; i < children.length; i++) {
            System.out.println(i + ": " + Arrays.toString(children[i]));
        }
    }
}
