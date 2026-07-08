import java.util.*;

class Solution {
    
    int N;
    int[] arr;
    long[] prefixSum, prefixLen;
    
    public long[] solution(int[] arr, long l, long r) {
    
        this.arr = arr;
        N = arr.length;
        prefixSum = new long[N];
        prefixLen = new long[N];
        
        
        prefixSum[0] = (long) arr[0] * arr[0];
        prefixLen[0] = arr[0];
        for (int i = 1; i < N; i++) {
            prefixSum[i] = (long) arr[i] * arr[i] + prefixSum[i - 1];
            prefixLen[i] = arr[i] + prefixLen[i - 1];
        }
        // System.out.println(Arrays.toString(prefixSum));
        // System.out.println(Arrays.toString(prefixLen));
        
        long K = genK(l, r);
        
        long C = 0L;
        
        long tempL = 1;
        long tempR = r - l + 1;
        
        while (tempR <= prefixLen[N - 1]) {
            int leftIdx = getIdx(tempL);
            int rightIdx = getIdx(tempR);
            long tempK = genK(tempL, tempR);
            
            long rightMove = prefixLen[rightIdx] - tempR + 1;
            long leftMove = prefixLen[leftIdx] - tempL + 1;
            long move = Math.min(leftMove, rightMove);
            long diff = arr[rightIdx] - arr[leftIdx];
            
            if (diff == 0) {
                if (tempK == K)
                    C += move;
            } else if ((K - tempK) % diff == 0) {
                long mod = (K - tempK) / diff;
                if (mod >= 0 && mod < move) C++;
            }
            tempL += move;
            tempR += move;
        }
        
        return new long[] {K, C};
    }
    
    long genK(long l, long r) {
        long rightSum = getSum(r);
        
        long leftSum = 0L;
        if (l != 1) {
            leftSum = getSum(l - 1);   
        }
        
        return rightSum - leftSum;
    }
    
    long getSum(long seq) {
        // 처음부터 seq번째까지의 합 반환
        int seqIdx = getIdx(seq);
        
        long sum = 0L;
        if (seqIdx > 0) {
            sum = prefixSum[seqIdx - 1];
            sum += (seq - prefixLen[seqIdx - 1]) * arr[seqIdx];
        } else {
            sum = arr[0] * seq;
        }
        return sum;
    }
    
    int getIdx(long seq) {
        // seq번째 값이 어느 인덱스에 있는지 반환
        int left = 0;
        int right = N - 1;
        int seqIdx = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (prefixLen[mid] >= seq) {
                seqIdx = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return seqIdx;
    }
}