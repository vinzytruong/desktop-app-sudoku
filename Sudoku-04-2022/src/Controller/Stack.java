package Controller;

import Components.JButtonCellBig;
import Model.Cell;

public class Stack {
	private JButtonCellBig data[];
	public JButtonCellBig[] getData() {
		return data;
	}
	public void setData(JButtonCellBig[] data) {
		this.data = data;
	}
	private int top;
	private final int maxLength=100;
	public Stack() {
		data=new JButtonCellBig[maxLength];
		top=maxLength;
	}
	public void push(JButtonCellBig c) {
		if(top>0) {
			top--;
			data[top]=c;
		}
	}
	public void pop() {
		if(top<maxLength) top++;
	}
	public JButtonCellBig top() {
		return data[top];
	}
	public boolean empty() {
		return top==maxLength;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
}
