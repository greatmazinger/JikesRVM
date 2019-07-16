/*
 *  This file is part of the Jikes RVM project (http://jikesrvm.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License. You
 *  may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 *  See the COPYRIGHT.txt file distributed with this work for information
 *  regarding copyright ownership.
 */
package org.jikesrvm.tools.bootImageWriter;

import java.lang.Thread.UncaughtExceptionHandler;

import org.jikesrvm.runtime.ExitStatus;

public class KillVMOnUncaughtExceptionHandler implements
    UncaughtExceptionHandler {

  @Override
  public void uncaughtException(Thread thread, Throwable throwable) {
    throwable.printStackTrace(System.err);
    System.err.println("Killing VM due to failure in thread " + thread);
    System.exit(ExitStatus.EXIT_STATUS_DYING_WITH_UNCAUGHT_EXCEPTION);
  }

}
