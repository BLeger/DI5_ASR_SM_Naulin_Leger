#include <jni.h>
#include <string>

extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_example_myapplicationopencv_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz, jbyteArray data, jint w, jint h) {
    jboolean isCopy = false;
    jbyte * input = env->GetByteArrayElements(data, &isCopy);

    jbyte* output = new jbyte[w * h];

    // Juste pour tester
    for (int x = 0; x < w; x++) {
        for(int y = 0; y < h; y++) {
            output[x + w * y] = 0;
        }
    }

    jbyteArray ret = env->NewByteArray(w * h);
    env->SetByteArrayRegion (ret, 0, w * h, output);
    return ret;
}
