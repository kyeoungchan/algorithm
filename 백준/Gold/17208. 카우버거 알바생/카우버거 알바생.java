import java.io.*;
import java.util.*;

/**
 * 모든 주문은 각각 치즈버거 요구 개수와 감자튀김 요구 개수를 의미하는 2개의 정수로 이루어진다.
 * 어떤 주문을 처리하기 위해서는 치즈버거와 감자튀김을 정확히  그 주문에서 요구하는 만큼 써야한다.(이상X)
 * 주문이 들어온 순서와 관계없이 원하는 주문을 선태갷서 처리 가능
 * 한 번 처리한 주문은 사라진다.
 * 어떤 주문은 처리하지 못할 수 있다.
 * 최선의 방법으로 주문을 선택해 처리한다면 최대 몇 개의 주문을 처리할 수 있을까.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 주문의 수
        int M = Integer.parseInt(st.nextToken()); // 남은 치즈버거 개수
        int K = Integer.parseInt(st.nextToken()); // 남은 감자튀김 개수
        
        int[][] dp = new int[M + 1][K + 1];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int cheeseOrder = Integer.parseInt(st.nextToken());
            int friedOrder = Integer.parseInt(st.nextToken());

            for (int m = M; m >= cheeseOrder ; m--) {
                for (int k = K; k >= friedOrder; k--) {
                    dp[m][k] = Math.max(dp[m - cheeseOrder][k - friedOrder] + 1, dp[m][k]);
                }
            }
        }

        System.out.println(dp[M][K]);
        br.close();
    }
}