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

import java.util.List;


/** <p>Represents an entity which consumes notifications from multiple inputs.
 * e.g an aggregator
 *
 * @author Mike Douglass   douglm  rpi.edu
 */
public interface MultiConsumer extends Consumer {
  /**
   * @return immutable list of current input channels
   * @throws NotifyException
   */
  List<Channel> getInputs() throws NotifyException;

  /**
   * @param c
   * @throws NotifyException
   */
  void addInput(Channel c) throws NotifyException;

  /**
   * @param c
   * @throws NotifyException
   */
  void removeInput(Channel c) throws NotifyException;
}
