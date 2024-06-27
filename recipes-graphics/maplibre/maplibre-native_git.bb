#
# Copyright (c) 2020-2023 Joel Winarske. All rights reserved.
#

SUMMARY = "MapLibre Native - Interactive vector tile maps for iOS, Android and other platforms."
DESCRIPTION = "MapLibre Native is a free and open-source library for publishing maps in your apps \
    and desktop applications on various platforms. Fast displaying of maps is possible thanks to \
    GPU-accelerated vector tile rendering."
AUTHOR = ""
HOMEPAGE = "https://maplibre.org/"
BUGTRACKER = "https://github.com/maplibre/maplibre-native/issues"
SECTION = "graphics"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=9557c4180a72a6137edd96affcb140e4"

DEPENDS += " \
    curl \
    virtual\egl \
    virtual\gles2 \
    glfw \
    icu \
    jpeg \
    libpng \
    libuv \
    libwebp \
"

SRCREV = "169dc065c5eb63c4162dbc85cf78efba23484ba7"
SRC_URI = "gitsm://github.com/maplibre/maplibre-native.git;protocol=https;lfs=0;nobranch=1"

S = "${WORKDIR}/git"

inherit cmake features_check pkgconfig

PACKAGECONFIG ??= " \
    ${@bb.utils.filter('DISTRO_FEATURES', 'wayland x11', d)} \
"

# resolve the most common collision automatically by preferring wayland. this
# really only removes the unnecessary runtime deps. it doesn't change linking
# options

PACKAGECONFIG:remove ??= " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'x11', '', d)} \
"

PACKAGECONFIG[wayland] = "-DMLN_WITH_WAYLAND=ON, -DMMLN_WITH_X11=OFF, wayland wayland-native wayland-protocols"
PACKAGECONFIG[x11] = "-DMMLN_WITH_X11=ON, -DMLN_WITH_WAYLAND=OFF"

EXTRA_OECMAKE += " \
    -D MLN_WITH_ELG=ON \
"
