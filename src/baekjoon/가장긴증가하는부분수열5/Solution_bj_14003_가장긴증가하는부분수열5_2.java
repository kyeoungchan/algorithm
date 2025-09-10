package baekjoon.가장긴증가하는부분수열5;

import java.io.*;
import java.util.*;

public class Solution_bj_14003_가장긴증가하는부분수열5_2 {

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            int[] A = new int[N];
            int[] lis = new int[N];
            int length = 0;
            int[] lengths = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }

            lis[length++] = A[0];
            lengths[0] = length;
            for (int i = 1; i < N; i++) {
                if (A[i] > lis[length - 1]) {
                    lis[length++] = A[i];
                    lengths[i] = length;
                } else if (A[i] < lis[length - 1]) {
                    int left = 0;
                    int right = length - 1;
                    while (left <= right) {
                        int mid = left + (right - left) / 2;
                        if (A[i] > lis[mid]) {
                            left = mid + 1;
                        } else {
                            right = mid - 1;
                        }
                    }
                    lis[left] = A[i];
                    lengths[i] = left + 1;
                }
            }

            sb.append(length).append("\n");

            ArrayDeque<Integer> stack = new ArrayDeque<>();

            // length가 0이었을 때에도 lengths[i]가 0인 값에 따라 A[i]가 추가될 수 있으므로 length > 0 조건도 같이 붙여야한다.
            for (int i = N - 1; i >= 0 && length > 0; i--) {
                if (lengths[i] == length) {
                    length--;
                    stack.push(A[i]);
                }
            }

            while (!stack.isEmpty()) {
                sb.append(stack.pop()).append(" ");
            }

            System.out.println(sb);
        }
    }
}
