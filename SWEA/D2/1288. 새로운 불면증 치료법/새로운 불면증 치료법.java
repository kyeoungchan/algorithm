import java.io.*;
import java.util.*;

/**
 * N의 배수 번호인 양을 센다.
 * N, 2N, 3N... kN번 양을 센다.
 * 이전에 셌던 번호들의 각 자리수에서 0~9까지의 모든 숫자를 보는 것은 최소 몇 번 양을 센 시점일까?
 *
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            int nowN = 0;
            int bitStatus = 0;
            while (true) {
                nowN += N;
                bitStatus = updateBitStatus(nowN, bitStatus);
                if (bitStatus == (1 << 10) - 1) {
                    break;
                }
            }
            sb.append("#").append(tc).append(" ").append(nowN).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static int updateBitStatus(int nowN, int bitStatus) {
        while (nowN > 0) {
            int digit = nowN % 10;
            bitStatus = bitStatus | (1 << digit);
            nowN /= 10;
        }
        return bitStatus;
    }
}