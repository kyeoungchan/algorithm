import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[N];
        int[] increasingDp = new int[N]; // 오르막길 길이
        int[] decreasingDp = new int[N]; // 내리막길 길이
        int answer = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            increasingDp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    increasingDp[i] = Math.max(increasingDp[i], increasingDp[j] + 1);
                }
            }
        }

        for (int i = N - 1; i >= 0; i--) {
            decreasingDp[i] = 1;
            for (int j = N - 1; j > i; j--) {
                if (arr[i] > arr[j]) {
                    decreasingDp[i] = Math.max(decreasingDp[i], decreasingDp[j] + 1);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            answer = Math.max(answer, increasingDp[i] + decreasingDp[i] - 1);
        }
        System.out.println(answer);
        br.close();
    }
}
