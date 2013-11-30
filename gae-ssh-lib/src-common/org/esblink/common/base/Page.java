package org.esblink.common.base;
 
 import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
 
 public class Page<T>
   implements IPage<T>
 {
   private long totalSize;
   private int pageSize;
   private long totalPage;
   private long currentPage;
   private Collection<T> data;
 
   public Page(Collection<T> data, long totalSize, int pageSize, int currentPage)
   {
     this.data = (data == null ? new ArrayList(0) : data);
     this.totalSize = totalSize;
     this.currentPage = currentPage;
     this.pageSize = pageSize;
     this.totalPage = calcTotalPage();
   }
 
   public long getTotalSize() {
     return this.totalSize;
   }
 
   public int getPageSize() {
     return this.pageSize;
   }
 
   private long calcTotalPage() {
     long t = getTotalSize();
     long p = getPageSize();
     if ((t == 0L) || (p == 0L))
       return 0L;
     long r = t % p;
     long pages = (t - r) / p;
     if (r > 0L)
       pages += 1L;
     return pages;
   }
 
   public long getTotalPage() {
     return this.totalPage;
   }
 
   public long getCurrentPage() {
     return this.currentPage;
   }
 
   public long getPageBegin() {
     return this.pageSize * this.currentPage;
   }
 
   public long getPageEnd() {
     return getPageBegin() + getData().size();
   }
 
   public Collection<T> getData() {
     return this.data;
   }
 
   public boolean isFirstPage() {
     return this.currentPage == 0L;
   }
 
   public boolean isLastPage() {
     return this.currentPage == this.totalPage;
   }
 
   public boolean add(T o) {
     return this.data.add(o);
   }
 
   public boolean addAll(Collection<? extends T> c) {
     return this.data.addAll(c);
   }
 
   public void clear() {
     this.data.clear();
   }
 
   public boolean contains(Object o) {
     return this.data.contains(o);
   }
 
   public boolean containsAll(Collection<?> c) {
     return this.data.containsAll(c);
   }
 
   public boolean equals(Object o) {
     return this.data.equals(o);
   }
 
   public int hashCode() {
     return this.data.hashCode();
   }
 
   public boolean isEmpty() {
     return this.data.isEmpty();
   }
 
   public Iterator<T> iterator() {
     return this.data.iterator();
   }
 
   public boolean remove(Object o) {
     return this.data.remove(o);
   }
 
   public boolean removeAll(Collection<?> c) {
     return this.data.removeAll(c);
   }
 
   public boolean retainAll(Collection<?> c) {
     return this.data.retainAll(c);
   }
 
   public int size() {
     return this.data.size();
   }
 
   public Object[] toArray() {
     return this.data.toArray();
   }
 
   public <T> T[] toArray(T[] a) {
     return this.data.toArray(a);
   }
 }


 
 
