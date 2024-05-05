# 무선충전
https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRDL1aeugDFAUo  
### 오답노트
1. `canCharge()` 메서드에서 범위를 range가 아닌 performance로 두었다.
2. 좌표 입력을 잘못하였다. 문제에서는 x,y로 즉, c,r 순으로 좌표를 주었는데 나는 r,c로 받아서 userB가 이동했을 때 BC2로부터 충전을 받지 못하는 상황이 발생하였다.(예제 1 기준)
3. 가장 치명적인 실수가 하나 더 있었는데, `freeUser()` 메서드를 통해서 사용자가 충전을 그만두면 하나의 충전기를 공유해서 쓰고 있는 경우에는 나머지 한 사용자가 그 충전기를 독점하게 되므로 충전량이 늘어나야하는데 그것을 미쓰하였다.
    => 원상복귀시킬 때 완벽하게 원상복귀시켰는지 꼼꼼히 따져보자!!