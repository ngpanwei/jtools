/** 
 *  Copyright (c) 2010  Ng Pan Wei
 *  
 *  Permission is hereby granted, free of charge, to any person 
 *  obtaining a copy of this software and associated documentation files 
 *  (the "Software"), to deal in the Software without restriction, 
 *  including without limitation the rights to use, copy, modify, merge, 
 *  publish, distribute, sublicense, and/or sell copies of the Software, 
 *  and to permit persons to whom the Software is furnished to do so, 
 *  subject to the following conditions: 
 *  
 *  The above copyright notice and this permission notice shall be 
 *  included in all copies or substantial portions of the Software. 
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 *  BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN 
 *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 *  SOFTWARE. 
 */

package jWatcher;

/**
 * For getting the signature of an executing line, the caller and so on.
 * Supports logging and hyper-linking through Eclipse error parser.
 * 
 * @author panwei
 */
public class Watcher   {
    /**
     * Get string signature of current executing line.
     * 
     * @return
     */
    public static String getSignature() {
        StackTraceElement trace = getCaller(null);
        String signature = getSignature(trace);
        return signature;
    }

    /**
     * Get the signature of stack trace conforming to eclipse error parser.
     * 
     * @return
     */
    protected static String getSignature(StackTraceElement trace) {
        String classNameParts[] = trace.getClassName().split("\\.");
        String className = classNameParts[classNameParts.length - 1];
        String signature = className + "." + trace.getMethodName();
        signature += getShortSignature(trace) ;
        return signature;
    }
    /**
     * Get short signature that only has file and line number.
     * @param trace
     * @return
     */

    protected static String getShortSignature(StackTraceElement trace) {
        return getShortSignature(trace.getFileName(),trace.getLineNumber()) ;
    }
    protected static String getShortSignature(String filename,int lineNumber) {
        String signature = "(" + filename + ":" + lineNumber+ ")";
        return signature;
    }   /**
     * Get the caller by scanning the stack trace. Ignores this class and
     * classes provided by caller.
     * 
     * @return
     */
    protected static StackTraceElement getCaller(String pIgnoredClassNames[]) {
        String thisClassName = Watcher.class.getSimpleName();
        StackTraceElement[] traces = new Throwable().getStackTrace();
        for (StackTraceElement trace : traces) {
            String traceClassName = trace.getClassName();
            boolean found = isTargetTrace(traceClassName, thisClassName,
                    pIgnoredClassNames);
            if (found)
                return trace;
        }
        return null;
    }

    /**
     * Part of trace scanning to determine if the trace is to be ignored.
     * 
     * @param traceClassName
     * @param pWatcherName
     * @param pIgnoredClassNames
     * @return
     */
    private static boolean isTargetTrace(String traceClassName,
            String pWatcherName, String pIgnoredClassNames[]) {
        if (traceClassName.endsWith(pWatcherName) == true)
            return false;
        if (traceClassName.contains("$") == true)
            return false;
        if (pIgnoredClassNames == null)
            return true;
        for (String ignoredClassName : pIgnoredClassNames) {
            if (traceClassName.endsWith(ignoredClassName) == true)
                return false;
        }
        return true;
    }

    /**
     * log executing class name, method name and line number.
     */
    public static void log() {
        log(null);
        System.err.println();
    }

    /**
     * log a message with executing class name, method name and line number.
     * 
     * @param pMessage
     */
    public static void log(String pMessage) {
        log(null, pMessage);
    }

    public static void logAround(String filename,String pMessage) {
        StackTraceElement[] traces = new Throwable().getStackTrace();
        String shortSignature = getShortSignature(filename,traces[1].getLineNumber());
        String signature = getSignature(traces[2]);
        System.err.print(signature);
        System.err.println(" "+shortSignature);
        if (pMessage == null)
            return;
        System.err.println("  [" + pMessage + "]");
    }
    public static void logBefore(String pMessage) {
        StackTraceElement[] traces = new Throwable().getStackTrace();
        String shortSignature = getShortSignature(traces[1]);
        String signature = getSignature(traces[2]);
        System.err.print(signature);
        System.err.println(" "+shortSignature);
        if (pMessage == null)
            return;
        System.err.println("  [" + pMessage + "]");
    }
    /**
     * Log a message ignoring a number of classes in the stack.
     * 
     * @param pIgnoredClassNames
     * @param pMessage
     */
    public static void log(String pIgnoredClassNames[], String pMessage) {
        StackTraceElement trace = getCaller(pIgnoredClassNames);
        String signature = getSignature(trace);
        System.err.print(signature);
        if (pMessage == null)
            return;
        System.err.println(" [" + pMessage + "]");
    }
}
