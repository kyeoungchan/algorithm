package baekjoon.효율적인해킹;

import java.io.*;
import java.util.*;

public class Solution_bj_1325_효율적인해킹 {

    static int N;
    static int[] lengthInfo;
    static Set<Integer>[] hackable;
    static List<Integer> answers;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        hackable = new Set[N + 1];
        answers = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (hackable[b] == null) hackable[b] = new HashSet<>();
            hackable[b].add(a);
        }

        lengthInfo = new int[N + 1];
        Arrays.fill(lengthInfo, -1);

        int length = 0;
        for (int i = 1; i < N + 1; i++) {
            if (lengthInfo[i] != -1) continue;
            setLengthInfo(i);
            if (length == lengthInfo[i]) {
                answers.add(i);
            } else if (length < lengthInfo[i]) {
                answers.clear();
                answers.add(i);
                length = lengthInfo[i];
            }
        }

        Collections.sort(answers);
        StringBuilder sb = new StringBuilder();
        for (int answer : answers) {
            sb.append(answer).append(" ");
        }
        System.out.println(sb.toString());
        br.close();
    }

    private static void setLengthInfo(int i) {
        lengthInfo[i]++;
        if (hackable[i] == null) return;
        for (int hack : hackable[i]) {
            if (lengthInfo[hack] == -1) setLengthInfo(hack);
            lengthInfo[i] += lengthInfo[hack];
            lengthInfo[i]++;
        }
    }
}
