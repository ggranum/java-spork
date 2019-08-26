/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */
package com.geoffgranum.spork.common.persistence.id;

import com.geoffgranum.spork.common.log.Log;
import com.google.inject.Singleton;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Id generally of the form described here: http://www.mongodb.org/display/DOCS/Object+IDs
 * <p>
 * However, uses full 8 bytes for time field instead of the 4 full Long.
 * <p>
 * [00][01][02][03][04][05][06][07][08][09][10][11][12][13][14][15]
 * | time                         |   machine |  pid  | inc       |
 * 8 Bytes for time (current time in millis)
 * 3 Bytes for machine ID
 * 2 Bytes for process ID
 * 3 Byte auto increment field.
 */
@Singleton
@ThreadSafe
@Immutable
public final class MongoStyleIdGenerator implements IdGenerator {

  private static final int THREE_BYTE_INT_MAX_VALUE = 1677216;
  private static final byte[] processIdHash = processIdHash();
  private static final byte[] machineIdHash = machineIdHash();
  private final AtomicInteger autoInc = new AtomicInteger((int)(Math.random() * THREE_BYTE_INT_MAX_VALUE));

  @Override
  public BigInteger nextId() {
    int nextAuto;
    synchronized (autoInc) {
      nextAuto = autoInc.getAndIncrement();
      checkForOverflow(autoInc);
    }
    byte[] bytes = new byte[16];
    long millis = System.currentTimeMillis();
    insertAsBytes(millis, bytes, 0);
    System.arraycopy(machineIdHash, 0, bytes, 8, 3);
    System.arraycopy(processIdHash, 0, bytes, 11, 2);
    // copy lower-order bytes from the int as the auto increment value
    insertAsBytes(nextAuto, 1, bytes, 13, 3);
    return new BigInteger(bytes);
  }

  @Override
  public <T extends TypedId> T nextId(Class<T> type) {
    T id;
    try {
      Constructor constructor = type.getConstructor(BigInteger.class);
      id = type.cast(constructor.newInstance(nextId()));
    } catch (Exception e) {
      /* Unless TypedId gets modified this won't happen. */
      throw new RuntimeException(e);
    }
    return id;
  }

  private static void checkForOverflow(AtomicInteger autoInc) {
    if(autoInc.get() >= THREE_BYTE_INT_MAX_VALUE) {
      Log.trace(MongoStyleIdGenerator.class, "AutoInc field rolled over to 0.");
      autoInc.set(0);
    }
  }

  private static byte[] processIdHash() {
    try {
      byte[] name = ManagementFactory.getRuntimeMXBean().getName().getBytes(Charset.forName("UTF-8"));
      return MessageDigest.getInstance("MD5").digest(name);
    } catch (NoSuchAlgorithmException e) {
      Log.error(MongoStyleIdGenerator.class, "Could not calculate a process ID hash.", e);
      throw new RuntimeException(e);
    }
  }

  private static byte[] machineIdHash() {
    try {
      InetAddress ip = InetAddress.getLocalHost();
      NetworkInterface network = NetworkInterface.getByInetAddress(ip);
      byte[] mac = network.getHardwareAddress();
      return MessageDigest.getInstance("MD5").digest(mac);
    } catch (Exception e) {
      Log.error(MongoStyleIdGenerator.class, "Could not calculate machine ID hash.", e);
      throw new RuntimeException(e);
    }
  }

  private static void insertAsBytes(long v, byte[] into, int destPos) {
    into[destPos] = (byte)(v >>> 56);
    into[destPos + 1] = (byte)(v >>> 48);
    into[destPos + 2] = (byte)(v >>> 40);
    into[destPos + 3] = (byte)(v >>> 32);
    into[destPos + 4] = (byte)(v >>> 24);
    into[destPos + 5] = (byte)(v >>> 16);
    into[destPos + 6] = (byte)(v >>> 8);
    into[destPos + 7] = (byte)(v);
  }

  private static void insertAsBytes(int v, int srcPos, byte[] into, int destPos, int length) {
    for (int i = srcPos; i < length; i++) {
      into[destPos + i] = (byte)(v >>> (24 - 8 * (srcPos + i)));
    }
  }
}
 
