package cn.antido.connection.service.list;

/**
 * @Description 缓冲区链表<br>
 * 所有对CloseCheckList的元素添加操作都会讲元素先放置在这里<br>
 * 当在获取MainList迭代器之前将TempList中的元素合并到MainList中<br>
 * 获取MainList迭代器时会持有本链表的对象锁 阻止新增操作
 * @author Antido
 * @date 2018年7月3日 下午2:05:44
 */
class TempList<E> {
	
	protected Node<E> head;
	protected Node<E> tail;
	
	public synchronized void add(E item) {
		Node<E> node = new Node<E>(item);
		
		if(isEmpty()) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public synchronized void removeAll() {
		head = null;
		tail = null;
	}
	
}
