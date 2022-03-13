
public class Server {
	private String name;
	private int Capacity;
	private int length;
	private int pool;
	
	public String getName() {
		return name;
	}
	public int getCapacity() {
		return Capacity;
	}
	public int getLength() {
		return length;
	}
	public int getPool() {
		return pool;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCapacity(int capacity) {
		Capacity = capacity;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setPool(int pool) {
		this.pool = pool;
	}
	
	public Server(String name, int capacity,int length) {
		this.setName(name);
		this.setCapacity(capacity);
		this.setLength(length);
	}
	
}
