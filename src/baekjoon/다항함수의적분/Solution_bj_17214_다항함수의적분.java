package baekjoon.다항함수의적분;

import java.io.*;
import java.util.*;

public class Solution_bj_17214_다항함수의적분 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            int numStartIdx = i;
            int numEndIdx;
            int num;
            if (s.charAt(numStartIdx) == 'x') {
                // 계수가 없는 경우
                numEndIdx = numStartIdx;
                num = 1;
            } else {
                numEndIdx = numStartIdx + 1;
                for (; numEndIdx < s.length(); numEndIdx++) {
                    char ch =  s.charAt(numEndIdx);
                    if (ch == 'x') {
                        break;
                    }
                }
                num = Integer.parseInt(s.substring(numStartIdx, numEndIdx));
            }

//            System.out.println("num = " + num);

            int xStartIdx = numEndIdx;
            int xEndIdx = xStartIdx;
            for (; xEndIdx < s.length(); xEndIdx++) {
                char ch =  s.charAt(xEndIdx);
                if (ch != 'x') {
                    break;
                }
            }
            int power = xEndIdx - xStartIdx;
//            System.out.println("power = " + power);
            int value = num / (power + 1);
            if (value != 1 && value != 0) {
                if (value == -1) {
                    sb.append("-");
                } else {
                    sb.append(value);
                }
            }
            for (int j = 0; j < power + 1 && num != 0; j++) {
                sb.append("x");
            }

            if (num != 0) {
                if (xEndIdx < s.length()) {
                    sb.append(s.charAt(xEndIdx));
                } else {
                    sb.append("+W");
                }
            } else {
                sb.append("W");
            }

            i = xEndIdx; // 이러면 위에 올라갔을 때 연산자를 건너뛴다.
        }

        System.out.println(sb.toString());
        br.close();
    }
}
