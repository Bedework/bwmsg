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

import java.util.Iterator;

/** Channels are all subclasses of this type. They all have an input and an output.
 * Typically they will be implemented on top of JMS queues so have many or all
 * of the characteristics of such a queue.
 *
 * @author douglm
 */
public interface Channel extends Addressable {
  /**
   * @return number of pending messages
   * @throws NotifyException
   */
  Iterator<Notification> getNotifications() throws NotifyException;

  /**
   * @return true if a message is available
   * @throws NotifyException
   */
  boolean isAvailable() throws NotifyException;

  /**
   * @return next Notification or null if interrupted
   * @throws NotifyException
   */
  Notification getNext() throws NotifyException;

  /**
   * @param maxWaitMillis maximum wait time or <0 indicating forever
   * @return next Notification or null if timedout
   * @throws NotifyException
   */
  Notification getNext(long maxWaitMillis) throws NotifyException;

  /** Post a notification
   *
   * @param n
   * @throws NotifyException
   */
  void post(Notification n) throws NotifyException;
}
