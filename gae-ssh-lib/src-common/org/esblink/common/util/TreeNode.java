 package org.esblink.common.util;
 
 import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.esblink.common.base.ITreeNode;
 
 public class TreeNode<T>
   implements ITreeNode<T>
 {
   private T data;
   private final ITreeNode<T> parent;
   private final List<ITreeNode<T>> children = new ArrayList();
 
   public TreeNode()
   {
     this(null, null);
   }
 
   public TreeNode(T data)
   {
     this(data, null);
   }
 
   public TreeNode(T data, ITreeNode<T> parent)
   {
     this.data = data;
     this.parent = parent;
   }
 
   public void addChild(T data) {
     this.children.add(new TreeNode(data, this));
   }
 
   public T get()
   {
     return this.data;
   }
 
   public ITreeNode<T> getParent()
   {
     return this.parent;
   }
 
   public boolean isRoot() {
     return this.parent == null;
   }
 
   public Iterator<ITreeNode<T>> getChildren()
   {
     return this.children.iterator();
   }
 
   public boolean isLeaf() {
     return this.children.size() == 0;
   }
 
   public int getChildCount() {
     return this.children.size();
   }
 
   public ITreeNode<T> getChildAt(int childIndex) {
     return (ITreeNode)this.children.get(childIndex);
   }
 
   public int getChildIndex(ITreeNode<T> node) {
     return this.children.indexOf(node);
   }
 
   public int indexOf(ITreeNode<T> node)
   {
     return 0;
   }
 
   public void set(T data) {
     this.data = data;
   }
 }


 
 
