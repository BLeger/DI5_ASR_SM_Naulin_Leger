#include <jni.h>
#include <string>

extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_example_myapplicationopencv_MainActivity_gradientJNI(JNIEnv *env, jobject thiz, jbyteArray data, jint w, jint h) {
    jboolean isCopy = false;
    jbyte* input = env->GetByteArrayElements(data, &isCopy);

    jbyte* output = new jbyte[w * h];

    for (int x = 0; x < w; x++) {
        for (int y = 0; y < h; y++) {
            // On ne traite pas les bordures
            if (x == 0 || y == 0 || x >= w - 1 || y >= h - 1) {
                output[x + w * y] = input[x + w * y];
            } else {
                int gradH = input[(x-1) + w * y] - input[(x+1) + w * y];
                int gradV = input[x + w * (y-1)] - input[x + w * (y+1)];
                jbyte gradient = (jbyte) ((gradH + gradV + 255) / 2);
                output[x + w * y] = gradient;
            }
        }
    }

    jbyteArray ret = env->NewByteArray(w * h);
    env->SetByteArrayRegion (ret, 0, w * h, output);
    return ret;
}

