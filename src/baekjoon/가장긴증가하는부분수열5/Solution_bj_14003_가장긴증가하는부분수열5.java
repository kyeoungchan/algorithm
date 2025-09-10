package baekjoon.가장긴증가하는부분수열5;

import java.io.*;
import java.util.*;

public class Solution_bj_14003_가장긴증가하는부분수열5 {

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int length = 0;
            int N = Integer.parseInt(br.readLine());
            int[] A = new int[N];
            int[] lisCandidate = new int[N]; // 정답은 아니므로 후보로 명칭
            int[] indices = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
                // 처음에는 0번 인덱스로 찍혀있으므로 -1로 셋팅을 하고 시작해주어야한다.
                indices[i] = -1;
            }

            indices[0] = length;
            lisCandidate[length++] = A[0];

            for (int i = 1; i < N; i++) {
                if (A[i] > lisCandidate[length - 1]) {
                    indices[i] = length;
                    lisCandidate[length++] = A[i];
                } else if (A[i] < lisCandidate[length - 1]) {
                    int left = 0;
                    int right = length - 1;
                    while (left <= right) {
                        int mid = left + (right - left) / 2;
                        if (A[i] > lisCandidate[mid]) {
                            left = mid + 1;
                        } else {
                            right = mid - 1;
                        }
                    }
                    indices[i] = left;
                    lisCandidate[left] = A[i];
                }
            }

            sb.append(length).append("\n");

            int idx = length - 1;
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            for (int i = N - 1; i >= 0 && idx >= 0; i--) {
                // idx가 -1일 때에도 부분 수열로 들어갈 수 있으므로 0까지 갔으면 반복문을 탈출시켜야한다.
                if (indices[i] == idx) {
                    stack.push(A[i]);
                    idx--;
                }
            }

            while (!stack.isEmpty()) {
                sb.append(stack.pop()).append(" ");
            }
            System.out.println(sb);
        }
    }
}
