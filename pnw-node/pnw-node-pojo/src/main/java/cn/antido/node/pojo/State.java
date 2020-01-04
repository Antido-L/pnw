package cn.antido.node.pojo;
/**
 * @Description 节点状态
 * @author Antido
 * @date 2018年3月16日 下午8:42:47
 */
public class State {
	private boolean alive;
	private boolean isClose;
	
	/**
	 * @param alive
	 * @param isClose
	 */
	public State(boolean alive, boolean isClose) {
		this.alive = alive;
		this.isClose = isClose;
	}
	
	public State() {
		
	}
	
	public boolean isAlive() {
		return alive;
	}

	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	
	public boolean isClose() {
		return isClose;
	}

	
	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}
	
}
