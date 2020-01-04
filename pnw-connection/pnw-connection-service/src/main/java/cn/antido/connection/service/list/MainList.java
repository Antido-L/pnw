package cn.antido.connection.service.list;

import java.util.Iterator;
/**
 * @Description 用于存放可迭代的监听对象
 * @author Antido
 * @date 2018年7月3日 下午2:05:01
 */
class MainList<E> implements Iterable<E> {
	
	protected Node<E> head;
	protected Node<E> tail;
	
	public void add(E item) {
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
	
	public E popHead() {
		if(isEmpty()) 
			return null;
		
		E item = head.item;
		
		if(head == tail) {
			head = null;
			tail = null;
		} else {
			head = head.next;
			head.prev = null;
		}
		
		return item;
	}
	
	public E popTail() {
		if(isEmpty()) return null;
		E item = tail.item;
		
		if(head == tail) {
			head = null;
			tail = null;
		} else {
			tail = tail.prev;
			tail.next = null;
		}
		
		return item;
	}
	
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public Iterator<E> iterator() {
		
		return new ListIterator();
	}
	
	/**
	 * @Description 主链表迭代器<br>
	 * 该迭代器的运行过程中会一直持有被迭代链表对象的对象锁
	 * @author Antido
	 * @date 2018年7月3日 下午2:01:15
	 */
	class ListIterator implements Iterator<E>{
		
		private Node<E> curr = MainList.this.head;
		private Node<E> prev = null;

		@Override
		public boolean hasNext() {
			synchronized (MainList.this) {
				return curr != null;
			}
		}

		@Override
		public E next() {
			synchronized (MainList.this) {
				E item = curr.item;
				prev = curr;
				curr = curr.next;
				return item;
			}
		}
		
		/**
		 * 在链表中移除next()方法返回的元素
		 */
		@Override
		public void remove() {
			synchronized (MainList.this) {
				if(prev == null) {
					throw new RuntimeException("迭代器只能移除当前迭代的元素");
				}
				
				if(prev == MainList.this.head) {
					//更改父类指针,节点只有一个时 此时head==tail
					MainList.this.head = MainList.this.head.next;
					//断链
					if(curr != null) { //当不是唯一节点时
						prev.next.prev.prev = null;
					}
					prev.next = null;
				} else {
					//当遍历到最后一个节点时
					if(prev == MainList.this.tail) {
						//更改父类指针,节点只有一个时 此时head==tail
						MainList.this.tail = MainList.this.tail.prev;
						
						prev.prev.next = null;
						prev.prev = null;
					} else {
						try {
							//断链
							prev.next.prev = prev.prev;
							prev.prev.next = prev.next;
							
						} catch (NullPointerException e) {
							System.out.println(prev);
							System.out.println(prev.item);
							e.printStackTrace();
						}
					}
				} 
				prev = null;
			}
		}
		
	}
}
