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
import java.util.Set;


/** <p>Represents a single subscription for a subscriber.
 *
 * @author Mike Douglass   douglm  rpi.edu
 */
public class Subscription implements Serializable {
  private static final long serialVersionUID = 1;

  private String kind;

  private String destination;

  /** Parameters for the destination
   */
  public static class Param implements Serializable {
    private static final long serialVersionUID = 1;

    private String name;

    private String value;

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
    public void setValue(final String val) {
      value = val;
    }

    /**
     * @return name of subscriber
     */
    public String getValue() {
      return value;
    }

    @Override
    public int hashCode() {
      return getName().hashCode();
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }

      if (!(o instanceof Param)) {
        return false;
      }

      return getName().equals(((Param)o).getName());
    }
  }

  private Set<Param> params;


  /**
   * @param val
   */
  public void setKind(final String val) {
    kind = val;
  }

  /**
   * @return kind of event
   */
  public String getKind() {
    return kind;
  }

  /**
   * @param val
   */
  public void setDestination(final String val) {
    destination = val;
  }

  /**
   * @return url of destination
   */
  public String getDestination() {
    return destination;
  }

  /**
   * @param val
   */
  public void setParams(final Set<Param> val) {
    params = val;
  }

  /**
   * @return parameters for destination
   */
  public Set<Param> getParams() {
    return params;
  }
}
