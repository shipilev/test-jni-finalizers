#include <jni.h>
#include "Test.h"

JNIEXPORT jobject JNICALL Java_Test_callNative
  (JNIEnv* env, jclass klass, jobject obj) {
  jclass c = env->FindClass("Test$Carrier");
  jfieldID f = env->GetFieldID(c, "x", "Ljava/lang/Object;");
  return env->GetObjectField(obj, f);
}
