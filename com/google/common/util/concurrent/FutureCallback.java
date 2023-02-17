package com.google.common.util.concurrent;

import javax.annotation.Nullable;

public interface FutureCallback<V> {
  void onSuccess(@Nullable V paramV);
  
  void onFailure(Throwable paramThrowable);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\google\commo\\util\concurrent\FutureCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */