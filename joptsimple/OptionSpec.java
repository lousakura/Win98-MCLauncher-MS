package joptsimple;

import java.util.Collection;
import java.util.List;

public interface OptionSpec<V> {
  List<V> values(OptionSet paramOptionSet);
  
  V value(OptionSet paramOptionSet);
  
  Collection<String> options();
  
  boolean isForHelp();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\joptsimple\OptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */