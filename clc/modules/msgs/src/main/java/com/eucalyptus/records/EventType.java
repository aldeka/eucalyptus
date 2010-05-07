/*******************************************************************************
 *Copyright (c) 2009  Eucalyptus Systems, Inc.
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, only version 3 of the License.
 * 
 * 
 *  This file is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 *  for more details.
 * 
 *  You should have received a copy of the GNU General Public License along
 *  with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  Please contact Eucalyptus Systems, Inc., 130 Castilian
 *  Dr., Goleta, CA 93101 USA or visit <http://www.eucalyptus.com/licenses/>
 *  if you need additional information or have any questions.
 * 
 *  This file may incorporate work covered under the following copyright and
 *  permission notice:
 * 
 *    Software License Agreement (BSD License)
 * 
 *    Copyright (c) 2008, Regents of the University of California
 *    All rights reserved.
 * 
 *    Redistribution and use of this software in source and binary forms, with
 *    or without modification, are permitted provided that the following
 *    conditions are met:
 * 
 *      Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 * 
 *      Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 * 
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *    IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *    TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. USERS OF
 *    THIS SOFTWARE ACKNOWLEDGE THE POSSIBLE PRESENCE OF OTHER OPEN SOURCE
 *    LICENSED MATERIAL, COPYRIGHTED MATERIAL OR PATENTED MATERIAL IN THIS
 *    SOFTWARE, AND IF ANY SUCH MATERIAL IS DISCOVERED THE PARTY DISCOVERING
 *    IT MAY INFORM DR. RICH WOLSKI AT THE UNIVERSITY OF CALIFORNIA, SANTA
 *    BARBARA WHO WILL THEN ASCERTAIN THE MOST APPROPRIATE REMEDY, WHICH IN
 *    THE REGENTS’ DISCRETION MAY INCLUDE, WITHOUT LIMITATION, REPLACEMENT
 *    OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO IDENTIFIED, OR
 *    WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT NEEDED TO COMPLY WITH
 *    ANY SUCH LICENSES OR RIGHTS.
 *******************************************************************************/
/*
 * Author: chris grzegorczyk <grze@eucalyptus.com>
 */

package com.eucalyptus.records;

public enum EventType {
  TIMEOUT,
  MSG_SERVICED,
  MSG_SENT,
  MSG_REJECTED,
  MSG_RECEIVED,
  QUEUE_LENGTH,
  QUEUE_TIME,
  SERVICE_TIME,
  MSG_PENDING,
  VM_PREPARE,
  VM_RESERVED,
  VM_STARTING,
  VM_TERMINATING,
  MSG_POLL_INTERNAL,
  VM_RUNNING,
  SOCKET_OPEN,
  SOCKET_CLOSE,
  SOCKET_BYTES_READ,
  SOCKET_BYTES_WRITE,
  PIPELINE_UNROLL,
  PIPELINE_HANDLER,
  PIPELINE_DUPLICATE,
  VM_TERMINATED,
  QUEUE,
  FLUSH_CACHE,
  LISTENER_REGISTERED,
  LISTENER_DEREGISTERED,
  LISTENER_EVENT_FIRED,
  LISTENER_EVENT_VETOD,
  LISTENER_DESTROY_ALL,
  TOKEN_RETURNED,
  TOKEN_ACCEPTED,
  TOKEN_SUBMITTED,
  TOKEN_ALLOCATED,
  TOKEN_REDEEMED,
  TOKEN_SPLIT,
  TOKEN_CHILD,
  TOKEN_RESERVED,
  CLUSTER_STATE_UPDATE,
  TRANSITION,
  MSG_PREPARED,
  VM_START_COMPLETED,
  VM_START_ABORTED,
  CLUSTER_CERT,
  CONTEXT_CREATE,
  CONTEXT_USER,
  CONTEXT_CLEAR,
  MSG_REPLY,
  CONTEXT_MSG,
  CONTEXT_EVENT,
  CONTEXT_SUBJECT,
  GENERATE_KEYPAIR,
  GENERATE_CERTIFICATE,
  MSG_AWAIT_RESPONSE,
  PROVIDER_CONFIGURED,
  PROVIDER_CONFLICT,
  PROVIDER_IGNORED,
  SYSTEM_DIR_CREATE,
  SYSTEM_DIR_CHECK,
  BOOTSTRAP_INIT_RESOURCES,
  BOOTSTRAP_STAGE_START,
  BOOTSTRAP_STAGE_TRANSITION,
  BOOTSTRAP_STAGE_COMPLETE,
  BOOTSTRAP_STAGE_BEGIN,
  BOOTSTRAP_INIT_SERVICE_CONFIG,
  BOOTSTRAP_INIT_SERVICE_DISABLED,
  BOOTSTRAP_RESOURCES_SERVICE_DISABLED,
  BOOTSTRAP_RESOURCES_SERVICE_CONFIG,
  BOOTSTRAP_STAGE_SKIPPED,
  BOOTSTRAPPER_SKIPPED,
  BOOTSTRAPPER_ERROR,
  BOOTSTRAP_STAGE_AGENDA,
  BOOTSTRAP_STAGE_LOAD,
  COMPONENT_INFO,
  BOOTSTRAP_INIT_COMPONENT,
  BOOTSTRAP_INIT_CONFIGURATION,
  COMPONENT_SERVICE_INIT,
  COMPONENT_SERVICE_INIT_REMOTE,
  COMPONENT_SERVICE_STOP,
  COMPONENT_SERVICE_STOP_REMOTE,
  COMPONENT_SERVICE_START,
  COMPONENT_SERVICE_START_REMOTE,
  BOOTSTRAP_RESOURCES_PROPERTIES,
  COMPONENT_REGISTERED,
  COMPONENT_REGISTRY_DUMP,
  COMPONENT_LIFECYCLE,
  BOOTSTRAPPER_LOAD,
  BOOTSTRAPPER_START,
  BOOTSTRAPPER_INIT,
  BOOTSTRAPPER_ADDED,
  DISCOVERY_LOADED_ENTRY,
  BINDING_SEEDED,
  DISCOVERY_FINISHED,
  DISCOVERY_STARTED,
  LIFECYCLE_TRANSITION,
  LIFECYCLE_TRANSITION_FAILED,
  LIFECYCLE_COMMIT,
  LIFECYCLE_TRANSITION_REGISTERED,
  LIFECYCLE_TRANSITION_DEREGISTERED,
  TRANSITION_BEGIN,
  TRANSITION_PREPARE,
  TRANSITION_COMMIT,
  TRANSITION_FAILED,
  TRANSITION_FINISHED,
  TRANSITION_EXECUTE,
  TRANSITION_POST,
  TRANSITION_ROLLBACK,
  COMPONENT_DEREGISTERED,
  MSG_SENT_ASYNC, PERSISTENCE_ENTITY_REGISTERED, 
  BUNDLE_STARTED,
  BUNDLE_PENDING,
  BUNDLE_CANCELLED,
  BUNDLE_CANCELING,
  BUNDLE_RESET,
  BUNDLE_TRANSITION,
  BUNDLE_STARTING, CERTIFICATE_WRITE,
}