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
package org.bedework.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Represents a subscriber to the notification system. A subscriber may have
 * zero or more current subscriptions.
 *
 * @author Mike Douglass   douglm  rpi.edu
 */
public class Subscriber implements Serializable {
  private static final long serialVersionUID = 1;

  private String name;

  private boolean system;

  private List<Subscription> subscriptions;

  /**
   * @param val
   */
  public void setName(final String val) {
    name = val;
  }

  /**
   * @return name of subscriber
   */
  public String getName() {
    return name;
  }

  /**
   * @param val
   */
  public void setSystem(final boolean val) {
    system = val;
  }

  /**
   * @return true for a system subscriber
   */
  public boolean getSystem() {
    return system;
  }

  /**
   * @param val
   */
  public void setSubscriptions(final List<Subscription> val) {
    subscriptions = val;
  }

  /**
   * @return list of subscriptions or null
   */
  public List<Subscription> getSubscriptions() {
    return subscriptions;
  }

  /**
   * @param val
   * @return true if collection changed
   */
  public boolean addSubscription(final Subscription val) {
    if (subscriptions == null) {
      subscriptions = new ArrayList<Subscription>();
    }

    return subscriptions.add(val);
  }

  /**
   * @param val
   * @return true if removed
   */
  public boolean removeSubscription(final Subscription val) {
    if (subscriptions == null) {
      return false;
    }

    return subscriptions.remove(val);
  }
}
