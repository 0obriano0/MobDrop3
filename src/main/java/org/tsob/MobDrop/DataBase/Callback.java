package org.tsob.MobDrop.DataBase;

public interface Callback<T> {
  void onSuccess(T result);
  void onFailure(Exception e);
}
