package jss2;

public class DropOutStack<T> extends LinkedStack<T> {
	
	private int maxStackSize;
	
	/**
	 * Default constructor will set a default stack size.
	 */
	public DropOutStack() {
		this.maxStackSize = 10;
	}
	
	/**
	 * Constructor that will set the stack size by the parameter.
	 * @param size int represents the size of the stack.
	 */
	public DropOutStack(int size) {
		setStackSize(size);
	}
	
	/**
	 * Pushes an element onto the stack. Will remove bottom stack
	 * @param element <T> puts element onto the top of the stack
	 */
	public void push (T element){
		super.push(element);
		
		if (super.size() > this.maxStackSize){
			LinkedStack<T> tempStack = new LinkedStack<T>();
			
			while (!super.isEmpty()){
				tempStack.push(super.pop());
			}
			
			tempStack.pop();
			
			while (!tempStack.isEmpty()){
				super.push(tempStack.pop());
			}
		}
	}
	
	/**
	 * Sets the size of the stack.
	 * @param size int represents the stack size
	 * @throws NumberFormatException when the input is not a valid number.
	 */
	private void setStackSize(int size) {
		
		if (size <= 0) {
			throw new NumberFormatException(size + " is not a valid size. Only numbers greater than 0 work.");
		}
		this.maxStackSize = size;
	}
	
	/**
	 * Gets the max stack size
	 * @return int returns the max stack size
	 */
	public int getMaxStackSize(){
		return maxStackSize;
	}
}
