package baekjoon.좋다;

import java.io.*;
import java.util.*;

public class Solution_bj_1253_좋다 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> list = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(list);
        Set<Integer> set = new HashSet<>();

        int answer = 0;
        for (int i = 0; i < N; i++) {
            int target = list.get(i);
            if (set.contains(target)) {
                answer++;
                continue;
            }

            int left = 0;
            int right = N - 1;
            while (left < right) {
                if (left == i) {
                    left++;
                    continue;
                }
                if (right == i) {
                    right--;
                    continue;
                }
                int sum = list.get(left) + list.get(right);
                if (target == sum) {
                    answer++;
                    set.add(target);
                    break;
                }
                if (sum > target) {
                    right--;
                }  else {
                    left++;
                }
            }
        }

        System.out.println(answer);
        br.close();
    }
}
