package Model;

public class Number {
	public int num[];
	public boolean used[];
	public Number() {
		num=new int[9];
		used=new boolean[9];
		for(int i=0;i<9;i++) {
			num[i]=i+1;
			used[i]=false;
		}
	}
	public Number(Number nb) {
		this.num=new int[9];
		this.used=new boolean[9];
		for(int i=0;i<9;i++) {
			this.num[i]=nb.num[i];
			this.used[i]=nb.used[i];
		}
	}
	public void showNum() {
		for(int i=0;i<9;i++) {
			if(used[i]==false) System.out.print(num[i]+" ");
		}
		System.out.println();
	}
	public void resetNum() {
		for(int i=0;i<9;i++) {
			num[i]=i+1;
			used[i]=false;
		}
	}
	public void markUsed(int number) {
		used[number-1]=true;
	}
	public int countNotUse() {
		int count=9;
		for(int i=0;i<9;i++) {
			if(used[i]==true) count--;
		}
		return count;
	}
	
	public static void main(String[] args) {
		Number nb=new Number();
		nb.showNum();
	}
}
