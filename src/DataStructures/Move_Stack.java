package DataStructures;

import Movement.Move;

public class Move_Stack {

	    static final int MAX = 1000;
	    int top;
	    Move a[] = new Move[MAX]; // Maximum size of Stack
	 
	    boolean isEmpty()
	    {
	        return (top < 0);
	    }
	    
	    public Move_Stack(){
	        top = -1;
	    }
	 
	    public boolean push(Move x)
	    {
	        if (top >= (MAX - 1)) {
	            return false;
	        }
	        else {
	            a[++top] = x;
	            return true;
	        }
	    }
	 
	    public Move pop()
	    {
	        if (top < 0) {
	            return null;
	        }
	        else {
	            Move x = a[top--];
	            return x;
	        }
	    }
	 
	    public Move peek()
	    {
	        if (top < 0) {
	            return null;
	        }
	        else {
	            Move x = a[top];
	            return x;
	        }
	    }
	    
	    void print(){
	    for(int i = top;i>-1;i--){
	      System.out.print(" "+ a[i]);
	    }
	  }
	
	
}
