package joptsimple;

public interface ValueConverter<V> {
  V convert(String paramString);
  
  Class<V> valueType();
  
  String valuePattern();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\joptsimple\ValueConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */