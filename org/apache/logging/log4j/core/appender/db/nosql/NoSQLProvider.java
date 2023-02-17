package org.apache.logging.log4j.core.appender.db.nosql;

public interface NoSQLProvider<C extends NoSQLConnection<?, ? extends NoSQLObject<?>>> {
  C getConnection();
  
  String toString();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */