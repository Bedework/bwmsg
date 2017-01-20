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

import org.bedework.messages.exc.MsgException;


/** <p>Sources, destinations and channels are addressed by objects implementing
 * this interface which extends the URI concept.
 *
 * Internally the address may take the form bwmsg:&lt;path&gt;. External
 * addresses will have the scheme part "bwmsg:" replaced by the scheme, host,
 * port and context of the remote service.
 *
 * <p>The path part consists of a notification context followed by path elements.
 * The notification context may be reserved by subscribers with sufficient
 * privileges or by the system itself.
 *
 * <p>Note that a notification content is NOT the servlet context. This forms
 * the prefix of an external address which has the form <br/>
 * &lt;external-prefix&gt;&lt;absolute-path&gt;
 *
 * <p>Implementations of this interface MUST be immutable. The system will use
 * the object as a key.
 *
 * @author Mike Douglass   douglm  rpi.edu
 */
public interface BwmsgAddress {
  /**
   * @return false if this is an invalid address. i.e. it was removed from the
   * system.
   */
  boolean isValid();

  /** Invalidate this address - i.e. remove it from the system.
   *
   * @throws MsgException if already invalid or entity state prevents unbind
   */
  void invalidate() throws MsgException;

  /** Get the bound entity if one exists.
   *
   * @return entity or null.
   */
  Addressable getAddressed();
}
