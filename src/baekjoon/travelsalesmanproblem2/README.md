# 외판원 순회2

### 공부한 점
- 재귀호출을 통해서 cnt를 N까지 가는 경우는 지금까지 방문처리한 원소들과 독립적일 때 사용가능하다.
  - 여기에서는 마지막 원소에서는 더이상 `!v[i]`를 만족하는 원소가 남지 않으므로 `cnt==N`으로 재귀호출 종료 조건을 건다면 끝까지 가지 못한다.
  - 본 코드 주석에도 달았다시피 마지막 원소는 더이상 갈 곳이 start말고는 없으므로 cnt = N-1인 경우에 판단을 해야한다.