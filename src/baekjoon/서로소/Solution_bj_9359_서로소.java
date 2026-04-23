package baekjoon.서로소;

import java.io.*;
import java.util.*;

public class Solution_bj_9359_서로소 {
    static List<Long> primes = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            long A = Long.parseLong(st.nextToken());
            long B = Long.parseLong(st.nextToken());
            long N = Long.parseLong(st.nextToken());

            primes.clear();
            findPrimeFactors(N);

            long ans = count(B) - count(A - 1);
            sb.append("Case #").append(tc).append(": ").append(ans).append("\n");
        }
        System.out.print(sb);
        br.close();
    }

    // N의 서로 다른 소인수들을 primes에 저장
    static void findPrimeFactors(long n) {
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                primes.add(i);
                while (n % i == 0) n /= i;
            }
        }
        if (n > 1) primes.add(n);
    }

    // [1, x] 범위에서 N과 서로소인 수의 개수 (포함-배제 원리)
    static long count(long x) {
        int m = primes.size();
        long result = 0;
        for (int mask = 0; mask < (1 << m); mask++) {
            long prod = 1;
            int bits = Integer.bitCount(mask);
            for (int i = 0; i < m; i++) {
                if ((mask >> i & 1) == 1) {
                    prod *= primes.get(i);
                }
            }
            if (bits % 2 == 0) {
                result += x / prod;
            } else {
                result -= x / prod;
            }
        }
        return result;
    }
}