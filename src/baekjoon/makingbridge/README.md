# 낚시왕
https://www.acmicpc.net/problem/17143

입력1
```text
4 6 8
4 1 3 3 8
1 3 5 2 9
2 4 8 4 1
4 5 0 1 4
3 3 1 2 7
1 5 8 4 3
3 6 2 1 2
2 2 2 3 5
```
출력1
```text
22
```

입력2
```text
100 100 0
```
출력2
```text
0
```

입력3
```text
4 5 4
4 1 3 3 8
1 3 5 2 9
2 4 8 4 1
4 5 0 1 4
```
출력3
```text
22
```

입력4
```text
2 2 4
1 1 1 1 1
2 2 2 2 2
1 2 1 2 3
2 1 2 1 4
```
출력4
```text
4
```

### 배운점
```java
static void move(int[][] sharkInfo){
    for(int id=1;id<M +1;id++){
        if(!isGone[id]){
            int[]info=sharkInfo[id];
            int pI=info[0];
            int pJ=info[1];
            int d=info[3];
            int v=info[2];
    
            v=d< 2?v%(2*(R-1)):v%(2*(C-1));
            int nI=pI+dI[d]*v;
            int nJ=pJ+dJ[d]*v;
            // ...
        }
    }
}
```
튕겨나가는 로직을 `v = d < 2 ? v % (2 * (R - 1)) : v % ( 2 * (C - 1));` 와 같은 코드를 통해서 한 번에 줄여줄 수 있다.