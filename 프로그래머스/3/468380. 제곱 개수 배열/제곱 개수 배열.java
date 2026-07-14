class Solution {
    
    int N;
    int[] arr;
    long[] prefixLen, prefixSum;
    
    public long[] solution(int[] arr, long l, long r) {
        N = arr.length;
        this.arr = arr;
        
        prefixLen = new long[N];
        prefixSum = new long[N];
        
        prefixLen[0] = arr[0];
        prefixSum[0] = (long)arr[0] * arr[0];
        
        for (int i = 1; i < N; i++) {
            prefixLen[i] = prefixLen[i - 1] + arr[i];
            prefixSum[i] = prefixSum[i - 1] + (long)arr[i] * arr[i];
        }
        
        
        long K = genK(l, r);
        // System.out.println("K: " + K);
        long C = genC(K, r - l + 1);
        
        return new long[] {K, C};
    }
    
    int getIdx(long seq) {
        int left = 0;
        int right = N;
        int idx = N - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (prefixLen[mid] >= seq) {
                idx = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return idx;
    }
    
    long sumFromZero(long seq, int idx) {
        // int idx = getIdx(seq);
        if (idx == 0) return seq * arr[0];
        
        long result = prefixSum[idx - 1];
        result += (seq - prefixLen[idx - 1]) * arr[idx];
        return result;
    }
    
    long genK(long l, long r) {
        int lIdx = getIdx(l);
        long lSum = sumFromZero(l-1, lIdx);
        
        int rIdx = getIdx(r);
        long rSum = sumFromZero(r, rIdx);
        
        return rSum - lSum;
    }
    
    long genC(long K, long len) {
        long result = 0L;
        
        long lSeq = 1L;
        long rSeq = len;
        int lIdx = 0;
        int rIdx = getIdx(rSeq);
        
        
        while (rSeq <= prefixLen[N - 1]) {
            long leftMove = prefixLen[lIdx] - lSeq + 1;
            long rightMove = prefixLen[rIdx] - rSeq + 1;
            long move = Math.min(leftMove, rightMove);
            
            long window = genK(lSeq, rSeq);
            
            if (arr[lIdx] == arr[rIdx]) {
                if (window == K)
                    result += move;
            } else {
                long diff = arr[rIdx] - arr[lIdx];
                // window, window + diff, window + 2*diff +... + (move-1) * diff
                long div = (K - window) / diff;
                
                long mod = (K - window) % diff;
                if (div >= 0 && div < move && mod == 0) result++;
            }
            
            lSeq += move;
            rSeq += move;
            if (lSeq > prefixLen[lIdx]) lIdx++;
            if (rSeq > prefixLen[rIdx]) rIdx++;
        }
        
        return result;
    }
}