103062216 范祐恩 assignment8

簡短敘述：
新增一個layout get_ipaddr
並且在onCreate中把一開始進入的layout改成get_ipaddr
get_ipaddr會詢問使用者serverIP為多少
按下OK！後到socket連線
並到activity_main
直到玩家在result_page按return之後
送出玩家輸入的數字、運算符號、答案給server顯示在textArea上
回到activity_main

遇到的問題：
其中一個問題是依照pdf檔說的新增java application之後
原本的MainActivity.jave就會一直顯示Gradle project sync failed. Basic functionality(e.g. editing, debugging) will not work properly.
刪掉java application才會正常
所以只好改用eslipse寫server
放在assignment8server資料夾裡
有server.jar的可執行檔可以用
不必開eslipse執行

還有一個問題是NetworkOnMainThreadException
這是因為網路的活動跑在主要執行緒上了
這樣子APP可能會因為網路的活動等待回應太久
而被系統強制關閉
把socket放在Thread上就能解決

另一個問題是Unable to resolve host "IP" : No address associated with hostname
因為我把IP打錯沒發現
所以才會一直出現這個錯誤
後來我一度想換成用本機IP來做
但是會遇到failed to connect to /127.0.0.1 (port 8000): connect failed: ECONNREFUSED (Connection refused)
因為127.0.0.1是指手機本身
要連到模擬器外的電腦要給他10.0.2.2
就能連上
之後換回電腦IP打對了就沒有錯誤了