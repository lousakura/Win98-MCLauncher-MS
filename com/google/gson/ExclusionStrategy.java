package com.google.gson;

public interface ExclusionStrategy {
  boolean shouldSkipField(FieldAttributes paramFieldAttributes);
  
  boolean shouldSkipClass(Class<?> paramClass);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\google\gson\ExclusionStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */