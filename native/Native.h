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

JNIEXPORT jint JNICALL Java_eu_menzani_lang_DecimalConversion_appendDouble
  (JNIEnv *, jclass, jdouble, jlong);

JNIEXPORT jint JNICALL Java_eu_menzani_lang_DecimalConversion_appendFloat
  (JNIEnv *, jclass, jfloat, jlong);

JNIEXPORT jdouble JNICALL Java_eu_menzani_lang_DecimalConversion_parseDouble
  (JNIEnv *, jclass, jlong, jint);

JNIEXPORT jfloat JNICALL Java_eu_menzani_lang_DecimalConversion_parseFloat
  (JNIEnv *, jclass, jlong, jint);

#ifdef __cplusplus
}
#endif

#endif
