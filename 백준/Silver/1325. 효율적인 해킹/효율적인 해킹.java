import java.io.*;
import java.util.*;

public class Main {

    static int N;
    static int[] lengthInfo, visited;
    static List<Integer>[] hackable;
    static ArrayDeque<Integer> q;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        hackable = new ArrayList[N + 1];
        lengthInfo = new int[N + 1];
        visited = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            hackable[i] = new ArrayList<>();
            lengthInfo[i] = -1;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            hackable[b].add(a);
        }


        int length = 0;
        StringBuilder sb = new StringBuilder();
        q = new ArrayDeque<>();
        for (int i = 1; i < N + 1; i++) {
            setLengthInfo(i);
            if (length == lengthInfo[i]) {
                sb.append(i).append(" ");
            } else if (length < lengthInfo[i]) {
                sb.delete(0, sb.length());
                sb.append(i).append(" ");
                length = lengthInfo[i];
            }
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static void setLengthInfo(int i) {
        lengthInfo[i]++; // 이제 0
        q.offer(i);
        visited[i] = i;
        while (!q.isEmpty()) {
            int cur = q.poll();
            lengthInfo[i]++;
            for (int next: hackable[cur]) {
                if (visited[next] == i) continue;
                q.offer(next);
                visited[next] = i;
            }
        }
    }
}