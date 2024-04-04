# 최단경로
https://www.acmicpc.net/problem/1753

입력
```text
5 6
1
5 1 1
1 2 2
1 3 3
2 3 4
2 4 5
3 4 6
```
출력
```text
0
2
3
7
INF
```

### 오답 노트
pq에 다시 업데이트된 nextNode와 가중치를 넣는 것을 깜빡하였다.