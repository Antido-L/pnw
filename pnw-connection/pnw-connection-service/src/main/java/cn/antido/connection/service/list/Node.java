package cn.antido.connection.service.list;

class Node<E>{
	public E item;
	public Node<E> prev;
	public Node<E> next;
	
	public Node(E item) {
		this.item = item;
	}
	
	public Node(E item, Node<E> prev, Node<E> next) {
		this.item = item;
		this.prev = prev;
		this.next = next;
	}
}
