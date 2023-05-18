package one;

import java.util.ArrayList;

public class SquidGame {
	static int naiveMax = Integer.MAX_VALUE;
    static String naiveAnswer = "";
    static int efficientMax = Integer.MAX_VALUE;
    static String efficientAnswer = "";

    public static void naiveHelper(int k, int l[], int start, int n, int sum, int maxSum) {
        if (k == 1) {
            maxSum = Math.max(maxSum, sum);
            sum = 0;
            for (int i = start; i < n; i++) {
                sum += l[i];
            }
            maxSum = Math.max(maxSum, sum);
            naiveMax = Math.min(naiveMax, maxSum);
            return;
        }
        sum = 0;
        for (int i = start; i < n; i++) {
            sum += l[i];
            maxSum = Math.max(maxSum, sum);
            naiveHelper(k-1, l, i+1, n, sum, maxSum);
        }
    }
    public static String naive(int k, int[] l) {
        naiveMax = Integer.MAX_VALUE;
        naiveHelper(k, l, 0, l.length, 0, 0);
        naiveAnswer = naiveMax + ";";
        int sum = 0;
        int players = 0;
        for (int i = 0; i < l.length; i++) {
            if (sum + l[i] <= naiveMax) {
                sum += l[i];
                if (i == l.length - 1) {
                    naiveAnswer += l[i];
                } else {
                    naiveAnswer += l[i];
                    if (i < l.length - 1)
                        if (sum + l[i + 1] <= naiveMax)
                            naiveAnswer += ",";
                        else {
                            naiveAnswer += ";";
                            sum = 0;
                        }
                }
            } else {
                sum = 0;
                naiveAnswer += ";";
            }
        }
        players = numOfChar(naiveAnswer);
        if (players + 1 == k)
            return naiveAnswer;
        System.out.println(players);
        int j = 0;
        System.out.println("playersBefore: " + players);
        while (players < k){
            naiveAnswer = naiveAnswer.replaceFirst(",",";");
            players++;
        }
        System.out.println("playersAfter: " + players);
        System.out.println(naiveAnswer);
        return naiveAnswer;
    }
    public static int numOfChar(String s){
        int n = 0;
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == ';')
                n++;
        }
        return n-1;
    }
	static ArrayList<ArrayList<Integer>> dist;
	static ArrayList<ArrayList<Integer>> finalA;
	static boolean isPossible(int time, int K,int job[])
	{
		int cnt = 0;
		int curr_time = 0;	
		dist = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> assigned = new ArrayList<Integer>();
		for (int i = 0; i <=job.length;)
		{
			if (i == job.length || (curr_time + job[i]) > time || (curr_time>0 && job.length - i == K - cnt - 1)) {
				ArrayList<Integer> temp = new ArrayList<Integer>(assigned);
				//				for (Integer n :temp)
					//					System.out.print(n + " ");
				//				System.out.println("");
				dist.add(temp);
				assigned.clear();
				curr_time = 0;
				cnt++;
				if (i == job.length)
					i++;
			}
			else
			{
				curr_time += job[i];
				assigned.add(job[i]);
				i++;
			}
		}
		return (cnt <= K);
	}

	static int findMinTime(int K,int job[])
	{
		int end = 0, start = 0 , job_max = 0;
		for (int i = 0; i < job.length; ++i)
		{
			end += job[i];
			if (job[i] > job_max)
				job_max = job[i];
		}
		int ans = end;
		while (start <= end)
		{
			int mid = (start + end) / 2;
			if (mid >= job_max && isPossible(mid, K, job))
			{

				finalA = new ArrayList<ArrayList<Integer>>(dist);
				ans = Math.min(ans, mid);
				end = mid - 1;
			}

			else
				start = mid + 1;
		}
		return (ans);
	}
	public static String efficient(int k , int[] l)
	{
		int minTime = findMinTime(k,l);
		String result = "";
		result += minTime;
		for(ArrayList<Integer> a : finalA)
		{
			result += ";";
			for (int i = 0 ; i < a.size()-1 ; i++)
				result = result + a.get(i) + ",";
			result += a.get(a.size()-1);
		}
		return result;
	}
	public static void main(String arg[])
	{
		int job[] = {66,193,110,125,194,94,166,32,67,121,102,24,21,82,121,153,64,174,17,115,55,193,140,17,199,137,62,151,118,25,183,47,167,166,61,152,98,74,44,108,39,66,130,13,171,14,120,155,147,13,130,124,197,195,111,81,15,64,91,8,23,93,10,139,185,174,160,133,167,62,164,188,42,116,51,160,159,57,168,22,173,82,137,129,51,96,149,118,167,113,97,114,0,42,98,42,149,68,156,54,30,153,33,60,192,17,86,181,39,133,159,85,155,79,139,76,45,36,58,20,179,55,10,63,71,12,193,199,142,125,53,121,4,12,95,199,108,198,7,88,165,144,8,142,73,65,116,179,119,188,162,121,160,136,91,21,22,181,37,159,57,198,167,9,171,93,19,161,43,71,33,77,159,134,120,194,50,141,31,137,13,163,162,23,25,143,32,175,83,44,57,62,161,81,69,2,17,184,136,144};
		int k = 50;
		String result = "468;66,193,110;125,194,94;166,32,67,121;102,24,21,82,121;153,64,174,17;115,55,193;140,17,199;137,62,151,118;25,183,47,167;166,61,152;98,74,44,108,39,66;130,13,171,14,120;155,147,13,130;124,197;195,111,81,15,64;91,8,23,93,10,139;185,174;160,133,167;62,164,188,42;116,51,160;159,57,168,22;173,82,137;129,51,96,149;118,167,113;97,114,0,42,98,42;149,68,156,54,30;153,33,60,192,17;86,181,39,133;159,85,155;79,139,76,45,36,58,20;179,55,10,63,71,12;193,199;142,125,53,121,4,12;95,199,108;198,7,88,165;144,8,142,73,65;116,179,119;188,162;121,160,136;91,21,22,181,37;159,57,198;167,9,171,93,19;161,43,71,33,77;159,134,120;194,50,141,31;137,13,163;162,23,25,143,32;175,83,44,57,62;161,81,69,2,17;184,136,144";
		System.out.println(result.equals(efficient(k, job)));
		System.out.println();
	}
}
