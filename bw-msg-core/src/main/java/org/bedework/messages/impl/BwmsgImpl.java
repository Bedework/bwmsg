/* ********************************************************************
    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.
*/
package org.bedework.messages.impl;

import org.bedework.messages.Addressable;
import org.bedework.messages.Bwmsg;
import org.bedework.messages.BwmsgAddress;
import org.bedework.messages.BwmsgProperties;
import org.bedework.messages.exc.MsgException;
import org.bedework.util.misc.Logged;
import org.bedework.util.misc.Util;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsMessageType;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.ExecutorServiceManager;
import org.apache.camel.spi.ThreadPoolProfile;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.net.URI;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

/** Note - As noted here: https://access.redhat.com/solutions/250253
 * we should use the option mapJmsMessage to avoid deserialization of 
 * messages - which is more expensive and requires all message classes 
 * be on the classpath. For example:
 * <pre>
 *   from("jms:queue:myQueue?mapJmsMessage=false").
 *   to("jms:queue:destQueue");
 * </pre>
 * 
 *
 * @author Mike Douglass   douglm  rpi.edu
 */
public class BwmsgImpl extends Logged implements Bwmsg {
  private boolean debug;

  private transient Logger log;
  
  private final BwmsgProperties bnprops;

  private static final String syseventsInQueue =
      // JNDIname "jms:queue:bedeworkSysevents"
      "jms:queue:bedework.sysevents"; /// actual name

  private static final String syseventsLoggerQueue =
      // JNDIname "jms:queue:syseventsLogger"
      "jms:queue:bedework.sysevents.logger"; /// actual name

  private static final String syseventsMonitorQueue =
      // JNDIname "jms:queue:syseventsMonitor"
      "jms:queue:bedework.sysevents.monitor"; /// actual name

  private static final String syseventsCrawlerQueue =
      // JNDIname "jms:queue:syseventsCrawler"
      "jms:queue:bedework.crawler"; /// actual name

  private static final String syseventsChangesQueue =
          // JNDIname "jms:queue:syseventsChanges"
          "jms:queue:bedework.sysevents.changes"; /// actual name

  private static final String syseventsSchedInQueue =
      // JNDIname "jms:queue:bedeworkScheduleIn"
      "jms:queue:bedework.scheduleIn"; /// actual name

  private static final String syseventsSchedOutQueue =
      // JNDIname "jms:queue:bedeworkScheduleOut"
      "jms:queue:bedework.scheduleOut"; /// actual name

  private static volatile Properties pr;

  private static final Object lockit = new Object();

  private CamelContext context;
  boolean started;

  private static class BwmsgRouteBuilder extends RouteBuilder {
    private final JmsTransactionManager transactionManager;
    
    BwmsgRouteBuilder(final JmsTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
    }
    
    @Override
    public void configure() {
      final JmsPropagationRequiredPolicy policy = 
              new JmsPropagationRequiredPolicy(transactionManager);
      
      from(syseventsInQueue + "?mapJmsMessage=false")
              .setHeader("CamelJmsMessageType", constant(JmsMessageType.Text))
              .policy(policy)
              .multicast().parallelProcessing()
              .to(syseventsLoggerQueue,
                  syseventsMonitorQueue,
                  syseventsCrawlerQueue,
                  syseventsChangesQueue,
                  "direct:schedIn",
                  "direct:schedOut");

      from("direct:schedIn")
              .filter(simple("${header.inbox} == 'true' or ${header.scheduleEvent} == 'true'"))
              .to(syseventsSchedInQueue);

      from("direct:schedOut")
              .filter(simple("${header.outbox} == 'true'"))
              .to(syseventsSchedOutQueue);
    }
  }
  
  public BwmsgImpl(final BwmsgProperties bnprops) {
    this.bnprops = bnprops;
  }

  @Override
  public BwmsgAddress fromUri(final URI uri) throws MsgException {
    return null;
  }

  @Override
  public BwmsgAddress fromPath(final String addr) throws MsgException {
    return null;
  }

  @Override
  public void bind(final BwmsgAddress address,
                   final Addressable entity) throws MsgException {

  }
  
  @Override
  public synchronized void start() throws MsgException {
    debug = getLogger().isDebugEnabled();

    if (started) {
      return;
    }

    try {
      ConnectionFactory connFactory = null;

      final Properties pr = getPr();
      final Context ctx = new InitialContext(pr);

      /* Wait around for the connection factory. We might get deployed before
         JMS is up.
       */
      do {
        try {
          connFactory = (ConnectionFactory)ctx.lookup(
                  pr.getProperty(
                          "org.bedework.connection.factory.name"));
        } catch (final Throwable t) {
          // Assume no JMS yet
          warn("No connection factory available - waiting");
          TimeUnit.SECONDS.sleep(2);
        }
      } while (connFactory == null);

      context = new DefaultCamelContext();
      final JmsComponent jmsc = 
              JmsComponent.jmsComponentTransacted(connFactory);
      
      final JmsTransactionManager transactionManager =
              new JmsTransactionManager();
      
      jmsc.setTransactionManager(transactionManager);              
      context.addComponent("jms", jmsc);
      context.addRoutes(new BwmsgRouteBuilder(transactionManager));

      final ExecutorServiceManager esm = context.getExecutorServiceManager();
      final ThreadPoolProfile profile = esm.getDefaultThreadPoolProfile();
      profile.setMaxPoolSize(50);

      //context.setTracing(true);
      //context.getProperties().put(Exchange.LOG_DEBUG_BODY_STREAMS, "true");

      context.start();
      started = true;
    } catch (final Throwable t) {
      throw new MsgException(t);
    }
  }

  @Override
  public boolean isStarted() {
    return started;
  }

  @Override
  public void stop() throws MsgException {
    if (!started) {
      return;
    }

    try {
      context.stop();
      started = false;
    } catch (final Throwable t) {
      throw new MsgException(t);
    }
  }

  /* Get a logger for messages
   */
  protected Logger getLogger() {
    if (log == null) {
      log = Logger.getLogger(this.getClass());
    }

    return log;
  }

  /* ====================================================================
   *                   Protected methods
   * ==================================================================== */

  private Properties getPr() throws MsgException {
    synchronized (lockit) {
      if (pr != null) {
        return pr;
      }

      /* Load properties from config */

      try {
        pr = new Properties();

        if (Util.isEmpty(bnprops.getSyseventsProperties())) {
          throw new MsgException("No sysevents properties");
        }
        
        final StringBuilder sb = new StringBuilder();

        @SuppressWarnings("unchecked")
        final List<String> ps = bnprops.getSyseventsProperties();

        for (final String p: ps) {
          sb.append(p);
          sb.append("\n");
        }

        pr.load(new StringReader(sb.toString()));

        return pr;
      } catch (final MsgException cee) {
        throw cee;
      } catch (final Throwable t) {
        Logger.getLogger(BwmsgImpl.class).error("getEnv error", t);
        throw new MsgException(t.getMessage());
      }
    }
  }
}
