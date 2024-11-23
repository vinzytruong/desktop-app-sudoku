package Controller;

import View.JButtonCell;

public class Stack {

    private JButtonCell data[];
    private int top;
    private final int maxLength = 100;

    public JButtonCell[] getData() {
        return data;
    }

    public void setData(JButtonCell[] data) {
        this.data = data;
    }

    public Stack() {
        data = new JButtonCell[maxLength];
        top = maxLength;
    }
    public boolean checkIdentical(JButtonCell c) {
        int t = top;

        System.out.println(t+","+maxLength);
        while(t < maxLength) {
            if (data[t].getI() == c.getI() && data[t].getJ() == c.getJ()) {
                return true;
            }
            else t++;
        }
        return false;
    }

    public void push(JButtonCell c) {
        if (top > 0) {
            top--;
            data[top] = c;
        }
    }

    public void pop() {
        if (top < maxLength) {
            top++;
        }
    }

    public JButtonCell top() {
        return data[top];
    }

    public boolean empty() {
        return top == maxLength;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
