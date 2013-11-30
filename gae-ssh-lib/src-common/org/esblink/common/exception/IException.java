package org.esblink.common.exception;


public abstract interface IException
{
  public static final int BIZ_EXCEPTION_CODE = 99902000;
  public static final int OPERATION_EXCEPTION_CODE = 99902001;
  public static final int CONFIG_EXCEPTION_CODE = 99903001;
  public static final int NETWORK_EXCEPTION_CODE = 99904000;
  public static final int SESSION_TIMEOUT_EXCEPTION_CODE = 99904001;
  public static final int BAD_SQL_GRAMMAR_EXCEPTION_CODE = 99905000;
  public static final int CANNOT_ACQUIRE_LOCK_EXCEPTION_CODE = 99905001;
  public static final int CANNOT_GET_JDBC_CONNECTION_EXCEPTION_CODE = 99905002;
  public static final int CANNOT_SERIALIZE_TRANSACTION_EXCEPTION_CODE = 99905003;
  public static final int CLEANUP_FAILURE_DATAACCESS_EXCEPTION_CODE = 99905004;
  public static final int CONCURRENCY_FAILURE_EXCEPTION_CODE = 99905005;
  public static final int DATAACCESS_RESOURCE_FAILURE_EXCEPTION_CODE = 99905006;
  public static final int DATA_INTEGRITY_VIOLATION_EXCEPTION_CODE = 99905007;
  public static final int DATA_RETRIEVAL_FAILURE_EXCEPTION_CODE = 99905008;
  public static final int DEAD_LOCKLOSER_DATAACCESS_EXCEPTION_CODE = 99905009;
  public static final int EMPTY_RESULT_DATAACCESS_EXCEPTION_CODE = 99905010;
  public static final int INCORRECT_RESULTSET_COLUMNCOUNT_EXCEPTION_CODE = 99905011;
  public static final int INCORRECT_RESULT_SIZE_DATAACCESS_EXCEPTION_CODE = 99905012;
  public static final int INCORRECT_UPDATE_SEMANTICS_DATAACCESS_EXCEPTION_CODE = 99905013;
  public static final int INVALID_DATAACCESS_API_USAGE_EXCEPTION_CODE = 99905014;
  public static final int INVALID_DATAACCESS_RESOURCE_USAGE_EXCEPTION_CODE = 99905015;
  public static final int INVALID_RESULTSET_ACCESS_EXCEPTION_CODE = 99905016;
  public static final int JDBCUPDATE_AFFECTED_INCORRECTNUMBEROFROWS_EXCEPTION_CODE = 99905017;
  public static final int LOB_RETRIEVAL_FAILURE_EXCEPTION_CODE = 99905018;
  public static final int OPTIMISTIC_LOCKING_FAILURE_EXCEPTION_CODE = 99905019;
  public static final int PERMISSION_DENIED_DATAACCESS_EXCEPTION_CODE = 99905020;
  public static final int PESSIMISTIC_LOCKING_FAILURE_EXCEPTION_CODE = 99905021;
  public static final int SQL_WARNING_EXCEPTION_CODE = 99905022;
  public static final int TYPE_MISMATCH_DATAACCESS_EXCEPTION_CODE = 99905023;
  public static final int UNCATEGORIZED_DATAACCESS_EXCEPTION_CODE = 99905024;
  public static final int UNCATEGORIZED_SQL_EXCEPTION_CODE = 99905025;
  public static final int JDBC_EXCEPTION_CODE = 99905026;
  public static final int UNKNOW_DAO_EXCEPTION_CODE = 99905027;
  public static final int DB_CONNECTION_CODE = 99906001;
  public static final int DB_STRUCTURE_CODE = 99906002;
  public static final int DB_OPERATION_CODE = 99906003;
  public static final int DB_UNKOWN_CODE = 99906004;

  public abstract String getMessageKey();

  public abstract Object[] getMessageArgs();

  public abstract int getCode();

  public abstract ExceptionLevel getLevel();

  public abstract ExceptionSource getSource();
}


 
 
