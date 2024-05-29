import java.util.*;
import java.io.*;

/**
 * 준규가 갖고 있는 동전: 총 N 종류
 * 각각의 동전은 무제한
 * 가치의 합 K로 만들려고 한다.
 * 필요한 동전 개수의 최소값
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(br.readLine());
        }

        int answer = 0;
        for (int i = N - 1; i > -1; i--) {
            int price = A[i];
            if (price <= K) {
                answer += K / price;
                K %= price;
            }
        }

        System.out.println(answer);
        br.close();
    }
}