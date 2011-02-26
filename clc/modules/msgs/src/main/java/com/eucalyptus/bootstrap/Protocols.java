/*******************************************************************************
 * Copyright (c) 2009  Eucalyptus Systems, Inc.
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
 *******************************************************************************
 * @author chris grzegorczyk <grze@eucalyptus.com>
 */

package com.eucalyptus.bootstrap;

import java.util.List;
import org.jgroups.conf.ClassConfigurator;
import org.jgroups.protocols.BARRIER;
import org.jgroups.protocols.FC;
import org.jgroups.protocols.FD;
import org.jgroups.protocols.FD_SOCK;
import org.jgroups.protocols.FRAG2;
import org.jgroups.protocols.MERGE2;
import org.jgroups.protocols.MFC;
import org.jgroups.protocols.PING;
import org.jgroups.protocols.UDP;
import org.jgroups.protocols.UFC;
import org.jgroups.protocols.UNICAST;
import org.jgroups.protocols.VERIFY_SUSPECT;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.protocols.pbcast.STATE_TRANSFER;
import org.jgroups.stack.Protocol;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

public class Protocols {
  public static short PROTOCOL_ID = 512;
  
  private static String registerProtocol( Protocol p ) {
    if ( ClassConfigurator.getProtocolId( p.getClass( ) ) == 0 ) {
      ClassConfigurator.addProtocol( ++PROTOCOL_ID, p.getClass( ) );
    }
    return "euca-" + ( p.getClass( ).isAnonymousClass( )
      ? p.getClass( ).getSuperclass( ).getSimpleName( ).toLowerCase( )
      : p.getClass( ).getSimpleName( ).toLowerCase( ) ) + "-protocol";
  }
  
  public static List<Protocol> getMembershipProtocolStack( ) {
    return Lists.newArrayList( udp.get( ), ping.get( ), merge2.get( ), fdSocket.get( ), fd.get( ), verifySuspect.get( ), nakack.get( ), unicast.get( ),
                               stable.get( ), groupMemberShip.get( ), flowControl.get( ), fragmentation.get( ), stateTransfer.get( ) );
  }
  
