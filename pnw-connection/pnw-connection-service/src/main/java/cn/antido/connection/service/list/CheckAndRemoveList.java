package cn.antido.connection.service.list;

import java.util.Iterator;

/**
 * @Description 需要被持续监控锁状态的元素链表<br>
 * 该链表的add()方法为同步方法,元素的添加ConfirmLimitListener线程完成,该线程在目前的设计中为单线程<br>
 * 当遍历此链表时请使用iterator()方法获取该链表的迭代器,该迭代器重写了remove方法可以删除当前元素<br>
 * 在迭代过程中可以进行添加操作,但不会在本次迭代中出现<br>
 * 
 * TODO: 这个数据结构需要改变的地方还有许多,等有时间的
 * 
 * @author Antido
 * @date 2018年7月2日 上午10:53:00
 */
public class CheckAndRemoveList<E> implements Iterable<E> {
	
	/**
	 * 主链表,用于存放被迭代的元素<br>
	 * 使用双向链表
	 */
	private MainList<E> mainList;
	private TempList<E> tempList;
	
	/**
	 * 初始化链表
	 */
	public CheckAndRemoveList() {
		this.mainList = new MainList<E>();
		this.tempList = new TempList<E>();
	}
	
	/**
	 * 新增监听对象
	 * @param item
	 */
	public void add(E item) {
		//将新加入的对象先放入tempList中
		tempList.add(item);
	}
	
	public boolean isEmpty() {
		return mainList.isEmpty() & tempList.isEmpty();
	}
	
	/**
	 * 获取检查对象链表迭代器<br>
	 * 该迭代器含有remove方法
	 */
	@Override
	public Iterator<E> iterator() {
		//锁住tempList,将缓冲区的元素放入监视区中
		synchronized(tempList) {
			merge();
			//返回监视区迭代器
			return mainList.iterator();
		}
	}
	
	/**
	 * 将缓冲链表中的元素放入监听链表中<br>
	 */
	private void merge() {
		if(tempList.isEmpty()) return;
		
		if(mainList.isEmpty()) { 
			//主链表为空时 直接将缓冲区移动到主链表中
			mainList.head = tempList.head;
			mainList.tail = tempList.tail;
		} else {
			//主链表不为空是合并链表
			Node<E> mainTail = mainList.tail;
			Node<E> tempHead = tempList.head;
			mainTail.next = tempHead;
			tempHead.prev = mainTail;
			
			mainList.tail = tempList.tail;
		}
		
		//清空缓冲区
		tempList.removeAll();
	}
	
}
