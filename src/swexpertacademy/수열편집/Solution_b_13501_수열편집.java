package swexpertacademy.수열편집;

import java.util.*;
import java.io.*;

/**
 * N개의 10억이하(int형) 자연수로 이루어진 수열
 * M번의 편집을 거쳐야한다.
 * 인덱스 L의 데이터를 출력하는 프로그램
 */
public class Solution_b_13501_수열편집 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_b_13501.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            ArrayList<Integer> list = new ArrayList<>();
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine(), " ");

            for (int i = 0; i < N; i++)
                list.add(Integer.parseInt(st.nextToken()));

            int idx = -1, data = -1;
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                char command = st.nextToken().charAt(0);
                switch (command) {
                    case 'I':
                        idx = Integer.parseInt(st.nextToken());
                        data = Integer.parseInt(st.nextToken());
                        list.add(idx, data);
                        break;
                    case 'D':
                        idx = Integer.parseInt(st.nextToken());
                        list.remove(idx);
                        break;
                    case 'C':
                        idx = Integer.parseInt(st.nextToken());
                        data = Integer.parseInt(st.nextToken());
                        list.set(idx, data);
                        break;
                }
            }
            sb.append("#").append(tc).append(" ");
            if (list.size() > L) sb.append(list.get(L));
            else sb.append(-1);
            sb.append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
