import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 0~999999 사이의 수로 표현되는 암호문
 * 암호문을 N개로 모아 암호문 뭉치
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc < 11; tc++) {
            int N = Integer.parseInt(br.readLine());
            List<Integer> list = new ArrayList<>(N);
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++)
                list.add(Integer.parseInt(st.nextToken()));
            int M = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            int x = -1, y = -1;
            for (int i = 0; i < M; i++) {
                char command = st.nextToken().charAt(0);
                switch (command) {
                    case 'I':
                        x = Integer.parseInt(st.nextToken());
                        y = Integer.parseInt(st.nextToken());
                        for (int j = 0; j < y; j++, x++)
                            list.add(x, Integer.parseInt(st.nextToken()));
                        break;
                    case 'D':
                        x = Integer.parseInt(st.nextToken());
                        y = Integer.parseInt(st.nextToken());
                        for (int j = 0; j < y; j++)
                            list.remove(x);
                        break;
                    case 'A':
                        y = Integer.parseInt(st.nextToken());
                        for (int j = 0; j < y; j++)
                            list.add(Integer.parseInt(st.nextToken()));
                        break;
                }
            }
            sb.append("#").append(tc).append(" ");
            for (int i = 0; i < 10; i++) {
                sb.append(list.get(i)).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}