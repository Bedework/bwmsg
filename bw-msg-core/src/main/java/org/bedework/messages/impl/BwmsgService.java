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

import org.bedework.messages.Bwmsg;
import org.bedework.util.config.ConfInfo;
import org.bedework.util.jmx.ConfBase;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author douglm
 */
public class BwmsgService extends ConfBase<BwmsgPropertiesImpl>
        implements BwmsgServiceMBean {
  /* Name of the property holding the location of the config data */
  private static final String confuriPname = "org.bedework.bwmsg.confuri";

  private transient Logger log;

  private Bwmsg bn;

  private final static String nm = "bwmsg";

  public BwmsgService() {
    super(getServiceName(nm));

    setConfigName(nm);
    setConfigPname(confuriPname);
  }
  
  /**
   * @param name of the service
   * @return object name value for the mbean with this name
   */
  @SuppressWarnings("WeakerAccess")
  public static String getServiceName(final String name) {
    return "org.bedework.bwmsg:service=" + name;
  }

  public static String getTheServiceName() {
    return getServiceName(nm);
  }

  @Override
  public void start() {
    if ((bn != null) && bn.isStarted()) {
      return;
    }

    bn = new BwmsgImpl(this);
    try {
      bn.start();
    } catch (final Throwable t) {
      error(t);
      bn = null;
    }
  }

  @Override
  public void stop() {
    if ((bn == null) || !bn.isStarted()) {
      return;
    }

    try {
      bn.stop();
    } catch (final Throwable t) {
      error(t);
    }
    bn = null;
  }

  @Override
  public String loadConfig() {
    return loadConfig(BwmsgPropertiesImpl.class);
  }

  @Override
  public void setSyseventsProperties(final List<String> val) {
    getConfig().setSyseventsProperties(val);
  }

  @Override
  @ConfInfo(collectionElementName = "syseventsProperty" ,
          elementType = "java.lang.String")
  public List<String> getSyseventsProperties() {
    return getConfig().getSyseventsProperties();
  }

  @Override
  public void addSyseventsProperty(final String name,
                                   final String val) {
    getConfig().addSyseventsProperty(name, val);
  }

  @Override
  public String getSyseventsProperty(final String name) {
    return getConfig().getSyseventsProperty(name);
  }

  @Override
  public void removeSyseventsProperty(final String name) {
    getConfig().removeSyseventsProperty(name);
  }

  @Override
  public void setSyseventsProperty(final String name,
                                   final String val) {
    getConfig().setSyseventsProperty(name, val);
  }
}
