# from here: https://www.guardsquare.com/en/proguard/manual/examples#scala

-dontwarn scala.**

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

-keep class * implements org.xml.sax.EntityResolver

-keepclassmembers class * {
    ** MODULE$;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinPool {
   long eventCount;
   int  workerCounts;
   int  runControl;
   scala.concurrent.forkjoin.ForkJoinPool$WaitQueueNode syncStack;
   scala.concurrent.forkjoin.ForkJoinPool$WaitQueueNode spareStack;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinWorkerThread {
    int base;
    int sp;
    int runState;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinTask {
    int status;
}

-keepclassmembernames class scala.concurrent.forkjoin.LinkedTransferQueue {
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference head;
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference tail;
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference cleanMe;
}

# my additions

-dontnote scala.concurrent.forkjoin.**
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**