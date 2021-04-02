#define NDEBUG

#include <jni.h>

#ifndef _Included_eu_menzani_Native
#define _Included_eu_menzani_Native

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_eu_menzani_system_WindowsSleep_prepare
  (JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_eu_menzani_system_WindowsSleep_sleepHalfAMillisecond
  (JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_eu_menzani_system_LinuxSleep_sleep
  (JNIEnv *, jclass, jint);

JNIEXPORT jint JNICALL Java_eu_menzani_system_Threads_bindCurrentThread
  (JNIEnv *, jclass, jint);

JNIEXPORT jint JNICALL Java_eu_menzani_system_Threads_setCurrentThreadPriority
  (JNIEnv *, jclass);

JNIEXPORT jdouble JNICALL Java_eu_menzani_lang_DoubleConversion_parse
  (JNIEnv *, jclass, jlong, jint);

JNIEXPORT jint JNICALL Java_eu_menzani_lang_DoubleConversion_append
  (JNIEnv *, jclass, jdouble, jlong);

#ifdef __cplusplus
}
#endif

#endif
