
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Number6 {
	static int[] unf;
	
	// v의 root 노드 찾기
	static int find(int v) {
		if(v==unf[v]) return v;
		return find(unf[v]);
	}
	static void union(int a, int b) {
		int fa = find(a);
		int fb = find(b);
		if(fa!=fb) unf[fa]=fb;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		unf = new int[n+1];
		
		for(int i=1; i<=n; i++) {
			unf[i] = i;
		}
		
		for(int i=1; i<=m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			union(a, b);
		}
		
		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		
		int fx = find(x);
		int fy = find(y);
		
		if(fx==fy) {
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}
	}
}
