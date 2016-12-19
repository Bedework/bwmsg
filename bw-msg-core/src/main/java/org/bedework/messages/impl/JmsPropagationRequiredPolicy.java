/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.messages.impl;

import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Configures a SpringTransactionPolicy using the PROPAGATION_REQUIRED policy. 
 * User: mike
 * Date: 12/15/16
 * Time: 14:15
 */
@Named("PROPAGATION_REQUIRED")
public class JmsPropagationRequiredPolicy extends
        SpringTransactionPolicy {
  @Inject
  public JmsPropagationRequiredPolicy(final JmsTransactionManager cdiTransactionManager) {
    super(new TransactionTemplate(cdiTransactionManager,
                                  new DefaultTransactionDefinition(
                                          TransactionDefinition.PROPAGATION_REQUIRED)));
  }
}
