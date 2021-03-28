#include "Native.h"

#include "double-conversion/bignum.cc"
#include "double-conversion/cached-powers.cc"
#include "double-conversion/string-to-double.cc"
#include "double-conversion/strtod.cc"

JNIEXPORT jdouble JNICALL Java_eu_menzani_lang_NoGarbageParseDouble_parse
  (JNIEnv *env, jclass clazz, jlong src, jint srcLength)
{
    static double_conversion::StringToDoubleConverter converter(
            double_conversion::StringToDoubleConverter::NO_FLAGS, 0.0, 0.0, "Infinity", "NaN");
    int processed_characters_count;
    return converter.StringToDouble(reinterpret_cast<const char *>(src), srcLength, &processed_characters_count);
}
