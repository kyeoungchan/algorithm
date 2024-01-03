package swexpertacademy.prelearning.advanced.towngroupscount;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();
        for (int test_case = 1; test_case <= T; test_case++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int[] parent = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                parent[i] = i;
            }
            for (int i = 0; i < m; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                unionParent(parent, a, b);
            }
            System.out.printf("#%d %d\n", test_case, countGroup(parent));
        }

    }

    private static int findParent(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = findParent(parent, parent[x]);
        }
        return parent[x];
    }

    private static void unionParent(int[] parent, int a, int b) {
        a = findParent(parent, a);
        b = findParent(parent, b);
        if (a < b)
            parent[b] = a;
        else
            parent[a] = b;
    }

    private static int countGroup(int[] parent) {
        return Arrays.stream(parent)
                .map(p -> findParent(parent, p))
                .boxed()
                .collect(Collectors.toSet())
                .size() - 1;
    }

}
