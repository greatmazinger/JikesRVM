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
package org.vmmagic.pragma;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import org.vmmagic.Pragma;

/**
 * By default all Java code is interruptible, that is scheduling or garbage
 * collection may occur at points within the code. Code can be marked as
 * {@link Unpreemptible} or {@link Uninterruptible}, that instructs the JVM to
 * avoid garbage collection and thread scheduling. The {@link Uninterruptible}
 * annotation disallows any operation that may cause garbage collection or
 * thread scheduling, for example memory allocation. The {@link Unpreemptible}
 * annotation doesn't disallow operations that can cause garbage collection or
 * scheduling, but instructs the JVM to avoid inserting such operations during a
 * block of code.<p>
 *
 * In the internals of a VM most code wants to be {@link Uninterruptible}.
 * However, code involved in scheduling and locking will cause context switches,
 * and creating exception objects may trigger garbage collection, this code is
 * therefore {@link Unpreemptible}.<p>
 *
 * Any method that is marked as uninterruptible is treated specially by the
 * machine code compiler:
 * <ol>
 *   <li>the normal thread switch test that would be emitted in the method
 * prologue is omitted.</li>
 *   <li>the stack overflow test that would be emitted in the method prologue is
 * omitted.</li>
 *   <li>calls to preemptible code causes warnings.</li>
 *   <li>bytecodes that can cause interruption cause warnings.</li>
 *   <li>uninterruptible code will be generated assuming no RuntimeExceptions are
 * raised and without any GC maps (since by definition there can be noGC if
 * control is not lost).</li>
 * </ol>
 * <p>
 *
 * This is the inverse of {@link Interruptible}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Pragma
public @interface Uninterruptible {
  /**
   * @return Explanation of why code needs to be uninterruptible
   */
  String value() default "";
}
