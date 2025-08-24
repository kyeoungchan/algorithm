import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            int[] A  = new int[N];
            int[] lis = new int[N];
            int idx = 0;
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }
            lis[idx++] = A[0];
            for (int i = 1; i < N; i++) {
                if (A[i] > lis[idx - 1]) {
                    lis[idx++] = A[i];
                } else {
                    int left = 0;
                    int right = idx - 1;
                    while (left <= right) {
                        int mid = left + (right - left) / 2;
                        if (A[i] > lis[mid]) {
                            left = mid + 1;
                        } else {
                            right = mid - 1;
                        }
                    }
                    lis[left] = A[i];
                }
            }
            System.out.println(idx);
        }
    }
}