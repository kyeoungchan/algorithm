class Solution {

    public long[] solution(int[] arr, long l, long r) {
        int n = arr.length;

        long[] prefixLen = new long[n];
        long[] prefixValSum = new long[n];

        prefixLen[0] = arr[0];
        prefixValSum[0] = 1L * arr[0] * arr[0];

        for (int i = 1; i < n; i++) {
            prefixLen[i] = prefixLen[i - 1] + arr[i];
            prefixValSum[i] = prefixValSum[i - 1] + 1L * arr[i] * arr[i];
        }

        long totalLen = prefixLen[n - 1];
        long L = r - l + 1;

        long K = prefixSum(prefixLen, prefixValSum, arr, r)
                - prefixSum(prefixLen, prefixValSum, arr, l - 1);

        long maxStart = totalLen - L + 1;

        long count = 0L;

        // 시작점 s=1인 윈도우 합
        long cur = prefixSum(prefixLen, prefixValSum, arr, L);

        long s = 1; // 시작점(1-indexed)

        while (s <= maxStart) {
            // 마지막 시작점이면 바로 처리하고 종료
            if (s == maxStart) {
                if (cur == K) count++;
                break;
            }

            int outBlock = lowerBound(prefixLen, s);       // brr[s]가 속한 블록
            int inBlock = lowerBound(prefixLen, s + L);    // brr[s+L]가 속한 블록

            long outEnd = prefixLen[outBlock];             // 현재 outBlock의 끝 위치
            long inEnd = prefixLen[inBlock];               // 현재 inBlock의 끝 위치

            // diff = brr[t+L] - brr[t] 가 일정한 마지막 t
            long endT = Math.min(outEnd, inEnd - L);
            endT = Math.min(endT, maxStart - 1);

            long diff = (long) arr[inBlock] - arr[outBlock];

            // 이번 구간에서 세는 시작점: s ~ endT
            long cnt = endT - s + 1;

            count += countInAP(cur, diff, cnt, K);

            // 다음 시작점(endT+1)의 윈도우 합으로 이동
            cur += cnt * diff;
            s = endT + 1;
        }

        return new long[]{K, count};
    }

    // brr의 앞에서부터 p개 원소의 합
    private long prefixSum(long[] prefixLen, long[] prefixValSum, int[] arr, long p) {
        if (p <= 0) return 0L;

        int idx = lowerBound(prefixLen, p);

        long prevLen = (idx == 0) ? 0L : prefixLen[idx - 1];
        long prevSum = (idx == 0) ? 0L : prefixValSum[idx - 1];

        long inside = p - prevLen;
        return prevSum + inside * arr[idx];
    }

    // prefixLen[idx] >= target 인 최소 idx
    private int lowerBound(long[] prefixLen, long target) {
        int left = 0;
        int right = prefixLen.length - 1;

        while (left < right) {
            int mid = (left + right) >>> 1;
            if (prefixLen[mid] >= target) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    // first, first+diff, ..., 총 cnt개 중 target 개수
    private long countInAP(long first, long diff, long cnt, long target) {
        if (cnt <= 0) return 0L;

        if (diff == 0) {
            return first == target ? cnt : 0L;
        }

        long x = target - first;
        if (x % diff != 0) return 0L;

        long t = x / diff;
        return (0 <= t && t < cnt) ? 1L : 0L;
    }
}