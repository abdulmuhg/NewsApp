##include <jni.h>

extern "C" JNIEXPORT jstring JNICALL

JAVA_secretymus_id_newsapp_views_MainActivity_getAPIKey(JNIEnv* env, jobject){
    return (*env) -> NewStringUTF(env, "339e6576cb374c28a8467f42beb485f6")
}