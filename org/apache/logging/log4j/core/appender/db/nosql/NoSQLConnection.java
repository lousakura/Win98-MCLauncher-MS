package org.apache.logging.log4j.core.appender.db.nosql;

import java.io.Closeable;

public interface NoSQLConnection<W, T extends NoSQLObject<W>> extends Closeable {
  T createObject();
  
  T[] createList(int paramInt);
  
  void insertObject(NoSQLObject<W> paramNoSQLObject);
  
  void close();
  
  boolean isClosed();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */