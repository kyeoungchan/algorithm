package mincoding.공지방송;

import java.io.*;
import java.util.*;

/**
 * 사원의 출퇴근 시각이 주어진다.
 * 회사에서 M명 이상이 공지 방송을 들을 수 있도록 방송 시작 시각을 정하고자 한다.
 */
public class Solution_min_공지방송 {

    static class Employee implements Comparable<Employee> {
        int id, start, end;
        boolean isRemoved;

        public void setData(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
            isRemoved = false;
        }

        @Override
        public int compareTo(Employee o) {
            return start == o.start ? Integer.compare(end, o.end) : Integer.compare(start, o.start);
        }
    }

    private PriorityQueue<Employee> pq = new PriorityQueue<>();
    private Employee[] employees = new Employee[8_000]; // add는 최대 8,000번
    private Map<Integer, Integer> idMap = new HashMap<>();
    private int idx, totalCnt;

    public void init() {
        pq.clear();
        idx = -1;
        idMap.clear();
        totalCnt = 0;
    }

    /**
     * 만약에 이미 존재하는 ID라면, 추가하지 않고 기존의 출퇴근 시각을 변경한다.
     * 삭제되었던 사원이 다시 추가될 수 있다.
     * @param mId 사원 id
     * @param mStart 출근 시각
     * @param mEnd 퇴근 시각
     * @return 출퇴근 시각이 등록된 사원 수를 반환한다.
     * 1<= id <= 1,000,000,000
     * 0 <= mStart <= 85,400
     * mStart < mEnd < 86,400
     * 8,000이하 호출
     */
    public int add(int mId, int mStart, int mEnd) {
        if (idMap.containsKey(mId) && !employees[idMap.get(mId)].isRemoved) {
            // 만약에 이미 존재하는 ID라면, 출퇴근 시각 변경
            employees[idMap.get(mId)].isRemoved = true;
        } else {
            totalCnt++;
        }
        pq.offer(createEmployee(mId, mStart, mEnd));
        return totalCnt;
    }

    private Employee createEmployee(int mId, int mStart, int mEnd) {
        idx++;
        idMap.put(mId, idx);
        if (employees[idx] == null) employees[idx] = new Employee();
        employees[idx].setData(mId, mStart, mEnd);
        return employees[idx];
    }

    /**
     * 이미 삭제된 사원의 ID가 주어질 수 있다.
     * @param mId 사원 id
     * @return 출퇴근 시각이 등록된 사원 수를 반환한다.
     * 1<= id <= 1,000,000,000
     * 1,700 이하 호출
     */
    public int remove(int mId) {
        if (!idMap.containsKey(mId) || employees[idMap.get(mId)].isRemoved) return totalCnt;
        Employee employee = employees[idMap.get(mId)];
        employee.isRemoved = true;
        totalCnt--;
        return totalCnt;
    }

    /**
     * mDuration 기간의 공지 방송을 모두 들어야하는 최소 사원 수 M이 주어진다.
     * M명 이상이 방송을 들을 수 있는 가장 빠른 방송 시작 시간을 반환한다.
     * 가능한 방송 시작 시각이 없으면 -1을 반환한다.
     * 2 <= mDuration <= 3,600
     * 3 <= M <= 2,500
     * 호출 횟수: 300 이하
     */
    public int announce(int mDuration, int M) {
        if (totalCnt < M) return -1;
        int[] listenableCnt = new int[86_401 - mDuration + 1];
        PriorityQueue<Employee> temp = new PriorityQueue<>();
        int max = -1;
        while (!pq.isEmpty()) {
            Employee cur = pq.poll();
            if (cur.isRemoved) continue;
            if (cur.end - cur.start < mDuration) {
                temp.offer(cur);
                continue;
            }

            // 누적합 전략 사용
            listenableCnt[cur.start]++;
            int limit = cur.end - mDuration + 2;
            listenableCnt[limit]--;
            max = Math.max(max, limit);
/*
            for (int t = cur.start; t < cur.end - mDuration + 2; t++) {
                listenableCnt[t]++;
                if (listenableCnt[t] == M) {
                    complete = true;
                    result = t;
                    break;
                }
            }
*/
            temp.offer(cur);
        }
        pq = temp;
        int tempNum = 0;
        for (int i = 0; i < max; i++) {
            tempNum += listenableCnt[i];
            if (tempNum >= M) {
                return i;
            }
        }
        return -1;
    }
}
