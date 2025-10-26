FROM openjdk:17-jdk-slim

# Aktifkan arsitektur i386 dan install library 32-bit yang dibutuhkan Android SDK
RUN dpkg --add-architecture i386 && \
    apt-get update && apt-get install -y \
    wget \
    unzip \
    dos2unix \
    libc6:i386 \
    libncurses5:i386 \
    libstdc++6:i386 \
    lib32z1 \
    --no-install-recommends

ENV ANDROID_HOME /usr/local/android-sdk
ENV PATH ${PATH}:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools

# Perintah ini seharusnya sudah benar
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    cd ${ANDROID_HOME}/cmdline-tools && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O cmdline-tools.zip && \
    unzip cmdline-tools.zip && \
    mv cmdline-tools latest && \
    rm cmdline-tools.zip

# Perintah ini juga seharusnya sudah benar
RUN yes |
sdkmanager --sdk_root=${ANDROID_HOME} "platform-tools" "platforms;android-34" "build-tools;34.0.0"

WORKDIR /workspace
