package swexpertacademy.treasurechest;

import java.util.*;
import java.io.*;

/**
 * 각변에 16진수 숫자가 적혀있는 보물상자가 있다.
 * 상자의 뚜껑은 시계 방향으로 돌리고, 숫자가 시계방향으로 한 칸씩 회전한다.
 * 각 변에는 일정한 개수의 숫자가 있고, 시계방향 순으로 높은 자리 숫자에 해당한다.
 * 자물쇠의 비밀번호: 보물 상자에 적힌 숫자로 만들 수 있는 모든 수 중, K번째로 큰 수를 10진수로 만든 수
 * N개의 수자가 이벽으로 주어졌을 때 비밀번호를 출력하자.
 * 단, 동일한 수가 중복으로 생성될 수 있고, 크기 순서는 중복을 빼고 세야함을 주의하자.
 *
 * 입력으로 주어진 숫자들을 일렬로 세우고, 첫 번째 변의 숫자만 마지막 하나의 숫자를 제외하고 일렬의 맨 끝에 갖다붙인다.
 * 그리고 한 변의 길이(N/4)만큼씩 subString을 이용해서 TreeSet에 집어넣는다.
 * 마지막에 K만큼 firstPoll()을 사용해서 K번째 값을 꺼내서 출력한다.
 */
public class Solution_d9_5658_보물상자비밀번호2 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5658.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int oneSide = N / 4;
            String s = br.readLine();
            s += s.substring(0, oneSide - 1);
            TreeSet<String> ts = new TreeSet<>(Comparator.reverseOrder());
            for (int startIdx = 0; startIdx < N; startIdx++) {
                ts.add(s.substring(startIdx, startIdx + oneSide));
//                ts.add(Integer.parseInt(s.substring(startIdx, startIdx + oneSide), 16));
            }
            int idx = 0;
            int answer = 0;
            for (String pwd : ts) {
                if (idx++ == K - 1) {
//                    answer = pwd;
                    answer = Integer.parseInt(pwd, 16);
                    break;
                }
            }
//            List<String> tempList = new ArrayList<>(ts);
//            answer = Integer.parseInt(tempList.get(K - 1), 16);
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
