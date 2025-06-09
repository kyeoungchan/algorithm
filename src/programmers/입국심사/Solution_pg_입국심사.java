package programmers.입국심사;

import java.io.*;
import java.util.*;

/**
 * n명이 입ㄱ구심사를 위해 줄서고 있음
 * 심사관마다 심사하는 데 걸리는 시간이 다르다.
 * 처음에는 모든 심사대는 비어있다.
 * 한 심사대에서는 한명씩만 심사 가능
 * 가장 앞에 서 있는 사람은 비어있는 심사대로 가서 심사를 받을 수 있다.
 * 하지만 더 빨리 끝나는 심사대가 있으면 기다렸다가 그곳으로 가서 심사를 받을 수도 있다.
 * 모든 사람이 심사를 받는 데 걸리는 시간을 최소로 하고 싶다.
 * n: 입국심사를 기다리는 사람 수
 * 각 심사관이 한 명을 심사하는 데 걸리는 시간이 담긴 배열: times
 * 모든 사람이 심사를 받는데 걸리는 시간의 최솟값을 리턴
 * 1 <= n <= 1_000_000_000
 * 1 <= times[i] <= 1_000_000_000
 * 1 <= times.length <= 100_000
 */
public class Solution_pg_입국심사 {
    /*
    * m: 심사위원의 수*/
    private int n, m;
    private int[] times;

    public long solution(int n, int[] times) {
        this.n = n;
        this.times = times;
        this.m = times.length;

        Arrays.sort(times);
        return parametricSearch();
    }

    private long parametricSearch() {
        long left = times[0];
        long right = (long) times[m - 1] * (n / m + 1);
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (!availableTime(mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private boolean availableTime(long mid) {
        long waitingMembers = n; // n을 지역변수로 따로 할당
        for (int i = 0; i < m; i++) {
            waitingMembers -= mid / times[i];
            if (waitingMembers <= 0) return true;
        }
        return false;
    }
}