  private static final Supplier<Protocol> udp                  = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new UDP( ) {
                                                                     {
                                                                       this.name = Protocols.registerProtocol( this );
                                                                       this.setMulticastAddress( MembershipConfiguration.getMulticastInetAddress( ) );
                                                                       this.setMulticastPort( MembershipConfiguration.getMulticastPort( ) );
                                                                       this.setBindToAllInterfaces( true );
                                                                       this.setDiscardIncompatiblePackets( true );
                                                                       this.setMaxBundleSize( 60000 );
                                                                       this.setMaxBundleTimeout( 30 );
                                                                       
                                                                       this.setDefaultThreadPool( MembershipConfiguration.getThreadPool( ) );
                                                                       this.setDefaultThreadPoolThreadFactory( MembershipConfiguration.getThreadPool( ) );
                                                                       
                                                                       this.setThreadFactory( MembershipConfiguration.getNormalThreadPool( ) );
                                                                       this.setThreadPoolMaxThreads( MembershipConfiguration.getThreadPoolMaxThreads( ) );
                                                                       this.setThreadPoolKeepAliveTime( MembershipConfiguration.getThreadPoolKeepAliveTime( ) );
                                                                       this.setThreadPoolMinThreads( MembershipConfiguration.getThreadPoolMinThreads( ) );
                                                                       this.setThreadPoolQueueEnabled( MembershipConfiguration.getThreadPoolQueueEnabled( ) );
                                                                       this.setRegularRejectionPolicy( MembershipConfiguration.getRegularRejectionPolicy( ) );
                                                                       
                                                                       this.setOOBThreadPoolThreadFactory( MembershipConfiguration.getOOBThreadPool( ) );
                                                                       this.setOOBThreadPool( MembershipConfiguration.getOOBThreadPool( ) );
                                                                       this.setOOBThreadPoolMaxThreads( MembershipConfiguration.getOobThreadPoolMaxThreads( ) );
                                                                       this.setOOBThreadPoolKeepAliveTime( MembershipConfiguration.getOobThreadPoolKeepAliveTime( ) );
                                                                       this.setOOBThreadPoolMinThreads( MembershipConfiguration.getOobThreadPoolMinThreads( ) );
                                                                       this.setOOBRejectionPolicy( MembershipConfiguration.getOobRejectionPolicy( ) );
                                                                     }
                                                                   };
                                                                 }
                                                               };
  private static final Supplier<Protocol> ping                 = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new PING( ) {
                                                                     {
                                                                       this.name = Protocols.registerProtocol( this );
                                                                       this.setTimeout( 2000 );
                                                                       this.setNumInitialMembers( 2 );
                                                                     }
                                                                   };
                                                                 }
                                                               };
  private static final Supplier<Protocol> merge2               = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new MERGE2( );
                                                                 }
                                                               };
  
  private static final Supplier<Protocol> fdSocket             = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new FD_SOCK( );
                                                                 }
                                                               };
  private static final Supplier<Protocol> fd                   = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new FD( ) {
                                                                     {
                                                                       this.name = Protocols.registerProtocol( this );
                                                                       this.setTimeout( 10000 );
                                                                       this.setMaxTries( 5 );
                                                                       this.setShun( true );
                                                                     }
                                                                   };
                                                                 }
                                                               };
  private static final Supplier<Protocol> verifySuspect        = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new VERIFY_SUSPECT( );
                                                                 }
                                                               };
  private static final Supplier<Protocol> barrier              = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new BARRIER( );
                                                                 }
                                                               };
  private static final Supplier<Protocol> nakack               = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new NAKACK( ) {
                                                                     {
                                                                       this.name = Protocols.registerProtocol( this );
                                                                       this.setUseMcastXmit( false );
                                                                       this.setDiscardDeliveredMsgs( true );
                                                                       this.setGcLag( 0 );
//                                                    this.setProperty( "retransmit_timeout", "300,600,1200,2400,4800" );
                                                                     }
                                                                     
                                                                   };
                                                                 }
                                                               };
  
  private static final Supplier<Protocol> unicast              = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new UNICAST( );
                                                                 }
                                                               };
  private static final Supplier<Protocol> stable               = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new STABLE( ) {
                                                                     {
                                                                       this.name = Protocols.registerProtocol( this );
                                                                       this.setDesiredAverageGossip( 50000 );
                                                                       this.setMaxBytes( 400000 );
//                                                                                          this.setStabilityDelay( 1000 );
                                                                     }
                                                                   };
                                                                 }
                                                               };
  private static final Supplier<Protocol> groupMemberShip      = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new GMS( ) {
                                                                     {
                                                                       this.name = Protocols.registerProtocol( this );
                                                                       this.setPrintLocalAddress( true );
                                                                       this.setJoinTimeout( 3000 );
                                                                       this.setShun( false );
                                                                       this.setViewBundling( true );
                                                                     }
                                                                   };
                                                                 }
                                                               };
  private static final Supplier<Protocol> flowControl          = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new FC( ) {
                                                                     {
                                                                       this.name = Protocols.registerProtocol( this );
                                                                       this.setMaxCredits( 20000000 );
                                                                       this.setMinThreshold( 0.1 );
                                                                     }
                                                                   };
                                                                 }
                                                               };
  private static final Supplier<Protocol> unicastFlowControl   = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new UFC( );
                                                                 }
                                                               };
  private static final Supplier<Protocol> multicastFlowControl = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new MFC( );
                                                                 }
                                                               };
  private static final Supplier<Protocol> fragmentation        = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new FRAG2( ) {
                                                                     {
                                                                       this.name = Protocols.registerProtocol( this );
                                                                     }
                                                                   };
                                                                 }
                                                               };
  private static final Supplier<Protocol> stateTransfer        = new Supplier<Protocol>( ) {
                                                                 
                                                                 @Override
                                                                 public Protocol get( ) {
                                                                   return new STATE_TRANSFER( );
                                                                 }
                                                               };
  
}
