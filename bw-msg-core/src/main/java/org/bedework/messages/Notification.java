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

import javax.jms.Message;

/** Notifications are all subclasses of this type. These are what get sent and
 * received within the system. They have many or most of the characteristics of
 * JMS messages, in particular we use the JMS message selection as a way of
 * filtering or selecting notifications.
 *
 * <p>We place a number of restrictions on messages within the system. We need
 * the following characteristics and capabilities for notifications to allow
 * their identification and selection by receivers: <UL>
 * <li>sender:</li>
 * <li>type:</li>
 * <li>categories: namespaced</li>
 * <li>expiry</li>
 * <li>priority</li>
 * <li>messageid: a jms message id</li>
 * <li>deliveryStatusTo: absent for no delivery status response required</li>
 * <li>stage: which filter had we reached? When system restarted notifications
 *      are added to the appropriate filters pending queue</li>
 * <li>filterInTime: timestamp for arrival at current filter</li>
 * <li>filterQueued: timestamp last added to pending queue</li>
 * <li>holdUntil: allows delaying of notifications</li>
 * </ul>
 *
 * @see javax.jms.Message#getJMSMessageID
 * @author douglm
 */
public interface Notification extends Message {
}
