# 🔥 전송시간
[링크](https://swexpertacademy.com/main/code/codeBattle/problemDetail.do?contestProbId=AZCbUeuqEnADFAVs&categoryId=AZEGCEMa7TkDFAQW&categoryType=BATTLE&battleMainPageIndex=1)
### 오답노트
1. 다익스트라를 쓸 때 웬만하면 PriorityQueue는 전역 변수로 두지 말고 일일이 새로 생성하자.
2. 그래프 초기화를 테스트케이스마다 안 했음. => 애매하다 싶으면 차라리 처음부터 리스트를 생성하는 전략도 나쁘지 않다.
3. 문제를 잘못 읽어서 노드가 새로 생성이 안 되는 것을 연결이 되지 않은 노드가 있을 거라는 것을 생각하지 못했다.
   - 즉, init() 메서드에서 등장하지 않은 노드가 있을 수 있음을 인지하지 못했다.
   - 따라서 addLine() 메서드에서 NPE가 발생하곤했다.
4. 연결된 라인이 없을 경우 아무 것도 하지 않는다는 것을 인지했음에도 NPE가 발생할 수 있음을 인지하지는 못했다.