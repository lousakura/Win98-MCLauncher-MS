package com.google.common.io;

import com.google.common.annotations.Beta;
import java.io.IOException;

@Beta
public interface ByteProcessor<T> {
  boolean processBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  T getResult();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\google\common\io\ByteProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */