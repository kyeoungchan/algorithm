# 네트워크 복구
https://www.acmicpc.net/problem/2211

입력
```text
4 5
1 2 1
1 4 4
1 3 2
4 2 2
4 3 3
```
출력
```text
3
1 2
3 1
4 2
```

## 오답노트
PQ에 업데이트된 비용을 넣을 때 `dist[nextVertex]`가 아닌 `cost`를 넣었다...