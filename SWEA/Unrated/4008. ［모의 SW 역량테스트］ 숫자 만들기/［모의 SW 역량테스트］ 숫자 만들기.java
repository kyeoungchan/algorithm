import java.io.*;
import java.util.*;

/**
 * 게임판에 적힌 숫자는 1~9
 */
public class Solution {
    static int N, min, max;
    static int[] operators, operands;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            // N개의 숫자(3 <= N <= 12)
            N = Integer.parseInt(br.readLine());
            operands = new int[N];
            operators = new int[4];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < 4; i++) {
                operators[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                operands[i] = Integer.parseInt(st.nextToken());
            }
            // 결과는 int형 범위 내다.
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            calculate(1, operands[0]);
            // 결과의 최댓값과 최솟값의 차이를 출력하라.
            sb.append("#").append(tc).append(" ").append(max - min).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void calculate(int cnt, int result) {
        if (cnt == N) {
            min = Math.min(result, min);
            max = Math.max(result, max);
            return;
        }

        // 연산자 카드의 개수의 총합은 항상 N - 1
        for (int i = 0; i < 4; i++) {
            if (operators[i] == 0) continue;
            operators[i]--;
            int nextResult = result;
            // 연산자의 우선순위는 단순히 왼쪽에서 오른쪽 차례
            // +, -, x, / 연산자 카드를 숫자 사이에 끼워넣는다.
            switch (i) {
                case 0:
                    nextResult += operands[cnt];
                    break;
                case 1:
                    nextResult -= operands[cnt];
                    break;
                case 2:
                    nextResult *= operands[cnt];
                    break;
                case 3:
                    // 나눗셈 시 소수점 이하는 버린다.
                    nextResult /= operands[cnt];
                    break;
            }
            calculate(cnt + 1, nextResult);
            operators[i]++;
        }
    }
}