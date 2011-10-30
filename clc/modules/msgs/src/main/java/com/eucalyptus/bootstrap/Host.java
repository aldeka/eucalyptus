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
 *    THE REGENTS DISCRETION MAY INCLUDE, WITHOUT LIMITATION, REPLACEMENT
 *    OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO IDENTIFIED, OR
 *    WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT NEEDED TO COMPLY WITH
 *    ANY SUCH LICENSES OR RIGHTS.
 *******************************************************************************
 * @author chris grzegorczyk <grze@eucalyptus.com>
 */

package com.eucalyptus.bootstrap;

import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;
import org.jgroups.Address;
import com.eucalyptus.component.Topology;
import com.eucalyptus.records.Logs;
import com.eucalyptus.util.Internets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

public class Host implements java.io.Serializable, Comparable<Host> {
  private static final long          serialVersionUID = 1;
  private static Logger              LOG              = Logger.getLogger( Host.class );
  private final String               displayName;
  private final Address              groupsId;
  private final InetAddress          bindAddress;
  private ImmutableList<InetAddress> hostAddresses;
  private Boolean                    hasDatabase;
  private Boolean                    hasBootstrapped;
  private AtomicLong                 timestamp        = new AtomicLong( System.currentTimeMillis( ) );
  private Long                       lastTime         = 0l;
  private Long                 startedTime;
  private Integer                    epoch;
  
  Host( ) {
    this( System.currentTimeMillis( ) );
  }
  
  Host( Long startedTime ) {
    this.startedTime = startedTime == 0 ? System.currentTimeMillis( ) : startedTime;
    this.displayName = Internets.localHostIdentifier( );
    this.groupsId = Hosts.getLocalGroupAddress( );
    this.bindAddress = Internets.localHostInetAddress( );
    this.epoch = Topology.epoch( );
    this.lastTime = this.timestamp.getAndSet( System.currentTimeMillis( ) );
    ImmutableList<InetAddress> newAddrs = ImmutableList.copyOf( Ordering.from( Internets.INET_ADDRESS_COMPARATOR ).sortedCopy( Internets.getAllInetAddresses( ) ) );
    this.hasBootstrapped = Bootstrap.isFinished( );
    this.hasDatabase = BootstrapArgs.isCloudController( );
    this.hostAddresses = newAddrs;
    LOG.trace( "Updated host: " + this );
  }
  
  Host( Host last ) {
    this( last.getStartedTime( ) );
    this.lastTime = last.getTimestamp( ).getTime( );
    this.timestamp.set( System.currentTimeMillis( ) );
  }
  
  public Address getGroupsId( ) {
    return this.groupsId;
  }
  
  public ImmutableList<InetAddress> getHostAddresses( ) {
    return this.hostAddresses;
  }
  
  public InetAddress getBindAddress( ) {
    return this.bindAddress;
  }
  
  public Boolean hasDatabase( ) {
    return this.hasDatabase;
  }
  
  @Override
  public int hashCode( ) {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( ( this.displayName == null )
      ? 0
      : this.displayName.hashCode( ) );
    return result;
  }
  
  @Override
  public boolean equals( Object obj ) {
    if ( this == obj ) {
      return true;
    }
    if ( obj == null ) {
      return false;
    }
    if ( getClass( ) != obj.getClass( ) ) {
      return false;
    }
    Host other = ( Host ) obj;
    if ( this.displayName == null ) {
      if ( other.displayName != null ) {
        return false;
      }
    } else if ( this.displayName.toString( ).equals( other.displayName.toString( ) ) ) {
      return false;
    }
    return true;
  }
  
  @Override
  public int compareTo( Host that ) {
    return this.getDisplayName( ).compareTo( that.getDisplayName( ) );
  }
  
  void stateUpdate( ) {
    Logs.exhaust( ).debug( "Applying update for host: " + this );
    ImmutableList<InetAddress> newAddrs = ImmutableList.copyOf( Ordering.from( Internets.INET_ADDRESS_COMPARATOR ).sortedCopy( Internets.getAllInetAddresses( ) ) );
    this.hostAddresses = newAddrs;
    this.hasDatabase = BootstrapArgs.isCloudController( );
    this.hasBootstrapped = Bootstrap.isFinished( );
    this.epoch = Topology.epoch( );
    this.lastTime = this.timestamp.getAndSet( System.currentTimeMillis( ) );
  }
  
  public boolean isLocalHost( ) {
    return Internets.testLocal( this.getBindAddress( ) );
  }
  
  public Boolean hasBootstrapped( ) {
    return this.hasBootstrapped;
  }
  
  void markBootstrapped( ) {
    this.hasBootstrapped = true;
  }
  
  void markDatabase( ) {
    this.startedTime = System.currentTimeMillis( );
    this.hasDatabase = true;
  }
  
  public Integer getEpoch( ) {
    return this.epoch;
  }
  
  @Override
  public String toString( ) {
    StringBuilder builder = new StringBuilder( );
    builder.append( "Host:" );
    if ( this.groupsId != null ) builder.append( " " ).append( this.groupsId ).append( ":" );
    if ( this.epoch != null ) builder.append( "epoch=" ).append( this.epoch ).append( " " );
    if ( this.bindAddress != null ) builder.append( this.bindAddress ).append( " " );
    if ( this.hasDatabase != null ) builder.append( "db=" ).append( this.hasDatabase ).append( " " );
    if ( this.hasBootstrapped != null ) builder.append( "up=" ).append( this.hasBootstrapped ).append( " " );
    if ( this.timestamp != null ) builder.append( "ats=" ).append( new Date( this.timestamp.get( ) ) ).append( " " );
    if ( this.lastTime != null ) builder.append( "mts=" ).append( new Date( this.lastTime ) ).append( " " );
    if ( this.hostAddresses != null ) builder.append( this.hostAddresses );
    return builder.toString( );
  }
  
  public Date getTimestamp( ) {
    return new Date( this.timestamp.get( ) );
  }
  
  public Long getLastTime( ) {
    return this.lastTime;
  }
  
  public String getDisplayName( ) {
    return this.displayName;
  }
  
  public Long getStartedTime( ) {
    return this.startedTime;
  }
  
}