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

import org.bedework.messages.exc.NotifyException;

import java.net.URI;

/** A notification service which allows subscribers to the system to specify
 * notification messages they are interested in and how they should be delivered.
 *
 * <p>A subscriber may specify different delivery mechanisms depending upon the
 * type of the notification or even some characteristics of the notification,
 * such as category or source.
 *
 * <p>A subscriber is represented by an authenticated or unauthenticated
 * principal. An authenticated principal can create permanent subscriptions and
 * delivery will take place even while the subscriber is not connected.
 *
 * <p>An unauthenticated principal will be able to subscribe to notifications
 * but they subscriptions will disappear when the connection is closed.
 *
 * <p>A channel is a line of communication between a source and a destination.
 *
 * <p>Sources, destinations and channels are addressed by BwmsgAddress
 *
 * <p>All of this is a veneer on top of apache camel using activemq as a
 * transport mechanism.
 *
 * <p>Note that we can configure Camel to carry out some relatively complex
 * tasks. These in genral will be handled as static configurations which require
 * a restart and perhaps implementation.
 *
 * <p>In addition we provide a dynamically configurable service which will
 * allow subscription to named resources with the applciation of a filter and
 * delivery to other named resources, for example, subscribe to a source of
 * scheduling change events, aggregate them and deliver them via email.
 *
 * @see BwmsgAddress
 * @author Mike Douglass   douglm  rpi.edu
 */
public interface Bwmsg {
  /** Create or return an address using the URI as a key. The URI will not be
   * used by the created or returned object. The returned address is not bound
   * to any entity.
   *
   * @param uri key
   * @return new BwmsgAddress
   * @throws NotifyException on error
   */
  BwmsgAddress fromUri(URI uri) throws NotifyException;

  /** Create or return an address using the string value as a key. The address
   *  will not be bound to any entity. The parameter may be may be:<ul>
   * <li>A fully specified URL</li>
   * <li>An absolute path</li>
   * </ul>
   * <p>If a URI it is equivalent to calling fromURI. If the initial part of the
   * address references the current instance then it is the same as calling this
   * method with an absolute path.
   *
   * <p>If the address is absolute (i.e. starts with "/") the returned address
   * will be that of an entity within the instance.
   *
   * @param addr the address
   * @return new BwmsgAddress
   * @throws NotifyException on error
   */
  BwmsgAddress fromPath(String addr) throws NotifyException;

  /** Start the service
   *
   * @throws NotifyException on error
   */
  void start() throws NotifyException;

  /**
   * @return true if started
   */
  boolean isStarted();

  /** Stop the service
   *
   * @throws NotifyException on error
   */
  void stop() throws NotifyException;

  /** Bind the entity to the address
   *
   * @param address the address
   * @param entity the entity
   * @throws NotifyException if entity already bound or the address is bound to
   * another entity or is invalid.
   */
  void bind(BwmsgAddress address, Addressable entity) throws NotifyException;
}
