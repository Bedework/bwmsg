/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.messages.impl;

import org.springframework.transaction.jta.JtaTransactionManager;

import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * User: mike
 * Date: 12/15/16
 * Time: 14:12
 */
@Named("transactionManager")
public class JmsTransactionManager extends JtaTransactionManager {
  private final TransactionManager transactionManager;

  private final UserTransaction userTransaction;

  public JmsTransactionManager() {
    try {
      final Context ctx = new InitialContext();

      transactionManager = (TransactionManager)ctx.lookup(
              "java:/TransactionManager");
      userTransaction = (UserTransaction)ctx.lookup(
              "java:jboss/UserTransaction");
      
      setTransactionManager(transactionManager);
      setUserTransaction(userTransaction);
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
