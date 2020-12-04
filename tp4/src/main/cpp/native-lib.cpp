#include <jni.h>
#include <string>
#include <cmath>

int getIndice(int x, int y, int w) {
    return x + w * y;
}

int matriceX[] = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
int matriceY[] = {-1, -2, -1, 0, 0, 0, 1, 2, 1};

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

int convolution(int* matriceConvolution, jbyte* image, int x, int y, int w){
    int k = 0;
    int result = 0;
    for (int j = y-1; j <= y+1; j++){
        for (int i = x-1; i <= x+1; i++){
            result += matriceConvolution[k] * image[getIndice(i, j, w)];
            k++;
        }
    }
    return result;
}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_example_myapplicationopencv_MainActivity_sobelJNI(JNIEnv *env, jobject thiz, jbyteArray data, jint w, jint h) {
    jboolean isCopy = false;
    jbyte* input = env->GetByteArrayElements(data, &isCopy);

    jbyte* output = new jbyte[w * h];

    for (int x = 0; x < w; x++) {
        for (int y = 0; y < h; y++) {
            // On ne traite pas les bordures
            if (x == 0 || y == 0 || x >= w - 1 || y >= h - 1) {
                output[getIndice(x, y, w)] = input[getIndice(x, y, w)];
            } else {
                int gradH = convolution(matriceX, input, x, y, w);
                int gradV = convolution(matriceY, input, x, y, w);
                int grad = (int) sqrt(gradH * gradH + gradV * gradV);
                output[getIndice(x, y, w)] = (jbyte) grad;
            }
        }
    }

    jbyteArray ret = env->NewByteArray(w * h);
    env->SetByteArrayRegion (ret, 0, w * h, output);
    return ret;
}


