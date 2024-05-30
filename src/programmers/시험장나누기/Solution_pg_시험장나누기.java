package programmers.시험장나누기;

import java.util.*;

public class Solution_pg_시험장나누기 {

    int n, root, cutCnt;
    int[] parents;

    // 시험장이 n개 있다면, 시험장의 고유 번호는 0부터 n-1까지 부여됩니다.
    public int solution(int k, int[] num, int[][] links) {
        int answer = 0;
        // 각 노드들의 부모를 가리키는 배열
        n = num.length;
        parents = new int[n];
        Arrays.fill(parents, -1);

        int start = 0;
        int end = 0;
        for (int i = 0; i < n; i++) {
            int left = links[i][0];
            if (left != -1) parents[left] = i;
            int right = links[i][1];
            if (right != -1) parents[right] = i;
            start = Math.max(start, num[i]);
            end += num[i];
        }

        for (int i = 0; i < n; i++) {
            if (parents[i] == -1) {
                root = i;
                break;
            }
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            // mid 인원수를 최대로 하는 그룹으로 나눴을 때 k개 이하의 그룹이 생성이 가능하지 못하다면
/*
            if (!canDivide(mid, k, num, links)) {
                start = mid + 1;
            } else {
//                answer = mid;
                end = mid - 1;
            }
*/
            if (canDivide(mid, k, num, links)) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
            // 위에서 주석을 해제하고 start를 반환하든, end 위에 answer를 주석 처리하든 다 정답이다.
        }
        return start;
    }

    private boolean canDivide(int mid, int k, int[] num, int[][] links) {
        cutCnt = 0;
        divide(mid, root, num, links);
        return cutCnt <= k - 1;
    }

    // 현재노드에서 자식 노드를 자른다고 생각하자.
    private int divide(int mid, int idx, int[] num, int[][] links) {
        int left = 0, right = 0;
        int leftIdx = links[idx][0];
        int rightIdx = links[idx][1];
        if (leftIdx != -1) left = divide(mid, leftIdx, num, links);
        if (rightIdx != -1) right = divide(mid, rightIdx, num, links);

        // 어느쪽도 안 잘라도 되는 경우
        if (num[idx] + left + right <= mid) {
            return num[idx] + left + right;
        }

        // 한 쪽만 자르면 해결되는 경우
        if (num[idx] + Math.min(left, right) <= mid) {
            cutCnt++;
            return num[idx] + Math.min(left, right);
        }

        // 양 쪽 다 잘라야하는 경우
        cutCnt += 2;
        return num[idx];
    }
}

