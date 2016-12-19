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

/** Filter, source, receiver are all subclasses of this type. They all have
 * inputs and outputs.
 *
 * <p>Outputs may be registered as subscribeable so that other filters can
 * connect to them. An output can be connected to an input to create a pending
 * queue.
 *
 * <p>We will use the http servlet/portlet/filter as a model in that messages
 * flow through a series of filters before emerging or being consumed. To that
 * extent messages are all a single type like HttpRequest and we will develop
 * wrappers etc.
 *
 * @author douglm
 */
public interface Filter extends Addressable {
  /**
   * @return number of pending messages
   */
  int getPendingCount();

  /** Flush any pending messages.
   *
   * @throws NotifyException
   */
  void flushPending() throws NotifyException;
}
