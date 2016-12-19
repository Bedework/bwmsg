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

import org.bedework.messages.BwmsgProperties;
import org.bedework.util.config.ConfInfo;
import org.bedework.util.config.ConfigBase;
import org.bedework.util.misc.ToString;

import java.util.List;

/**
 * @author douglm
 *
 */
@ConfInfo(elementName = "bwmsg-properties",
          type = "org.bedework.notify.BwmsgProperties")
public class BwmsgPropertiesImpl
        extends ConfigBase<BwmsgPropertiesImpl>
        implements BwmsgProperties {
  private List<String> syseventsProperties;

  /* ========================================================================
   * Attributes
   * ======================================================================== */

  @Override
  public void setSyseventsProperties(final List<String> val) {
    syseventsProperties = val;
  }

  @Override
  @ConfInfo(collectionElementName = "syseventsProperty")
  public List<String> getSyseventsProperties() {
    return syseventsProperties;
  }

  @Override
  public void addSyseventsProperty(final String name,
                                   final String val) {
    setSyseventsProperties(addListProperty(getSyseventsProperties(),
                                           name, val));
  }

  @Override
  public String getSyseventsProperty(final String name) {
    return getProperty(getSyseventsProperties(), name);
  }

  @Override
  public void removeSyseventsProperty(final String name) {
    removeProperty(getSyseventsProperties(), name);
  }

  @Override
  public void setSyseventsProperty(final String name,
                                   final String val) {
    setSyseventsProperties(setListProperty(getSyseventsProperties(),
                                           name, val));
  }

  /* ====================================================================
   *                   Object methods
   * ==================================================================== */

  @Override
  public String toString() {
    final ToString ts = new ToString(this);

    return ts.toString();
  }
}
