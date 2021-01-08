package domain;

import java.util.Stack;

public class GenericClassStack<T> {
    private Stack<T> stack;

    public Stack<T> getStack() {
        return stack;
    }

    public void setStack(Stack<T> stack) {
        this.stack = stack;
    }
}
