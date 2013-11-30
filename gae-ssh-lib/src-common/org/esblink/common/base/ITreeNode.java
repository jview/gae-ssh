package org.esblink.common.base;

import java.util.Iterator;

public abstract interface ITreeNode<T>
{
  public abstract void addChild(T paramT);

  public abstract T get();

  public abstract void set(T paramT);

  public abstract ITreeNode<T> getParent();

  public abstract boolean isRoot();

  public abstract Iterator<ITreeNode<T>> getChildren();

  public abstract boolean isLeaf();

  public abstract int getChildCount();

  public abstract ITreeNode<T> getChildAt(int paramInt);

  public abstract int getChildIndex(ITreeNode<T> paramITreeNode);
}


 
 
