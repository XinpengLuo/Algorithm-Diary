
### 2021年11月7日 第一次周赛 完成题目 2 / 4

> 第一题 [5918. 统计字符串中的元音子字符串](https://leetcode-cn.com/problems/count-vowel-substrings-of-a-string/)

自己用的暴力方法 枚举所有子字符串 然后统计该字符串**是否是只含有元音字母 且5个元音字母都有**

**至于如何枚举一个字符串的所有子字符串 不是子串**

可以这样想我们两层循环 第一层循环控制子串的起点 第二层循环控制子串的终点 最多不超过边界 

所以这道题就是暴力枚举 然后看看行成的子字符串是否满足题目要求

```java
public int countVowelSubstrings(String word) {
        int count = 0;
        for(int i = 0; i < word.length(); i++){
            for(int j = 0; i + j < word.length(); j++){
               if(countTemp(word,j,i + j))//注意这里的边界 两边都是闭区间
                   count++;
            }
        }
        return count;
    }
    public boolean countTemp(String word, int start, int end){
        int ans = 0;
        int a = 0, e = 0, i = 0, o = 0, u = 0;
        for(int k = start; k <= end; k++){
            char temp = word.charAt(k);
            if(temp == 'a')
                a++;
            else if(temp == 'e')
                e++;
            else if(temp == 'i')
                i++;
            else if(temp == 'o')
                o++;
            else if(temp == 'u')
                u++;
            else
                return false;
        }
        if(a > 0 && e > 0 && i > 0 && o > 0 && u > 0)
            return true;
        return false;
    }
```

> 第二题 [5919. 所有子字符串中的元音](https://leetcode-cn.com/problems/vowels-of-all-substrings/)

这道题首先肯定可以想到暴力 枚举所有子字符串然后去挨个比较 记数 但是肯定超时 

**这道题自己想了一个方法**  我们开辟一个辅助数组 数组中每一项表示以当前位置开始到数组末尾整个区间的元音字母的数量 这可以一次从后向前循环不断累加得到

然后再去枚举所有子字符串的时候 不用再去统计新生成的子字符串里有多少个原因 我们只需要将子**字符串开始的位置的元音字母数量 -  结束位置 + 1的元音字母数量即可** 

但是这样也超时 故我们可以用**数学公式去计算这样一个过程** 对于每一个子串

```java
class Solution {
    public long countVowels(String word) {
long[] help = new long[word.length()]; //都必须是long
        long pre = 0; //为什是pre。因为字符串可能很大 int 已经超了
        for(int i = word.length() - 1; i >= 0; i--){
            char temp = word.charAt(i);
            if(temp == 'a' || temp == 'e' || temp == 'i' || temp == 'o' || temp == 'u')
                help[i] = pre + 1;
            else
                help[i] = pre;
            pre = help[i];
        }
        long ans = 0; //都必须是long
//        for(int i = 0; i < word.length(); i++)
//            ans = ans + (word.length() - i) * help[i] - i * help[i];
        int preW = word.length();
        for(int i = 0; i < word.length(); i++){
            ans += preW * help[i];
            preW -= 2;
        }
        return ans;
    }
}
```

注意这个题目的要求是**求子字符串中元音的总数 只是数量 和你是不是都含有 或者 以什么开头没有任何关系**

但是这道题可以一步dp的 从后向前dp  每一个dp的元素代表以 **当前位置为起点形成的子串中的元音字母是多少** 

所以对于每一个元素 如果当前是元音字母的话 那么当前元素的子串的元音字母 就是 **当前字符形成的所有子串的数量 + dp[i + 1]** 

为什么是当前字符形成的所有子串数量呢 **因为当前字符为起点形成的所有子串都含有最少含有自己本身那个元音字母 所以加上word.length - i**

而加上dp[i + 1] 的含义就是当前位置形成的所有子串 肯定包含 i + 1 形成的所有子串 而i + 1 形成的所有子串的元音字母我们已经统计过了7==4cccccdfgsdfgsdfgsdfg-6--

word.length - i 代表当前字符为起点形成的所有子串

所以除了元素本身外 他形成的每一个字符串都包含在下一个元素形成的所有子字符串里



而对于不是元音的 他形成的所有子串含有的元音数量就是下一个元素形成的

```java
public long dp(String word){
        long[] dp = new long[word.length() + 1];
        long ans = 0;
        for(int i = dp.length - 2; i >= 0; i--){
            if(isValid(word.charAt(i))){
                dp[i] += dp[i + 1] + (word.length() - i);
            }
            else
                dp[i] = dp[i + 1];
            ans += dp[i];
        }
        return ans;
    }
```

### 2021年11月13日 第二次周赛 完成题目 2.5 / 4

> 第一题 [5910. 检查两个字符串是否几乎相等](https://leetcode-cn.com/problems/check-whether-two-strings-are-almost-equivalent/)

首先暴力方法很简单 就是分别统计两个字符串中的字符出现次数 但是这个记录出现次数的方法也比较讲究

当然可以用哈希表 但是完全不用 所以开辟两个长度为26的数组 

**a[char - 'a']++ 就可以讲小写字母对应的下标记录下来** 

再比较两个数组出现次数对比的时候 千万不能拿其中一个为基准，**因为可能这个出现了某一个字符但是另一个没有 另一个有 这个没有**

所以要么以两个数组为基准 遍历两次 但是由于小写字只有26个 所以比较循环26次 依次相减就好啦

```java
public boolean checkAlmostEquivalentTwo(String word1, String word2) {
        int[] a = new int[26];
        int[] b = new int[26];
        for (int i = 0; i < word1.length(); i++) {
            a[word1.charAt(i) - 'a']++;
        }
        for (int i = 0; i < word2.length(); i++) {
            b[word2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if(Math.abs(a[i] - b[i]) > 3)
                return false;
        }
        return true;
    }
```

> 第二题  [5911. 模拟行走机器人 II](https://leetcode-cn.com/problems/walking-robot-simulation-ii/)

这道题 其实自己现场都写的差不多啦！ 但是超时了。

超时的原因是你根本没有必要真的去模拟每一步，因为机器人只有撞到墙后才改变方向，**可以得出机器人的行走是一个环，所以机器人只会沿着外环走**

所以如果要走的步数其实是 步数总数 % 环的长度 **我们模拟的步数所以就定格在了 环的长度 - 1** （如果模等于0 等于回到原点）

*这里一定要注意：* 如果模等于0后 除了简单的不用模拟步数后 还要考虑方向的问题。 想一下如果当前位置在原点 刚好走了整数倍的步数 那么又回到了原点 **但是此时的方向应该是向下的 你是从上倒下的嘛**

如果当前不是在原点 走了整数倍 那么方向不会变。

至于如何模拟步数：

无非就是看看当前的步数加上当前的坐标会不会越界 不会越界直接走 越界的话走到边界后 改变方向后 **并且把剩下的步数拿去递归**

**改变方向的方法 自己悟的 很灵性** 

```java
public class RobotBetter {
    private int x;
    private int y;
    private String[] directionName = {"North", "East", "South", "West"};
    private int directionIndex = 1;
    private int width;
    private int height;
    private int length;
    public RobotBetter(int width, int height) {
        this.width = width;
        this.height = height;
        this.length = 2 * width + 2 * (height - 2);
        this.x = 0;
        this.y = 0;
    }

    public void move(int num) {
        num = num % length;
        if(num == 0){
            if(x == 0 && y == 0)
                this.directionIndex = 2;
            return;
        }
        int nextNum = 0;
        if(directionIndex == 0){
            if(y + num >= height){
                nextNum = num - (height - 1 - y);
                y = height - 1;
            }
            else
                y += num;

        }
        if(directionIndex == 1){
            if(x + num >= width){
                nextNum = num - (width - 1 - x);
                x = width - 1;
            }
            else
                x += num;
        }
        if(directionIndex == 2){
            if(y - num < 0){
                nextNum = num - y;
                y = 0;
            }
            else
                y -= num;
        }
        if(directionIndex == 3){
            if(x - num < 0){
                nextNum = num - x;
                x = 0;
            }
            else
                x -= num;
        }
        directionIndex = nextNum == 0 ? directionIndex : (directionIndex - 1 + 4) % 4; // 记得要判断是否有剩下的步数才去递归 注意改变方向的方式很灵性
        move(num);
    }

    public int[] getPos() {
        return new int[]{x,y};
    }

    public String getDir() {
        return directionName[directionIndex];
    }
}

```

> 第三题 [5912. 每一个查询的最大美丽值](https://leetcode-cn.com/problems/most-beautiful-item-for-each-query/)

这道题自己一开始用的哈希法 可以是可以但是太暴力了

直接按照价格排序，然后从左到右更新其美丽值 这样的话 同一个价格中以及比当前小的价格中**，最大的美丽值肯定是在最后一个小于等于target的数字上**

那么对于每一次查询又变成了 **如何在有序数组中找到最后一个小于或者等于target的数字 这时候看二分总结即可。** 其实如果熟悉二分的话 这道题其实不算难。

```java
class Solution {
    public int[] maximumBeauty(int[][] items, int[] queries) {
     Arrays.sort(items, new Comparator<int[]>() {
            @Override
            public int compare(int[] t1, int[] t2) {
                return t1[0] - t2[0];
            }
        });
        int preMax = Integer.MIN_VALUE;
        for(int i = 0; i < items.length; i++){
            preMax = Math.max(preMax,items[i][1]);
            items[i][1] = preMax;
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int index = findLastIndex(items,queries[i]);
            if(index != -1)
                res[i] = items[index][1];
        }
        return res;
    }
    public int findLastIndex(int[][] items, int target){
         int l = 0;
        int r = items.length - 1;
        int index = -1;
        while (l <= r){
            int mid = l + (r - l) / 2;
            if(items[mid][0] <= target){
                l = mid + 1;
                index = mid;
            }
            else
                r = mid - 1;
        }
        if(index == -1)
            return -1;
        return index;
    }
}
```

###  2021年11月14日 第三次周赛 完成题目 3 / 4 ！！！

> #### [5926. 买票需要的时间](https://leetcode-cn.com/problems/time-needed-to-buy-tickets/)

与其去模拟人的环形队列 即人去找票买 不如去模拟票去主动找人 **这样把整个模拟过程就变成了下标元素的变换**

怎么个模拟法呢？ 就是票指针从0位置开始遇到要买票的人(即当前元素不为0) 时 就卖给他 然后代价加1 然后当前元素减1。直到我们要算的那个人 变为0结束。

```java
class Solution {
    public int timeRequiredToBuy(int[] tickets, int k) {
 int ans = 0;
        while (tickets[k] != 0){ //当要买的那个人不为0 时 一轮一轮转
            int count = 0;
            for(int i = 0; i < tickets.length; i++)
            {
                if(tickets[i] != 0){
                    count++;
                    tickets[i]--;
                }
                if(tickets[k] == 0) //如果当前那个人已经买完了 就直接结束吧。。。
                    break;
            }
            ans+= count;
        }
        return ans;
    }
}
```

> #### [5927. 反转偶数长度组的节点](https://leetcode-cn.com/problems/reverse-nodes-in-even-length-groups/)

即将链表长度分为1 + 2 + 3 + …… 如果最后一组不为n 那么就是其该有的长度

所以我们肯定要定义一个工具函数 用来反转从指定头节点 到 指定尾节点的函数 返回新的头节点 传入函数的头节点自然是本身的头节点 要反转的尾巴节点 **应该是原本尾巴节点的下一个节点**

你想一下反转整个链表也是 虽然是说反转第一个到最后个节点 **但是最后判定的范围是 走到最后个节点的后面个节点就停止（即null)**

链表的题怎么说呢 不难 **但都很考细节 出错的话就好好静下心来调试**

```java
class Solution {
    public ListNode reverseEvenLengthGroups(ListNode head) {
        if(head == null || head.next == null)
            return head;
        int count = 1;
        ListNode temp = head;
        ListNode preNode = new ListNode(-1);
        ListNode ans = preNode;
        while (temp != null){
            int tempCount = 1;
            ListNode tempHead = temp;
            ListNode tempTail = temp;
           while (tempTail != null && tempCount != count && tempTail.next != null){  //对于一般状况 结束循环后 tail刚好指向该指向的位置 而不是下一个 为什么要next 不等于 null 因为对于最后一次的时候会多算一次
                tempTail = tempTail.next;
                tempCount++;
            }
            ListNode next = tempTail.next;
            if( tempCount % 2 == 0)
            {
                preNode.next = reverse(tempHead,tempTail.next); //传入的是下一个节点
                preNode = tempHead; //反转后 一开始的头就等于了 下一次的前面个
            }
            else
                {
                    preNode.next = tempHead; //否则就正常连接
                    preNode = tempTail;
                }
            count++;
            temp = next;
        }
        return ans.next;
    }
    public ListNode reverse(ListNode head, ListNode tail){
        if(head == null || head.next == null)
            return head;
         ListNode temp = head;
         ListNode newHead = new ListNode(-1);
         while (temp != tail){
             ListNode next = temp.next;
             temp.next = newHead.next;
             newHead.next = temp;
             temp = next;
         }
         return newHead.next;
    }
}
```

> #### [5928. 解码斜向换位密码](https://leetcode-cn.com/problems/decode-the-slanted-ciphertext/)

其实很简单 首先列数 就等于输入字符串的长度 除以 传进来的行数

既然行数列数都知道了 那么就可以任意定义一个字符了 那么题目就转变成了 以第一行为基准，依次遍历，拼接其对应的斜线上的字母。

下一个斜线上下标为 i + (col + 1) 不要去开辟个数组真的去模拟下标啦 这样也可以呀

拼接的时候用stringbuilder 然后最后去除末尾的空格就好啦

```java
class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
      if(rows == 1)
            return encodedText;
        int cols = encodedText.length() / rows;
        int count = cols * rows;
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < cols; i++){
            int start = i;
            while (start < count){
                char temp = encodedText.charAt(start);
                ans.append(temp);
                start += (cols + 1);
            }
        }
        return trimEnd(ans.toString().toCharArray());
    }
    public String trimEnd(char[] value) {
        int len = value.length;
        int st = 0;
        char[] val = value;
        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return ((st > 0) || (len < value.length)) ? new String(val).substring(st, len) : new String(val);
    }
}
```

### 2021年11月21日 第四次周赛 完成题目 2.9 / 4 ！！！

> #### [5930. 两栋颜色不同且距离最远的房子](https://leetcode-cn.com/problems/two-furthest-houses-with-different-colors/)

我靠这道题不简单呀 不是那么简单

首先最简单的方法就是暴力梭哈 以每一个元素为起始位置 第二层循环以他加一为开始位置查找 比赛时居然没有想到操了

还有个方法就是哈希表 记录每一个不同颜色的最后出现位置，然后再遍历数组去和对应的位置计算距离比较 这样太蠢了

看到一个思路 太牛逼了

首先如果 首尾元素不等 那么答案就是首尾的距离

如果首尾相等 **那么中间那个不同的颜色的最大距离一定能扩充到首部或者尾部 **太帅了 这个思想

所以依次遍历整个数组 当房子的颜色和数组首部或者尾部的颜色不同的时间 比较其是到首部的位置远 还是 尾部的位置远 最后就是答案

```java
class Solution {
    public int maxDistance(int[] colors) {
 if(colors[0] != colors[colors.length - 1])
            return colors.length - 1;
        int ans = 0;
        for(int i = 1; i < colors.length; i++)
            if(colors[i] != colors[0])
                ans = Math.max(Math.max(i,colors.length - 1 - i),ans);
        return ans;
    }
}
```

> #### [5201. 给植物浇水](https://leetcode-cn.com/problems/watering-plants/)

一次遍历 模拟那个流程就好啦 

如果当前的水能够满足下一次 那就步伐加一 并且当前水减去需要的水

如果当前的水不能够满足下一次 那就首先要回去加水 再把水送回来 一趟是 （ i + 1 ） 首先要加两次 下标少1啦 然后要到下一步 还要加一 然后还要减去需要的水

```java
 public int wateringPlants(int[] plants, int capacity) {
        int ans = 0, full = capacity;
        for(int i = -1; i < plants.length - 1; i++){
            if(capacity >= plants[i + 1])
                {
                    ans++;
                    capacity -= plants[i + 1];}
            else{
                ans += (i + 1 + i + 1) + 1;
                capacity = full - plants[i + 1];
            }
        }
        return ans;
    }
```

> #### [5186. 区间内查询数字的频率](https://leetcode-cn.com/problems/range-frequency-queries/)

说实话这次周赛的题感觉都不算难 但是就是喜欢搞些奇淫技巧 比如这道题本质就是查**一个数组中某一个数字出现了几次**

最常用的做法就是直接遍历呗 已经很好了 但是他偏要你空间换时间 而且空间还不能是来的时候才去换，不能排序因为排序要时间

所以做法是为数组中为**每一个数字建立一个list 此list中存放的是该数字出现的下标**

由于数组中从左往右遍历 所以每个数字的list中出现的下标肯定是有序数列 递增的

那么我们要查某一个区间某个数字的出现次数该怎么办呢？

我们第一步先拿到该数字的list 比如数字5 在下标[1,3,4,6,7] 出现了 我们想要他在5到7的查询频数  很明显是 6 7 所以是2

这个问题就变成了有序数组中二分查找第一个大于等于target的数字的下标 以及第一个大于target的下标 两个下标相减 就是这个区间范围有几个数字

所以我们在list里查第一个大于等于left的下标 查第一个大于right的下标 两个相减 就是这个区间里有几个数字

注意可能区间没有数字 如果找不到返回-1 对于大于的 返回数组的长度

```java
class RangeFreqQuery {
 private int[] nums;
    HashMap<Integer,List<Integer>> map = new HashMap<>();
    public RangeFreqQuery(int[] arr) {
        nums = arr;
        for (int i = 0; i < arr.length; i++) {
            if(map.containsKey(nums[i])){
                List<Integer> temp = map.get(nums[i]);
                temp.add(i);
            }
            else{
                List<Integer> temp = new ArrayList<>();
                temp.add(i);
                map.put(arr[i],temp);
            }
        }
    }

    public int query(int left, int right, int value) {
        List<Integer> temp = map.get(value);
        int first = findFirstTarget(temp,left);
         if(first == -1)
            return 0;
        int last = findGreaterTarget(temp,right);
        return last - first;
    }
    public int findFirstTarget(List<Integer> arr, int target){
        if(arr == null || arr.size() == 0)
            return -1;
        int ans = -1;
        int l = 0;
        int r = arr.size() - 1;
        while (l <= r){
            int mid = l + (r - l) / 2;
            if(arr.get(mid) >= target)
            {
                r = mid - 1;
                ans = mid;
            }
            else if(arr.get(mid) < target)
                l = mid + 1;
        }
        return ans;
    }
    //查找第一个大于target的下标
    public int findGreaterTarget(List<Integer> arr, int target){
        if(arr == null || arr.size() == 0)
            return -1;
        int ans = arr.size();
        int l = 0;
        int r = arr.size() - 1;
        while (l <= r){
            int mid = l + (r - l) / 2;
            if(arr.get(mid) > target)
            {
                r = mid - 1;
                ans = mid;
            }
            else
                l = mid + 1;
        }
        return ans;
    }

}

```



 ### 2021年11月27日第五次周赛

> #### [5922. 统计出现过一次的公共字符串](https://leetcode-cn.com/problems/count-common-words-with-one-occurrence/)

哈希表模拟 太简单了 不说了

> #### [5923. 从房屋收集雨水需要的最少水桶数](https://leetcode-cn.com/problems/minimum-number-of-buckets-required-to-collect-rainwater-from-houses/)

 贪心算法，从左往右遍历，遇到房子先看其左边有没有水桶，如果有的话那就不管。

如果没有的话再看其右边是不是空地 如果是空地就把水桶放在右边的位置， 如果右边不是空地，那么再看看左边是不是空地

如果左边即没有水桶 左右两边也放不下去 那么就返回-1

每当我们遍历到一个房屋时，我们可以有如下的选择：

如果房屋的两侧已经有水桶，那么我们无需再放置水桶了；

如果房屋的两侧没有水桶，那么我们优先在房屋的「右侧」放置水桶，这是因为我们是从左到右进行遍历的，即当我们遍历到第 ii 个位置时，前 i-1i−1 个位置的房屋周围都是有水桶的。因此我们在左侧放置水桶没有任何意义，而在右侧放置水桶可以让之后的房屋使用该水桶。

如果房屋的右侧无法放置水桶（例如是另一栋房屋或者边界），那么我们只能在左侧放置水桶。如果左侧也不能放置，说明无解。

```java
public int minimumBuckets(String street) {
        int ans = 0;
        int[] temp = new int[street.length()];
        for(int i = 0; i < street.length(); i++){
            if(street.charAt(i) == 'H'){
                if(i - 1 >= 0 && temp[i - 1] == 1)
                    continue;
                else{
                    if(i + 1 < street.length() && street.charAt(i + 1) == '.'){
                        temp[i + 1] = 1;
                        ans++;
                    }
                    else if(i - 1 >= 0 && street.charAt(i - 1) == '.')
                    {
                        temp[i - 1] = 1;
                        ans++;
                    }
                    else
                        return -1;
                }
            }
        }
        return ans;
    }
```

> #### [5924. 网格图中机器人回家的最小代价](https://leetcode-cn.com/problems/minimum-cost-homecoming-of-a-robot-in-a-grid/)

这道题简直就是反向出题 管你朝多少个方向走 4个方向也好 8个方向也好 移动的代价都是正数

所以你直接朝目标方向走就行啦 先走行 先走列都一样的操了 真是 比赛时还一直去递归 动态规划 记忆 无语了

```java
public int minCost(int[] startPos, int[] homePos, int[] rowCosts, int[] colCosts) {
        int ans = 0;
        int x = startPos[0], y = startPos[1];
        int targetX = homePos[0], targetY = homePos[1];
        if(x < targetX){
            while (x < targetX){
                ans += rowCosts[x + 1];
                x++;
            }
        }
        else{
            while (x > targetX){
                ans += rowCosts[x - 1];
                x--;
            }
        }
        if(y < targetY){
            while (y < targetY){
                ans += colCosts[y + 1];
                y++;
            }
        }
        else{
            while (y > targetY){
                ans += colCosts[y - 1];
                y--;
            }
        }
        return ans;
    }

```

### 2021年11月28日 第六次周赛 3.5 / 4 

> #### [5938. 找出数组排序后的目标下标](https://leetcode-cn.com/problems/find-target-indices-after-sorting-array/)

 这道题当然可以直接排序 然后去遍历 但是复杂度你懂的 

所以一个更好的做法是 统计原来的数组中比target小的数字的个数 统计原来的数组中等于target的个数

那么等于target的下标一定是在小于它后面开始的 往后面数等于的个数

```java
 public List<Integer> targetIndices(int[] nums, int target) {
List<Integer> ans = new ArrayList<>();
        int countLess = 0;
        int countEqual = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] == target)
                countEqual++;
            if(nums[i] < target)
                countLess++;
        }
        countEqual += countLess;
        while (countLess < nums.length && countLess < countEqual){
            ans.add(countLess);
            countLess++;
        }
        return ans;
    }
```

> #### [5939. 半径为 k 的子数组平均值](https://leetcode-cn.com/problems/k-radius-subarray-averages/)

前缀和

```java
public int[] getAverages(int[] nums, int k) {
    int[] res = new int[nums.length];
    long[] preSum = new long[nums.length];
    long sum = 0;
    for(int i = 0; i < nums.length; i++){
        sum += nums[i];
        preSum[i] = sum;
    }
    int index = 0;
    while (index < res.length){
        if(index < k || index + k >= nums.length){
            res[index] = -1;
            index++;
            continue;
        }
        long leftValue = (preSum[index] - preSum[index - k] + nums[index - k]);
        long rightValue =  (preSum[index + k] - preSum[index]);
        res[index] = (int)((leftValue + rightValue) / (2 * k + 1));
        index++;
    }
    return res;
}
```

> #### [5940. 从数组中移除最大值和最小值](https://leetcode-cn.com/problems/removing-minimum-and-maximum-from-array/)

最简单的方式就是先找出最小值和最大值的下标 然后分六种情况判断 

但是看了官方题解后 觉得真他妈帅呀 假设我们已经找到了最大值的下标 和 最小值的下标 **我们取两个下标中较小的那个为l 较大的那个为r**

那么移除后的满足要求的数组 无非就是三种形式

[0, l - 1] [l + 1, r - 1],[r + 1,n - 1]

这三种对应的移除代价为（或者是说移除数组的个数为 n - l, l + 1 + n - r ,r + 1）就是减去剩下的元素 自己想下边界调节就好啦

```java
class Solution {
    public int minimumDeletions(int[] nums) {
if(nums.length == 1)
            return 1;
        if(nums.length == 2)
            return 2;
        int maxIndex = 0, maxValue = nums[0];
        int minIndex = 0, minValue = nums[0];
        for(int i = 1; i < nums.length; i++){
            if(nums[i] > maxValue){
                maxValue = nums[i];
                maxIndex = i;
            }
            else if(nums[i] < minValue){
                minValue = nums[i];
                minIndex = i;
            }
        }
        int l = Math.min(maxIndex,minIndex);
        int r = Math.max(maxIndex,minIndex);
        return Math.min(Math.min(r + 1,l + 1 + nums.length - r),nums.length - l);
    }
}
```

> #### [2092. 找出知晓秘密的所有专家](https://leetcode-cn.com/problems/find-all-people-with-secret/)

**关于并查集的思考 左神初级班第七节**

并查集的两个作用：

​	非常快的查两个元素是否属于一个集合

​	两个元素各自所在的集合合并在一起 注意是所在的集合哦

因为非常快 做某些题 会有很好的效果

初始化的时候 必须把所有的样本给他

### 2021年12月5日 第七次周赛 3.5 / 4

> #### [2094. 找出 3 位偶数](https://leetcode-cn.com/problems/finding-3-digit-even-numbers/)

感觉有点类似于全排列 直接暴力递归 回溯 所以我的做法其实很蠢 看了下官方解答的 真的很简单 因为只有三位数字 所以我们直接去枚举三位数字的所有可能就可以了 代码很简单 但是注意三位枚举时跟我们平时不太一样

看代码： 回溯代码就不贴了

```java
public int[] better(int[] digits){
    HashSet<Integer> set = new HashSet<>();
    for(int i = 0; i < digits.length; i++)
        for(int j = 0; j < digits.length; j++) //注意这里 j 和 k 都是从0开始 而不是从i + 1 以及 j + 1 开始 其实很简单 就好比你去枚举9的三次方组合 
            for(int k = 0; k < digits.length; k++){
                if(i == j || i == k || j == k) //因为一个数字只能用一次 所以相等直接滚 非常好这一句
                    continue;
                int tempNumber = 100 * digits[i] + 10 * digits[j] + digits[k];
                if(tempNumber >= 100 && tempNumber % 2 == 0)
                    set.add(tempNumber);
            }
    int[] res = new int[set.size()];
    Object[] objects = temp.toArray();
    for(int i = 0; i < objects.length; i++)
        res[i] = (int)objects[i];
    Arrays.sort(res);
    return res;
}
```

> #### [5943. 删除链表的中间节点](https://leetcode-cn.com/problems/delete-the-middle-node-of-a-linked-list/)

不说了 老规矩了 快慢指针 只是慢指针停下来的时候刚好走到中间节点 **我们知道要删除一个节点 一定需要被删除节点的前一个节点** 所以每次还要记录一下上一次慢指针的节点

```java
public ListNode deleteMiddle(ListNode head) {
if(head == null || head.next == null)
            return null;
        ListNode slow = head;
        ListNode fast = head;
        ListNode temp = null;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            temp = slow;
            slow = slow.next;
        }
        ListNode next = temp.next.next;
        temp.next.next = null;
        temp.next = next;
        return head;
    }
```

> #### [5944. 从二叉树一个节点到另一个节点每一步的方向](https://leetcode-cn.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/)

首先我们弄清楚如何求二叉树两个节点中最近公共祖先 我们假设给定的二叉树中一定含有该节点

- 如果一个节点在一个跟节点的左边一个在右边 那么该根节点就是最近公共祖先（且是唯一的） 自己举例想想啦
- 如果一个根节点的左边找不到p，q的任何一个节点 那么最近的公共祖先一定在他的右边树上 **至于是右边树上的哪里 我们去递归解决**
- 右边找不到 返回左边的答案同上
- 如果一个节点刚好和p或者q相等 直接返回

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null)
            return null;
        if(root.val == p.val || root.val == q.val)
            return root;
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        if(left == null)
            return right;
        if(right == null)
            return left;
        return root;
    }
```

好吧上面这个只是为了复习吧哈哈哈 这道题也用不上 

首先说一下自己的想法，就是给每个节点都添加父亲节点 然后三个方向暴力搜索递归 并且为了防止回来 还加了防再次访问的措施 可谓是很傻逼了 居然给过了 600多mb的内存啊

然后最好的方法是：

虽然两个节点的路径可以是 **最近公共祖先到两个节点的路径** 但是如果我们单独的去找公共祖先浪费了很多次遍历

**其实由于二叉树中不存在闭环 如果你总是向下走， 到某个节点的路径其实是唯一的**

 要来到一个节点肯定要先来到他们的最近公共祖先 **这就代表从root节点开始到两个节点有一段路径是公共的** 所以我们只要求出从根节点到start节点 和 end节点的路径 **再除去公共路径的部分** 剩下的就是他们的最短路径 当然 start 路径要全部改成'U'  

所以在一次dfs中求出两个节点的路径， 以前的话可能会去写一个方法，然后走两次 但是看别人的解答后，可以实现，代价是你必须遍历完整个树，哪怕你一上来就找到了答案！

```java
public String beeter(TreeNode root, int startValue, int destValue){
        StringBuilder start = new StringBuilder();
        StringBuilder dest = new StringBuilder();
        travelToTarget(root,startValue,destValue,start,dest,new StringBuilder());
        if(start.length() == 0)
            return dest.toString();
        int index = 0;
        while (index < start.length() && index < dest.length() && start.charAt(index) == dest.charAt(index)) //去找公共部分的下一个位置
            index++;
        StringBuilder temp = new StringBuilder();
        for(int i = index; i < start.length(); i++) //全部变成u
            temp.append('U');
        temp.append(dest.subSequence(index,dest.length())); //再把另外一边的路径加起来
        return temp.toString();
    }
    public void travelToTarget(TreeNode root, int startVal,int destVal, StringBuilder start, StringBuilder dest, StringBuilder temp){
        if(root == null)
            return;
        if(root.val == startVal) //递归真的好好玩 
            start.append(temp.toString());
        if(root.val == destVal)
            dest.append(temp.toString());
        temp.append("L");
        travelToTarget(root.left,startVal,destVal,start,dest,temp);
        temp.deleteCharAt(temp.length() - 1);
        temp.append("R");
        travelToTarget(root.right,startVal,destVal,start,dest,temp);
        temp.deleteCharAt(temp.length() - 1);
    }
```

### 2022年2月13日 第9次周赛（2 / 4）

> #### [6004. 得到 0 的操作数](https://leetcode-cn.com/problems/count-operations-to-obtain-zero/)

直接模拟即可。

```java
class Solution {
    public int countOperations(int num1, int num2) {
 int count = 0;
        while (num1 != 0 && num2 != 0){
            if(num1 >= num2)
                num1 -= num2;
            else
                num2 -= num1;
            count++;
        }
        return count;
    }
}
```

> #### [6005. 使数组变成交替数组的最少操作数](https://leetcode-cn.com/problems/minimum-operations-to-make-the-array-alternating/)

自己的思路是没错的 无非就是统计 奇数下标 和 偶数下标 分别出现次数最多的数字是什么 把不是这个数字的数字都变成这个数字就好了 `比如说：奇数下标的个数 - 奇数下标出现次数最多的那个数字的次数 = 奇数下标需要操作的次数` 最后将奇数下标和偶数下标的操作次数加在一起即可。 但是注意 **奇数下标出现次数最多的数字很可能和偶数的一样** 此时不满足 

故要分情况讨论 除了记录最大的 还要分别记录奇数和偶数次大的 当奇数和偶数的最多出现次数的数字相同时

此时比较是 把奇数的数字全部变成次大的 还是偶数的数字全部变成次大的

```java
class Solution {
    public int minimumOperations(int[] nums) {
HashMap<Integer,Integer> even = new HashMap<>(); //偶数
        HashMap<Integer,Integer> odd = new HashMap<>(); //奇数
        for(int i = 0; i < nums.length; i++){
            if(i % 2 == 0)
                even.put(nums[i],even.getOrDefault(nums[i],0) + 1);
            else
                odd.put(nums[i],odd.getOrDefault(nums[i],0) + 1);
        }
        int maxEven1 = 0, maxEven2 = 0, maxNumber1 = 0;
        int maxOdd1 = 0, maxOdd2 = 0, maxNumber2 = 0;
        //求出偶数出现次数最多的数字 和 第二大数字
        for (Integer key : even.keySet()) {
            if(even.get(key) > maxEven1) //注意这里统计次大的算法
               {
                   maxNumber1 = key;
                   maxEven2 = maxEven1;
                   maxEven1 = even.get(key);
               }else if(even.get(key) >maxEven2) //这个比较很关键
                   maxEven2 = even.get(key);
        }
        //求出奇数出现次数最多的数字 和 第二大数字
        for (Integer key : odd.keySet()) {
            if(odd.get(key) > maxOdd1){
                maxNumber2 = key;
                maxOdd2 = maxOdd1;
                maxOdd1 = odd.get(key);
            }
            else if(odd.get(key) > maxOdd2)
                maxOdd2 = odd.get(key);
        }
        int len = nums.length;
        int evenLength = len % 2 == 0 ? len / 2 : (len / 2) + 1;
        int oddLength = len - evenLength;
        if(maxNumber1 != maxNumber2)
            return (evenLength - maxEven1) + (oddLength - maxOdd1);
        return Math.min((evenLength - maxEven2) + (oddLength - maxOdd1), (evenLength - maxEven1) + (oddLength - maxOdd2));
    }
}
```

> #### [6006. 拿出最少数目的魔法豆](https://leetcode-cn.com/problems/removing-minimum-number-of-magic-beans/)

可以简单的发现经过变换后 剩下的状态肯定是 `数组中只剩下0和另一个数字 且另一个数字一定是原数组中某个数字`

比如 4 5 6 肯定是把5 6 变成4 不会大家都变成3 把？

那么我们枚举所有剩下的状态 即：分别以数组中的某个数字为基准 将其他数字要么变成0 要么变成基准数字

暴力实现 两层循环 外面个循环模拟基准数字 里面个挨个扫描判断

更好的方法（自己也想到了哦！！！）： 将原数组排序 以当前数字为基准的话 当前数字左边的数字由于都比当前数字小 故只能变成0 自己右边的数字都变成当前数字 故此时剩下的豆子总数为 `(len - i) * num[i]` 

再把之前豆子总数减去当前值 即可 求的拿出去的值 依次求最小即可。

注意 long 和溢出的问题

```java
class Solution {
    public long minimumRemoval(int[] beans) {
long res = Long.MAX_VALUE;
        long sum = 0;
        for (int bean : beans) {
            sum += bean;
        }
        Arrays.sort(beans);
        for(int i = 0; i < beans.length; i++){
            long temp = (long)(beans.length - i) * (long)beans[i]; //防溢出
            res = Math.min(res,sum - temp);
        }
        return res;
    }
}
```
