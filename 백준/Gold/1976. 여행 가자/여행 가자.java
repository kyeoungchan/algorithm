import java.io.*;
import java.util.*;

/**
 * 도시의 개수: N
 * 길이 있을 수도, 없을 수도 있다.
 * 여행 경로가 가능한지?
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine()); // 여행 계획에 속한 도시의 수
        int[] parents = new int[N];
        for (int i = 0; i < N; i++) parents[i] = i;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                int connect = Integer.parseInt(st.nextToken());
                if (connect == 1)
                    unionParents(i, j, parents);
            }
        }
        st = new StringTokenizer(br.readLine(), " ");
        int start = Integer.parseInt(st.nextToken()) - 1;
        start = findParent(start, parents);
        String answer = "YES";
        for (int i = 1; i < M; i++) {
            int next = Integer.parseInt(st.nextToken()) - 1; // 도시의 번호를 -1씩 해서 인덱스에 접근할 수 있게 해주자.
            if (start != findParent(next, parents)) {
                answer = "NO";
                break;
            }
        }
        System.out.println(answer);
    }

    static int findParent(int x, int[] parents) {
        if (x == parents[x]) return x;
        return parents[x] = findParent(parents[x], parents);
    }

    static void unionParents(int a, int b, int[] parents) {
        a = findParent(a, parents);
        b = findParent(b, parents);
        if (a < b) parents[b] = a;
        else parents[a] = b;
    }
}