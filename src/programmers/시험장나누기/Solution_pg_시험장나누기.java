package programmers.시험장나누기;

import java.util.*;
import java.io.*;

public class Solution_pg_시험장나누기 {
    public static void main(String[] args) throws Exception {
        System.out.println(new Solution().solution(3, new int[] {12, 30, 1, 8, 8, 6, 20, 7, 5, 10, 4, 1}, new int[][] {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {8, 5}, {2, 10}, {3, 0}, {6, 1}, {11, -1}, {7, 4}, {-1, -1}, {-1, -1}}));
    }
}

// 시험장이 n개 있다면, 시험장의 고유 번호는 0부터 n-1까지 부여됩니다.
class Solution {

    public int solution(int k, int[] num, int[][] links) {
        int answer = 0;
        int[] sum = new int[num.length];
        Arrays.fill(sum, -1);
        int root = -1;
        int max = 0; // 1 ≤ k ≤ 10,000

        for (int i = 0; i < num.length; i++) {
            if (sum[i] == -1)
                getSum(i, sum, num, links);
            if (sum[i] > max) {
                max = sum[i];
                root = i;
            }
        }

        int start = 0;
        int end = 0;
        for (int n : num) {
            end += n;
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (!canDivide(mid, root, k, sum, links)) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
            System.out.println();
        }
        answer = start;
        return answer;
    }

    private void getSum(int idx, int[] sum, int[] num, int[][] links) {

        if (sum[idx] != -1) {
            return;
        }
        sum[idx] = num[idx];

        if (links[idx][0] != -1) {
            getSum(links[idx][0], sum, num, links);
            sum[idx] += sum[links[idx][0]];
        }
        if (links[idx][1] != -1) {
            getSum(links[idx][1], sum, num, links);
            sum[idx] += sum[links[idx][1]];
        }
    }

    static int groupCnt;
    // 인원이 가장 많은 그룹의 인원이 최소화되도록
    // mid 인원수보다 적거나 같은 인원수대로 그룹화를 했을 때 k개보다 그룹화가 적거나 같게 가능한지 판별한다.
    private boolean canDivide(int mid, int root, int k, int[] sum, int[][] links) {
        if (sum[root] <= mid) return true;

        int[] tempSum = new int[sum.length];
        for (int i = 0; i < sum.length; i++) {
            tempSum[i] = sum[i];
        }
        groupCnt = k - 1;
        return kEnough(mid, root, tempSum, links);
    }

    private boolean kEnough(int mid, int idx, int[] sum, int[][] links) {
        if (groupCnt <= 0) return false;
        if (sum[idx] <= mid) {
            groupCnt--;
            return true;
        }

        int left = links[idx][0];
        int right = links[idx][1];
        if (left == -1 && right == -1) {
            return false;
        } else if (right == -1) {
            // 오른쪽 자식만 더 없는 경우
            boolean leftResult = kEnough(mid, left, sum, links);
            if (leftResult) {
                sum[idx] -= sum[left];
            }
            return leftResult;
        } else if (left == -1) {
            // 왼쪽 자식만 더 없는 경우
            boolean rightResult = kEnough(mid, right, sum, links);
            if (rightResult)
                sum[idx] -= sum[right];
            return rightResult;
        } else {
            return false;
        }
    }
}