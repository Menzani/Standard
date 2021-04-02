#include "Native.h"

#include "double-conversion/bignum.cc"
#include "double-conversion/bignum-dtoa.cc"
#include "double-conversion/cached-powers.cc"
#include "double-conversion/double-to-string.cc"
#include "double-conversion/fast-dtoa.cc"
#include "double-conversion/fixed-dtoa.cc"
#include "double-conversion/string-to-double.cc"
#include "double-conversion/strtod.cc"

using namespace double_conversion;

JNIEXPORT jint JNICALL Java_eu_menzani_lang_DoubleConversion_append
  (JNIEnv *env, jclass clazz, jdouble value, jlong dest)
{
    char *dest_ = reinterpret_cast<char *>(dest);
    bool sign;
    int length;
    int point;
    DoubleToStringConverter::DoubleToAscii(value, DoubleToStringConverter::DtoaMode::SHORTEST, 0, dest_, 0, &sign, &length, &point);
    char *pointInDest = dest_ + point;
    memmove(pointInDest + 1, pointInDest, length - point);
    *pointInDest = '.';
    return length + 1;
}

JNIEXPORT jdouble JNICALL Java_eu_menzani_lang_DoubleConversion_parse
  (JNIEnv *env, jclass clazz, jlong src, jint srcLength)
{
    static StringToDoubleConverter converter(StringToDoubleConverter::NO_FLAGS, 0.0, 0.0, "Infinity", "NaN");
    int processed_characters_count;
    return converter.StringToDouble(reinterpret_cast<const char *>(src), srcLength, &processed_characters_count);
}
