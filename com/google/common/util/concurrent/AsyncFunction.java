package com.google.common.util.concurrent;

public interface AsyncFunction<I, O> {
  ListenableFuture<O> apply(I paramI) throws Exception;
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\google\commo\\util\concurrent\AsyncFunction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */