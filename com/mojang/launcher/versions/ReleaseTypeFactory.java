package com.mojang.launcher.versions;

public interface ReleaseTypeFactory<T extends ReleaseType> extends Iterable<T> {
  T getTypeByName(String paramString);
  
  T[] getAllTypes();
  
  Class<T> getTypeClass();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\versions\ReleaseTypeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */